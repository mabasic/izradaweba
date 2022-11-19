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
import scalatags.text.Builder
import scalatags.Text.all.*
import scalatags.Text.tags2.title
import org.http4s.server.middleware.Logger
import org.http4s.server.{Router, Server}
import org.http4s.server.staticcontent.*
import eu.izradaweba.pages.{
  homePage,
  referencesPage,
  privacyNoticePage,
  creditsPage,
  contactPage,
  contactMessageValidationRules,
  messageReceivedPage
}
import eu.izradaweba.validation.{validate, ValidationStatus}
import org.http4s.Charset.`UTF-8`
import org.http4s.headers.`Content-Type`

/** When the http4s-scalatags package gets a new release with my PR merged then
  * this helper function can be replaced with:
  *
  * ```
  * import org.http4s.scalatags.*
  * Ok(homePage)
  * ```
  *
  * @param output
  *   HTML doctype
  * @return
  *   It returns a response from scalatags.
  */
def Ok(output: doctype) =
  org.http4s.dsl.io
    .Ok(output.render, `Content-Type`(MediaType.text.html, `UTF-8`))

def UnprocessableEntity(output: doctype) =
  org.http4s.dsl.io
    .UnprocessableEntity(
      output.render,
      `Content-Type`(MediaType.text.html, `UTF-8`)
    )

object Main extends IOApp {

  def routeService: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Route.Home.url =>
      Ok(homePage)
    case GET -> Route.References.url =>
      Ok(referencesPage)
    case GET -> Route.PrivacyNotice.url =>
      Ok(privacyNoticePage)
    case GET -> Route.Credits.url =>
      Ok(creditsPage)
    case GET -> Route.Contact.url =>
      Ok(contactPage())
    case req @ POST -> Route.Contact.url =>
      req.decode[IO, UrlForm] { data =>
        val validationStatus = validate(contactMessageValidationRules, data)

        validationStatus match
          // If validation passes send email, display success message.
          case ValidationStatus(true, data, _) =>
            Ok(messageReceivedPage)

          // If validation fails, display validation errors in form
          case ValidationStatus(false, _, errors) =>
            UnprocessableEntity(contactPage(data, errors))
      }
  }

  def httpApp: HttpApp[IO] =
    Router(
      "/" -> routeService,
      "/assets" -> fileService(FileService.Config("./src/main/resources/"))
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
