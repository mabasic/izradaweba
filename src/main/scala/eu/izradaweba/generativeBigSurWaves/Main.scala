package eu.izradaweba.generativeBigSurWaves

/* Credits:
  - https://codepen.io/georgedoescode/pen/bGBzGKZ (Generative macOS Big Sur waves - codepen)
  - https://stackoverflow.com/questions/36721830/convert-hsl-to-rgb-and-hex (hsl to hex - function)
  - https://github.com/georgedoescode/generative-utils (A collection of handy generative art utilities - library)
  - https://github.com/bgrins/TinyColor (Fast, small color manipulation and conversion for JavaScript - library)
  - https://www.w3schools.com/colors/colors_hsl.asp (HSL Calculator - utility)
  - https://codeburst.io/svg-morphing-the-easy-way-and-the-hard-way-c117a620b65f (SVG animate tag - tutorial) */

/* Summary:
  I have took the codepen SVG generation code and libraries and have converted it to a scala package (this file) which
  can generate random Big Sur waves as SVG (scalatags) which can then be included in the website to avoid FOUC - Flash
  of un-styled content. The code produced (this file) is not a 100% copy of the code from the libraries and the resulting
  SVG is not 100% the same. I first started with Ints, then Floats, and have ended on using Doubles everywhere. I've
  modified the darken from 50 to 40 as it was producing strange colors. The waves are now animated by default using the
  animate tag. */

import java.awt.Color
import java.util.Date
import scala.util.Random
import scalatags.Text.all.*
import scalatags.Text.svgTags.{
  circle,
  defs,
  g,
  linearGradient,
  path,
  rect,
  stop,
  svg,
  animate
}
import scalatags.Text.svgAttrs.{
  values,
  calcMode,
  keySplines,
  attributeName,
  repeatCount,
  dur,
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

def map(n: Int, start1: Double, end1: Double, start2: Double, end2: Double) =
  ((n - start1) / (end1 - start1)) * (end2 - start2) + start2

def lerp(v0: Double, v1: Double, t: Double) =
  v0 * (1 - t) + v1 * t;

val width = 1920.0
val height = 1080.0

case class Point(x: Double, y: Double)

def formatPoints(points: Vector[Point], close: Boolean) =
  val points1 = points.flatMap(point => Vector(point.x, point.y))

  if close then
    val lastPoint = points1.last
    val secondToLastPoint = points1(points1.length - 2)

    val firstPoint = points1.head
    val secondPoint = points1(1)

    Vector(secondToLastPoint, lastPoint) ++ points1 ++ Vector(
      firstPoint,
      secondPoint
    )
  else points1

def spline(
    points0: Seq[Point],
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

  for i <- startIteration until maxIteration by inc do
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

def wave(
    start: Point,
    end: Point,
    gradientId: String,
    addAnimation: Boolean = true
) =
  val numSteps = Math.round(Random.between(4.0, 8.0))
  val randomRange = Random.between(32, 64)

  def points =
    for i <- 0 to numSteps.toInt yield
      val step = map(i, 0, numSteps.toDouble, 0, 1)

      var x = lerp(start.x, end.x, step)
      var y = lerp(start.y, end.y, step)

      if i != 0 && i != numSteps then
        x += Random.between(-randomRange, randomRange)
        y += Random.between(-randomRange, randomRange)

      Point(x, y)

  def pathData =
    spline(points) + s"L ${end.x.toInt} $height L ${start.x.toInt} $height Z"

  val wave1Path = pathData
  val waveAnimatedPath = pathData

  path(
    d := wave1Path,
    fill := s"url(#$gradientId)",
    if addAnimation then
      animate(
        dur := "4s",
        repeatCount := "indefinite",
        attributeName := "d",
        values := s"$wave1Path;$waveAnimatedPath;$wave1Path",
        calcMode := "spline",
        keySplines := "0.29 0.32 0.33 0.95; 0.29 0.32 0.33 0.95"
      )
    else ""
  )

def clamp01(value: Double) =
  Math.min(1, Math.max(0, value))

case class Hsl(hue: Int, saturation: Double, lightness: Double):
  if (hue > 360)
    throw new IllegalArgumentException("Hue cannot be higher than 360")
  if (saturation > 100)
    throw new IllegalArgumentException("Saturation cannot be higher than 100")
  if (lightness > 100)
    throw new IllegalArgumentException("Lightness cannot be higher than 100")

  def analogous(results: Int = 6): Seq[Hsl] =
    val slices = 30
    val part = 360 / slices // 12

    val results1 = results

    var hue = ((this.hue - (part * results1 >> 1)) + 720) % 360

    val res = for _ <- 0 to results1 - 2 yield
      hue = (hue + part) % 360

      this.copy(hue = hue)

    this +: res

  def darken(amount: Double = 10): Hsl =
    val lightness = clamp01((this.lightness / 100) - (amount / 100))

    this.copy(lightness = lightness * 100)

  def desaturate(amount: Double = 10): Hsl =
    val saturation = clamp01((this.saturation / 100) - (amount / 100))

    this.copy(saturation = saturation * 100)

  def lighten(amount: Double = 10): Hsl =
    val lightness = clamp01((this.lightness / 100) + (amount / 100))

    this.copy(lightness = lightness * 100)

  // Spin takes a positive or negative amount within [-360, 360] indicating the change of hue.
  // Values outside of this range will be wrapped into this range.
  def spin(amount: Int = 10): Hsl =
    val hue = (this.hue + amount) % 360

    if hue < 0 then this.copy(hue = 360 + hue)
    else this.copy(hue = hue)

  override def toString: String =
    val saturation = this.saturation.toInt
    val lightness = this.lightness.toInt

    s"hsl($hue, $saturation%, $lightness%)"

  def toHex: String =
    val l = this.lightness / 100
    val a = this.saturation * Math.min(l, 1 - l) / 100

    val f = (n: Double) =>
      val k = (n + this.hue / 30) % 12
      val color = l - a * Math.max(List(k - 3, 9 - k, 1).min, -1)

      Math.round(255 * color).toHexString.padTo(2, '0')

    s"#${f(0)}${f(8)}${f(4)}"

def generate =
  val numWaves = 7.0
  val baseHsl = Hsl(Random.between(0, 360), 65, 55)
  val colors = baseHsl.analogous()

  def getRandomColor =
    colors(Random.between(0, colors.length))

  val rectSvg =
    rect(
      svgWidth := width,
      svgHeight := height,
      fill := getRandomColor.darken(40).toString
    )

  var gradients: Seq[ConcreteHtmlTag[String]] = Seq()

  val waves =
    for i <- 0 until numWaves.toInt yield
      val randomOffset = Random.between(-50, 50)
      val originY =
        map(
          i,
          0,
          numWaves,
          -height / 2,
          height / 3
        ) + randomOffset
      val endY = map(i, 0, numWaves, 0, 1000) + randomOffset

      val color =
        if i < 3 then getRandomColor.darken(40).desaturate()
        else getRandomColor

      val gradientOffset = map(i, 0, numWaves, 0.1, 1)

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

      wave(Point(0, originY), Point(width, endY), gradientId)

  svg(
    id := "canvas",
    cls := "w-screen h-screen",
    viewBox := s"0 0 $width $height",
    preserveAspectRatio := "xMaxYMid slice",
    rectSvg,
    defs(gradients),
    waves
  )
