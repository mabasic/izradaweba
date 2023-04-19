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
  messageReceivedPage,
  notFoundPage,
  ContactMessage
}
import org.http4s.Charset.`UTF-8`
import org.http4s.headers.`Content-Type`
import org.http4s.QueryParamDecoder
import org.http4s.dsl.impl.OptionalQueryParamDecoderMatcher
import org.http4s.dsl.impl.QueryParamDecoderMatcher

import eu.izradaweba.mail.aws.v2.sendContactMessage
import eu.izradaweba.temp.resourceServiceBuilder

import org.http4s.scalatags.*
import eu.izradaweba.validation.*
import org.scalactic.*
import org.scalactic.Accumulation.*
import eu.izradaweba.mail.aws.v2.sendContactMessage
import scala.util.Try

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
    // I have left this here because I did not want to delete it :).
    // The credits can be found in the project repository readme file.
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
        val fullName =
          parseString(data.getFirst("full_name"), "full_name", "ime i prezime")
        val emailAddress = parseEmail(
          data.getFirst("email_address"),
          "email_address",
          "email adresa"
        )
        val subject = parseTag(data.getFirst("subject"), "subject", "predmet")
        val message = parseString(data.getFirst("message"), "message", "poruka")
        val gdprConsent =
          parseConsent(data.getFirst("gdpr_consent"), "gdpr_consent", "privola")

        detectHoneypot(data.getFirst("first_name")) match
          case true =>
            Ok(messageReceivedPage())
          case false =>
            withGood(fullName, emailAddress, subject, message, gdprConsent) {
              ContactMessage(_, _, _, _, _)
            } match
              case Good(contactMessage) =>
                val response = Try(sendContactMessage(contactMessage))

                Ok(messageReceivedPage(Some(response)))
              case Bad(errors) =>
                UnprocessableEntity(contactPage(data, Some(errors)))
      }
    case req @ GET -> _ =>
      NotFound(notFoundPage)
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
