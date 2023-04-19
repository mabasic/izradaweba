package eu.izradaweba.pages

import scalatags.Text.all.*
import scalatags.Text.tags2.{abbr, address}
import eu.izradaweba.{Route, references, Config}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences
import eu.izradaweba.typography as typo

import java.net.URL

val notFoundPageContent = Seq(
  typo.page(
    Seq(
      typo.pageTitle("Stranica nije pronađena"),
      typo.pageSubtitle(
        "Greška: HTTP-404"
      ),
      typo.pageParagraph(
        "Stranica koju tražite nije pronađena."
      ),
      typo.pageParagraph(
        Seq(
          a(
            href := Route.Home.url.toString,
            cls := "inline-block bg-content-button-color dark:bg-dark-content-button-color border-0 text-[#ffffff] cursor-pointer transition duration-300 whitespace-nowrap mt-4 rounded-2xl py-2 px-6 hover:bg-content-button-hover-color dark:hover:bg-dark-content-button-hover-color mt-2",
            "Povratak na naslovnu"
          )
        )
      )
    )
  )
)

def notFoundPage =
  defaultLayout(
    notFoundPageContent,
    activeRoute = None,
    metaTitle = "Page not found"
  )
