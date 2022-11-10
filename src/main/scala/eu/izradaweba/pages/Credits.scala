package eu.izradaweba.pages

import scalatags.Text.all.*
import scalatags.Text.tags2.{abbr, address}
import eu.izradaweba.{Route, references, Config}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences
import eu.izradaweba.typography as typo

import java.net.URL

val summary = 
  """I have took the codepen SVG generation code and libraries and have converted it to a scala package (this file) which
 can generate random Big Sur waves as SVG (scalatags) which can then be included in the website to avoid FOUC - Flash
 of un-styled content. The code produced (this file) is not a 100% copy of the code from the libraries and the resulting
 SVG is not 100% the same. I first started with Ints, then Floats, and have ended on using Doubles everywhere. I've
 modified the darken from 50 to 40 as it was producing strange colors. The waves are now animated by default using the
 animate tag."""

case class Credit(
  href: URL,
  title: String
)

val credits = List(
  Credit(
    href = URL("https://codepen.io/georgedoescode/pen/bGBzGKZ"),
    title = "Generative macOS Big Sur waves - codepen"
  ),
  Credit(
    href = URL("https://stackoverflow.com/questions/36721830/convert-hsl-to-rgb-and-hex"),
    title = "Hsl to hex - function"
  ),
  Credit(
    href = URL("https://github.com/georgedoescode/generative-utils"),
    title = "A collection of handy generative art utilities - library"
  ),
  Credit(
    href = URL("https://github.com/bgrins/TinyColor"),
    title = "Fast, small color manipulation and conversion for JavaScript - library"
  ),
  Credit(
    href = URL("https://www.w3schools.com/colors/colors_hsl.asp"),
    title = "HSL Calculator - utility"
  ),
  Credit(
    href = URL("https://codeburst.io/svg-morphing-the-easy-way-and-the-hard-way-c117a620b65f"),
    title = "SVG animate tag - tutorial"
  ),
  Credit(
    href = URL("https://codepen.io/TurkAysenur/pen/ZEpxeYm"),
    title = "Glassmorphism Creative Cloud App Redesign - codepen"
  )
)

val creditsPageContent = Seq(
  typo.page(
    Seq(
      typo.pageTitle("Zahvale"),
      typo.pageSubtitle("Prilikom izrade ove web stranice korišteni su dolje navedeni resursi za inspiraciju i ideju."),
      ul(
        credits.sortBy(_.title).map(credit => li(typo.outboundLink(
          text = credit.title,
          url = credit.href
        )))
      ),
      typo.pageParagraph(summary)
    )
  )
)

def creditsPage =
  defaultLayout(
    creditsPageContent,
    activeRoute = Route.Credits,
    metaTitle = "Zahvale"
  )
