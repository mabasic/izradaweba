package eu.izradaweba.pages

import scalatags.Text.all.*
import scalatags.Text.tags2.{abbr, address}
import eu.izradaweba.{Route, references, Config}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences
import eu.izradaweba.typography as typo

case class Subject(
    id: String,
    text: String
)

def itemToSubject(item: Item) =
  Subject(text = item.name, id = item.tag.tag)

val productSubjects = products.map(itemToSubject)
val serviceSubjects = services.map(itemToSubject)

val subjects = (productSubjects ++ serviceSubjects).sortBy(_.text)

val requiredMark = span(cls := "text-red-500", "*")

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
          requiredMark,
          " su obavezna."
        )
      ),
      form(
        method := "POST",
        cls := "mt-4",
        action := Route.Contact.url.toString,
        div(
          cls := "mb-5",
          label(
            `for` := "name",
            "Ime i prezime",
            requiredMark
          ),
          input(
            id := "name",
            `type` := "text",
            name := "full_name",
            placeholder := "Upišite vaše ime i prezime",
            cls := "bg-white/30 dark:bg-black/20 rounded-xl w-full block p-2",
            autocomplete := "name"
          ),
          div(
            cls := "text-sm font-bold text-red-500 mt-2",
            "Ovo je neka greška."
          )
        ),
        div(
          cls := "mb-5",
          label(
            `for` := "email",
            "Email adresa",
            requiredMark
          ),
          input(
            id := "email",
            `type` := "email",
            name := "email_address",
            autocomplete := "email",
            placeholder := "Upišite vašu email adresu",
            cls := "bg-white/30 dark:bg-black/20 rounded-xl w-full block p-2"
          )
        ),
        div(
          cls := "mb-5",
          label(
            `for` := "subject",
            "Predmet",
            requiredMark
          ),
          select(
            id := "subject",
            name := "subject",
            cls := "bg-white/30 dark:bg-black/20 text-black dark:text-white rounded-xl w-full block p-2",
            option(value := "", "Odaberite predmet poruke"),
            for subject <- subjects
            yield option(
              value := subject.id,
              subject.text
            )
          )
        ),
        div(
          cls := "mb-5",
          label(
            `for` := "message",
            "Poruka",
            requiredMark
          ),
          textarea(
            id := "message",
            name := "message",
            placeholder := "Upišite vašu poruku",
            cls := "bg-white/30 dark:bg-black/20 rounded-xl w-full block p-2 h-52"
          )
        ),
        div(
          cls := "mb-5",
          input(
            id := "gdpr_consent",
            `type` := "checkbox",
            name := "gdpr_consent",
            value := "on",
            cls := "mr-3"
          ),
          label(
            `for` := "gdpr_consent",
            "Pročitao/la sam ",
            typo.routeLink(Route.PrivacyNotice),
            " i dajem svoju privolu da me možete kontaktirati u vezi ove forme."
          )
        ),
        button(
          `type` := "submit",
          cls := "bg-content-button-color dark:bg-dark-content-button-color border-0 text-[#ffffff] cursor-pointer transition duration-300 whitespace-nowrap mt-4 rounded-2xl py-2 px-6 hover:bg-content-button-hover-color dark:hover:bg-dark-content-button-hover-color",
          "Pošaljite poruku 💌"
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
