package eu.izradaweba

import cats.effect.*
import com.comcast.ip4s.*
import org.http4s.{HttpApp, HttpRoutes}
import org.http4s.dsl.io.*
import org.http4s.ember.server.*
import scalatags.Text.all.*
import scalatags.Text.tags2.title
import org.http4s.scalatags.*
import org.http4s.server.middleware.Logger
import org.http4s.server.{Router, Server}
import org.http4s.server.staticcontent.*

val response = html(
  head(
    title("Scalatags + http4s FTW"),
    script(src := "/assets/js/main.js", async := true)
  ),
  body(
    h1("This is my title"),
    div(
      p("This is my first paragraph"),
      p("This is my second paragraph")
    )
  )
)

object Main extends IOApp {

  val helloWorldService: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root =>
      Ok(response)
  }

  val httpApp: HttpApp[IO] =
    Router(
      "/" -> helloWorldService,
      "/assets" -> fileService(FileService.Config("./src/main/resources/"))
    ).orNotFound

  val finalHttpApp: HttpApp[IO] = Logger.httpApp(true, true)(httpApp)

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
