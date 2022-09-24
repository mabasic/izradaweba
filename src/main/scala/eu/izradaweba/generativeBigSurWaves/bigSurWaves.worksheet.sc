import eu.izradaweba.generativeBigSurWaves.*
import scalatags.Text.all._
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

//val baseHsl = Hsl(165, 65, 55)
// ["hsl(165, 65%, 55%)","hsl(141, 65%, 55%)","hsl(153, 65%, 55%)","hsl(165, 65%, 55%)","hsl(177, 65%, 55%)","hsl(189, 65%, 55%)"]

path(
  d := "",
  fill := s"url(#)",
  if true then
    animate(
      dur := "5sec",
      repeatCount := "indefinite",
      attributeName := "d",
      values := s";;",
      fill := "freeze",
      calcMode := "spline",
      keySplines := "0.4 0 0.2 1; 0.4 0 0.2 1"
    )
  else ""
).toString
