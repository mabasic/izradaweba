package eu.izradaweba.pages

import scalatags.Text.all.*
import scalatags.Text.tags2.{abbr, address}
import eu.izradaweba.{Route, references, Config}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences
import eu.izradaweba.typography as typo
import eu.izradaweba.validation.{Email, ValidationError}
import org.http4s.UrlForm
import org.scalactic.*
import scala.util.Try
import software.amazon.awssdk.services.sesv2.model.SendEmailResponse
import scala.util.Success
import scala.util.Failure

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

def itemToSubject(item: Item) =
  Subject(text = item.name, id = item.tag.tag)

val productSubjects = products.map(itemToSubject)
val serviceSubjects = services.map(itemToSubject)

val subjects = (productSubjects ++ serviceSubjects).sortBy(_.text)

val requiredMark = span(cls := "text-red-500", "*")

def displayError(
    inputName: String,
    maybeErrors: Option[Every[ValidationError]]
): Modifier =
  maybeErrors match
    case Some(errors) =>
      errors.find(error => error.inputName == inputName) match
        case Some(error) =>
          div(
            cls := "text-sm font-bold text-red-500 mt-2",
            error.message
          )
        case None => ""
    case None => ""

def old(fieldName: String, oldData: UrlForm): Modifier =
  if oldData.values.contains(fieldName) then
    value := oldData.getFirstOrElse(fieldName, "")
  else ""

def getSubject(
    oldData: UrlForm,
    subjectId: String,
    querySubject: Option[eu.izradaweba.Tag]
): Modifier =
  querySubject match
    case None =>
      if oldData.values.contains("subject") && subjectId == oldData
          .getFirstOrElse("subject", "")
      then selected := true
      else ""
    case Some(tag) =>
      if subjectId == tag.tag then selected := true
      else ""

val respondSuccess =
  Seq(
    typo.pageTitle("Uspjeh 💌"),
    typo.pageSubtitle("Hvala vam što ste nas kontaktirali."),
    typo.pageParagraph(
      "Primili smo vašu poruku i odgovoriti ćemo vam u najkraćem mogućem roku."
    )
  )

def messageReceivedPageContent(
    maybeEmailResponse: Option[Try[SendEmailResponse]]
) = Seq(
  typo.page(
    maybeEmailResponse match
      case Some(emailResponse) =>
        emailResponse match
          case Success(_) =>
            respondSuccess
          case Failure(exception) =>
            Seq(
              typo.pageTitle("Greška 🐞"),
              typo.pageSubtitle("Dogodila se greška prilikom slanja poruke."),
              typo.pageParagraph(exception.getMessage())
            )
      case None => respondSuccess
  )
)

def contactPageContent(
    oldData: UrlForm,
    errors: Option[Every[ValidationError]],
    querySubject: Option[eu.izradaweba.Tag]
) = Seq(
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
          cls := "mb-5 hidden",
          label(
            `for` := "name",
            "Ime",
            requiredMark
          ),
          input(
            id := "first_name",
            `type` := "text",
            name := "first_name",
            placeholder := "Upišite vaše ime",
            cls := "bg-white/30 dark:bg-black/20 rounded-xl w-full block p-2",
            autocomplete := "off"
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
              getSubject(oldData, subject.id, querySubject)
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
            if oldData.values.contains("message") then
              oldData.getFirstOrElse("message", "")
            else ""
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
    errors: Option[Every[ValidationError]] = None,
    querySubject: Option[eu.izradaweba.Tag] = None
) =
  defaultLayout(
    contactPageContent(oldData, errors, querySubject),
    activeRoute = Route.Contact,
    metaTitle = "Kontakt"
  )

def messageReceivedPage(emailResponse: Option[Try[SendEmailResponse]] = None) =
  defaultLayout(
    messageReceivedPageContent(emailResponse),
    activeRoute = Route.Contact,
    metaTitle = "Kontakt"
  )
