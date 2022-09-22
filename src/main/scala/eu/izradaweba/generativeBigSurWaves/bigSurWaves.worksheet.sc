import java.awt.Color
import java.util.Date
import scala.util.Random
import scalatags.Text.all._
import scalatags.Text.svgTags.{
  circle,
  defs,
  g,
  linearGradient,
  path,
  rect,
  stop,
  svg
}
import scalatags.Text.svgAttrs.{
  cx,
  cy,
  d,
  fill,
  gradientTransform,
  gradientUnits,
  offset,
  r,
  stroke,
  strokeLinecap,
  strokeLinejoin,
  strokeWidth,
  transform,
  viewBox,
  x,
  x1,
  x2,
  y,
  y1,
  y2,
  preserveAspectRatio,
  width => svgWidth,
  height => svgHeight,
  stopColor
}

def map(n: Int, start1: Float, end1: Float, start2: Float, end2: Float) =
  ((n - start1) / (end1 - start1)) * (end2 - start2) + start2

def lerp(v0: Float, v1: Float, t: Float) =
  v0 * (1 - t) + v1 * t;

val width = 1920
val height = 1080

case class Point(x: Float, y: Float)

def formatPoints(points: Vector[Point], close: Boolean) =
  val points1 = points.flatMap(point => Vector(point.x, point.y))

  if close then
    val lastPoint = points1.last;
    val secondToLastPoint = points1(points1.length - 2);

    val firstPoint = points1.head;
    val secondPoint = points1(1);

    Vector(secondToLastPoint, lastPoint) ++ points1 ++ Vector(
      firstPoint,
      secondPoint
    )
  else points1

def spline(
    points0: IndexedSeq[Point],
    tension: Int = 1,
    close: Boolean = false
) =
  val points = formatPoints(points0.toVector, close)

  val size = points.length
  val last = size - 4

  val startPointX = if close then points(2) else points(0)
  val startPointY = if close then points(3) else points(1)

  var path = s"M$startPointX,$startPointY"

  val startIteration = if close then 2 else 0
  val maxIteration = if close then size - 4 else size - 2
  val inc = 2

  for i <- startIteration to maxIteration - 1 by inc do
    val x0 = if i > 0 then points(i - 2) else points(0)
    val y0 = if i > 0 then points(i - 1) else points(1)

    val x1 = points(i + 0)
    val y1 = points(i + 1)

    val x2 = points(i + 2)
    val y2 = points(i + 3)

    val x3 = if i != last then points(i + 4) else x2
    val y3 = if i != last then points(i + 5) else y2

    val cp1x = x1 + ((x2 - x0) / 6) * tension
    val cp1y = y1 + ((y2 - y0) / 6) * tension

    val cp2x = x2 - ((x3 - x1) / 6) * tension
    val cp2y = y2 - ((y3 - y1) / 6) * tension

    path += s"C$cp1x,$cp1y,$cp2x,$cp2y,$x2,$y2"

  path

def wave(start: Point, end: Point, gradientId: String) =
  val numSteps = Random.between(4, 8)
  val randomRange = Random.between(32, 64)

  val points =
    for i <- 0 to numSteps yield
      val step = map(i, 0, numSteps.toFloat, 0, 1)

      var x = lerp(start.x, end.x, step)
      var y = lerp(start.y, end.y, step)

      if i != 0 && i != numSteps then
        x += Random.between(-randomRange, randomRange)
        y += Random.between(-randomRange, randomRange)

      Point(x, y)

  val pathData =
    spline(points, 1, false) + s"L ${end.x} $height L ${start.x} $height Z"

  path(
    d := pathData,
    fill := s"url(#$gradientId)"
  )

def clamp01(value: Float) =
  Math.min(1, Math.max(0, value))

case class Hsl(hue: Int, saturation: Float, lightness: Float):
  if (hue > 360) throw new IllegalArgumentException("Hue cannot be higher than 360")

  def analogous(results: Int = 6) =
    val slices = 30
    val part = 360 / slices // 12

    var hueVar = ((this.hue - (part * results >> 1)) + 720) % 360

    val res = for _ <- 1 to results yield
      hueVar = ((hueVar - (part * results >> 1)) + 720) % 360

      this.copy(hue = (hueVar + part) % 360)

    IndexedSeq(this) ++ res

  def darken(amount: Float = 10) =
    val lightness = clamp01((this.lightness / 100) - (amount / 100))

    this.copy(lightness = lightness * 100)

  def desaturate(amount: Float = 10) =
    val saturation = clamp01((this.saturation / 100) - amount / 100)

    this.copy(saturation = saturation * 100)

  def lighten(amount: Float = 10) =
    val lightness = clamp01((this.lightness / 100) + (amount / 100))

    this.copy(lightness = lightness * 100)

  // Spin takes a positive or negative amount within [-360, 360] indicating the change of hue.
  // Values outside of this range will be wrapped into this range.
  def spin(amount: Int = 10) =
    val hue = (this.hue + amount) % 360

    if hue < 0 then this.copy(hue = (360 + hue))
    else this.copy(hue = hue)

  override def toString =
    val saturation = this.saturation.toInt
    val lightness = this.lightness.toInt

    s"hsl($hue, $saturation%, $lightness%)"

  def toHex =
    val l = this.lightness / 100
    val a = this.saturation * Math.min(l, 1 - l) / 100

    val f = (n: Int) =>
      val k = (n + this.hue / 30) % 12
      val color = l - a * Math.max(Math.min(k - 3, 1), -1)

      Math.round(255 * color).toHexString.padTo(2, '0')

    s"#${f(0)}${f(8)}${f(4)}"

def generate =
  val numWaves = 7
  val baseHsl = Hsl(Random.between(0, 360), 65, 55)
  val colors = baseHsl.analogous(6)

  def getRandomColor =
    colors(Random.between(0, colors.length - 1))

  val rectSvg =
    rect(
      svgWidth := width,
      svgHeight := height,
      fill := getRandomColor.darken(40).toString
    )

  var gradients: IndexedSeq[ConcreteHtmlTag[String]] = IndexedSeq()

  val waves =
    for i <- 0 to numWaves - 1 yield
      val randomOffset = Random.between(-50, 50)
      val originY =
        map(
          i,
          0,
          numWaves.toFloat,
          -height.toFloat / 2,
          height.toFloat / 3
        ) + randomOffset
      val endY = map(i, 0, numWaves.toFloat, 0, 1000) + randomOffset

      val color =
        if i < 3 then getRandomColor.darken(50).desaturate(10)
        else getRandomColor

      val gradientOffset = map(i, 0, numWaves.toFloat, 0.1, 1)

      val gradientId = s"SvgjsLinearGradient100$i"

      val gradient =
        linearGradient(
          x1 := 0.5,
          y1 := 0,
          x2 := 0,
          y2 := 1,
          id := gradientId,
          Seq(
            stop(
              offset := 0,
              stopColor := color.lighten(30).toHex
            ),
            stop(
              offset := gradientOffset,
              stopColor := color.spin(60).toHex
            )
          )
        )

      gradients = gradients :+ gradient

      wave(Point(0, originY), Point(width.toFloat, endY), gradientId)

  Seq(rectSvg, defs(gradients)) ++ waves.toSeq

def output(children: Seq[ConcreteHtmlTag[String]]) =
  svg(
    id := "canvas",
    cls := "w-screen h-screen",
    viewBox := s"0 0 $width $height",
    preserveAspectRatio := "xMaxYMid slice",
    children
  )

output(generate)
