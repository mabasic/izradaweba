case class Hsl(hue: Float, saturation: Float, lightness: Float):
    // if (hue > 360) throw new IllegalArgumentException("Hue cannot be higher than 360")

    def toHex =
        val l = this.lightness / 100
        val a = this.saturation * Math.min(l, 1 - l) / 100

        val f = (n: Int) => {
            val k = (n + this.hue / 30) % 12
            val color = l - a * Math.max(Math.min(k - 3, 1), -1)

            Math.round(255 * color).toHexString.padTo(2, '0')
        }

        s"#${f(0)}${f(8)}${f(4)}"

val baseHsl = Hsl(360444449, 50, 50)

println(baseHsl.toHex)

// 123.toHexString