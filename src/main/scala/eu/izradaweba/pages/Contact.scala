package eu.izradaweba.pages

import scalatags.Text.all.*
import scalatags.Text.tags2.{abbr, address}
import eu.izradaweba.{Route, references, Config}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences
import eu.izradaweba.typography as typo
import eu.izradaweba.validation.{
  ValidationRules,
  Email,
  validateString,
  validateEmail,
  validateTag,
  validateConsent,
  ValidationErrors,
  ValidatedData
}
import org.http4s.UrlForm

case class Subject(
    id: String,
    text: String
)

case class ContactMessage(
    full_name: String,
    email_address: Email,
    subject: eu.izradaweba.Tag,
    message: String,
    gdpr_consent: Boolean
)

val contactMessageValidationRules: ValidationRules = Map(
  "full_name" -> List(validateString),
  "email_address" -> List(validateEmail),
  "subject" -> List(validateTag),
  "message" -> List(validateString),
  "gdpr_consent" -> List(validateConsent)
)

def itemToSubject(item: Item) =
  Subject(text = item.name, id = item.tag.tag)

val productSubjects = products.map(itemToSubject)
val serviceSubjects = services.map(itemToSubject)

val subjects = (productSubjects ++ serviceSubjects).sortBy(_.text)

val requiredMark = span(cls := "text-red-500", "*")

def displayError(fieldName: String, errors: ValidationErrors): Modifier =
  if (errors.contains(fieldName)) then
    div(
      cls := "text-sm font-bold text-red-500 mt-2",
      for error <- errors.getOrElse(fieldName, List())
      yield error.getMessage()
    )
  else ""

def old(fieldName: String, oldData: UrlForm): Modifier =
  if oldData.values.contains(fieldName) then
    value := oldData.getFirstOrElse(fieldName, "")
  else ""

val messageReceivedPageContent = Seq(
  typo.page(
    Seq(
      typo.pageTitle("Uspjeh 💌"),
      typo.pageSubtitle("Hvala vam što ste nas kontaktirali."),
      typo.pageParagraph(
        "Primili smo vašu poruku i odgovoriti ćemo vam u najkraćem mogučem roku."
      )
    )
  )
)

def contactPageContent(oldData: UrlForm, errors: ValidationErrors) = Seq(
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
            autocomplete := "name",
            old("full_name", oldData)
          ),
          displayError("full_name", errors)
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
            cls := "bg-white/30 dark:bg-black/20 rounded-xl w-full block p-2",
            old("email_address", oldData)
          ),
          displayError("email_address", errors)
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
            option(
              value := "",
              "Odaberite predmet poruke",
              disabled,
              selected,
              hidden
            ),
            for subject <- subjects
            yield option(
              value := subject.id,
              subject.text,
              if oldData.values.contains("subject") && subject.id == oldData
                  .getFirstOrElse("subject", "")
              then selected := true
              else ""
            )
          ),
          displayError("subject", errors)
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
            cls := "bg-white/30 dark:bg-black/20 rounded-xl w-full block p-2 h-52",
            old("message", oldData)
          ),
          displayError("message", errors)
        ),
        div(
          cls := "mb-5",
          input(
            id := "gdpr_consent",
            `type` := "checkbox",
            name := "gdpr_consent",
            value := "on",
            cls := "mr-3",
            if oldData.values.contains("gdpr_consent") && oldData
                .getFirstOrElse("gdpr_consent", "") == "on"
            then checked := true
            else ""
          ),
          label(
            `for` := "gdpr_consent",
            "Pročitao/la sam ",
            typo.routeLink(Route.PrivacyNotice),
            " i dajem svoju privolu da me možete kontaktirati u vezi ove forme."
          ),
          displayError("gdpr_consent", errors)
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

def contactPage(
    oldData: UrlForm = UrlForm(),
    errors: ValidationErrors = Map()
) =
  defaultLayout(
    contactPageContent(oldData, errors),
    activeRoute = Route.Contact,
    metaTitle = "Kontakt"
  )

def messageReceivedPage =
  defaultLayout(
    messageReceivedPageContent,
    activeRoute = Route.Contact,
    metaTitle = "Kontakt"
  )
