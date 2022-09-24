import eu.izradaweba.generativeBigSurWaves._

val baseHsl = Hsl(165, 65, 55)
// ["hsl(165, 65%, 55%)","hsl(141, 65%, 55%)","hsl(153, 65%, 55%)","hsl(165, 65%, 55%)","hsl(177, 65%, 55%)","hsl(189, 65%, 55%)"]

val darken = baseHsl.darken(50)
val desatured = darken.desaturate(10)

baseHsl.lighten(30)
baseHsl.spin(60)

println(baseHsl.analogous(6))
