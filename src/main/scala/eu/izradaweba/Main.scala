package eu.izradaweba

import cats.effect.*
import com.comcast.ip4s.*
import org.http4s.{
  Charset,
  EntityEncoder,
  HttpApp,
  HttpRoutes,
  MediaType,
  UrlForm
}
import org.http4s.dsl.io.*
import org.http4s.ember.server.*
import org.http4s.server.middleware.Logger
import org.http4s.server.{Router, Server}
// import org.http4s.server.staticcontent.*
import eu.izradaweba.pages.{
  homePage,
  referencesPage,
  privacyNoticePage,
  creditsPage,
  contactPage,
  contactMessageValidationRules,
  messageReceivedPage,
  contactFieldNames
}
import eu.izradaweba.validation.{validate, ValidationStatus}
import org.http4s.Charset.`UTF-8`
import org.http4s.headers.`Content-Type`
import org.http4s.QueryParamDecoder
import org.http4s.dsl.impl.OptionalQueryParamDecoderMatcher
import org.http4s.dsl.impl.QueryParamDecoderMatcher

// import eu.izradaweba.mail.aws.v2.sendContactMessage
import eu.izradaweba.temp.resourceServiceBuilder

import org.http4s.scalatags.*

// Note: What does it mean "implicit"?
implicit val subjectQueryParamDecoder: QueryParamDecoder[Option[Tag]] =
  QueryParamDecoder[String].map(Tag.from)

object OptionalSubjectQueryParamMatcher
    extends OptionalQueryParamDecoderMatcher[Option[Tag]]("subject")

object Main extends IOApp {

  def routeService: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Route.Home.url =>
      Ok(homePage)
    case GET -> Route.References.url =>
      Ok(referencesPage)
    case GET -> Route.PrivacyNotice.url =>
      Ok(privacyNoticePage)
    // case GET -> Route.Credits.url =>
    //   Ok(creditsPage)
    case GET -> Route.Contact.url :? OptionalSubjectQueryParamMatcher(
          maybeMaybeSubject
        ) =>
      maybeMaybeSubject match
        case None               => Ok(contactPage())
        case Some(maybeSubject) => Ok(contactPage(querySubject = maybeSubject))
    case req @ POST -> Route.Contact.url =>
      req.decode[UrlForm] { data =>
        val validationStatus =
          validate(contactMessageValidationRules, data, contactFieldNames)

        validationStatus match
          // If validation passes send email, display success message.
          case ValidationStatus(true, data, _) =>
            // val contactMessage = ContactMessage(
            //   full_name = data.getOrElse,
            //   email_address = "test@example.com",
            //   subject = eu.izradaweba.Tag.WebStandard,
            //   message = "Ovo je test poruka.",
            //   gdpr_consent = true
            // )

            // sendContactMessage()

            Ok(messageReceivedPage)

          // If validation fails, display validation errors in form
          case ValidationStatus(false, _, errors) =>
            UnprocessableEntity(contactPage(data, errors))
      }
  }

  def httpApp: HttpApp[IO] =
    Router(
      "/" -> routeService,
      "/assets" -> resourceServiceBuilder[IO]("").toRoutes
    ).orNotFound

  def finalHttpApp: HttpApp[IO] = Logger.httpApp(true, true)(httpApp)

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(finalHttpApp)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
}
