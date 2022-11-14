package eu.izradaweba.pages

import scalatags.Text.all.*
import scalatags.Text.tags2.{abbr, address}
import eu.izradaweba.{Route, references, Config}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences
import eu.izradaweba.typography as typo

val contactPageContent = Seq(
  typo.page(
    Seq(
      typo.pageTitle("Kontaktiranje nas"),
      typo.pageSubtitle(
        "Pošaljite nam poruku ukoliko ste zainterestirani za neku od naših usluga ili proizvoda."
      ),
      typo.pageParagraph(
        Seq(
          "Polja označena sa ",
          span(cls := "text-red-600", "*"),
          " su obavezna."
        )
      ),
      form(
        method := "POST",
        action := Route.Contact.url.toString,
        input(
          `type` := "text",
          cls := "bg-white rounded"
        ),
        br(),
        input(
          `type` := "email"
        ),
        textarea(
        )
      )
    )
  )
)

def contactPage =
  defaultLayout(
    contactPageContent,
    activeRoute = Route.Contact,
    metaTitle = "Kontakt"
  )
