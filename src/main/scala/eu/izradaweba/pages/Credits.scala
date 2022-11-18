package eu.izradaweba.pages

import scalatags.Text.all.*
import scalatags.Text.tags2.{abbr, address}
import eu.izradaweba.{Route, references, Config}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences
import eu.izradaweba.typography as typo

import java.net.URL

val summary =
  """
  The design of this website was inspired by a codepen "Glassmorphism Creative Cloud App Redesign".
  The design style is called glassmorphism.
  I have translated the design line by line to tailwind CSS.
  There are minor subtle differences in order for me to adapt the codepen to my needs.
  """

val summary2 =
  """
  The background in the codepen is a highly optimized video.
  I did not like how it looped and I knew that I would be bored with it in the long run.
  I found an another codepen "Generative macOS Big Sur waves" which generated random macOS Big Sur waves as an SVG using JS.
  I copied the code and it looked great except for the massive FOUC (Flash of un-styled content) which was happening because JS needs to load and run in order to generate a background.
  The solution that I have found is to "translate" the JS code to Scala and have it generated server side on each request.
  This enabled me to avoid the FOUC and speed up the website because there was no need for JS for that. 
  """

val summary3 =
  """
  Having the background image be different on each request was great, but it was lacking animation.
  I have read a tutorial on SVG animations and have found a way to animate the waves using SVG animations instead of relying on JavaScript (which was my first idea).
  By using SVG animations I have reduced the CPU usage, and the animations are smooth.
  """

val summary4 =
  """
  The entire website is made with Scala and Tailwind CSS, even the JavaScript code is written using Scala and compiled to JavaScript.
  """

val summary5 =
  Seq(
    b(
      "Feel free to send a PR which translates the text above to Croatian language ",
      span(cls := "text-red-500", "♥"),
      "."
    )
  )

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
    href = URL(
      "https://stackoverflow.com/questions/36721830/convert-hsl-to-rgb-and-hex"
    ),
    title = "Hsl to hex - function"
  ),
  Credit(
    href = URL("https://github.com/georgedoescode/generative-utils"),
    title = "A collection of handy generative art utilities - library"
  ),
  Credit(
    href = URL("https://github.com/bgrins/TinyColor"),
    title =
      "Fast, small color manipulation and conversion for JavaScript - library"
  ),
  Credit(
    href = URL("https://www.w3schools.com/colors/colors_hsl.asp"),
    title = "HSL Calculator - utility"
  ),
  Credit(
    href = URL(
      "https://codeburst.io/svg-morphing-the-easy-way-and-the-hard-way-c117a620b65f"
    ),
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
      typo.pageSubtitle(
        "Prilikom izrade ove web stranice korišteni su dolje navedeni resursi za inspiraciju i ideju."
      ),
      ul(
        cls := "my-3",
        credits
          .sortBy(_.title)
          .map(credit =>
            li(
              typo.outboundLink(
                text = credit.title,
                url = credit.href
              )
            )
          )
      ),
      typo.pageParagraph(summary),
      typo.pageParagraph(summary2),
      typo.pageParagraph(summary3),
      typo.pageParagraph(summary4),
      typo.pageParagraph(summary5)
    )
  )
)

def creditsPage =
  defaultLayout(
    creditsPageContent,
    activeRoute = Route.Credits,
    metaTitle = "Zahvale"
  )
