case class Hsl(hue: Float, saturation: Float, lightness: Float):
    // if (hue > 360) throw new IllegalArgumentException("Hue cannot be higher than 360")

    def analogous(results: Int = 6) =
        val slices = 30
        val part = 360 / slices // 12

        var results1 = results

        var hue = ((this.hue - (part * results1 >> 1)) + 720) % 360

        val res = for i <- 0 to results1 - 2 yield
            hue = (hue + part) % 360

            this.copy(hue = hue)

        this +: res

val baseHsl = Hsl(165, 65, 55)
// ["hsl(165, 65%, 55%)","hsl(141, 65%, 55%)","hsl(153, 65%, 55%)","hsl(165, 65%, 55%)","hsl(177, 65%, 55%)","hsl(189, 65%, 55%)"]

println(baseHsl.analogous(6))

// function analogous(color, results, slices) {
//     results = results || 6;
//     slices = slices || 30;

//     var hsl = tinycolor(color).toHsl();
//     var part = 360 / slices;
//     var ret = [tinycolor(color)];

//     for (hsl.h = ((hsl.h - (part * results >> 1)) + 720) % 360; --results; ) {
//         hsl.h = (hsl.h + part) % 360;
//         ret.push(tinycolor(hsl));
//     }
//     return ret;
// }