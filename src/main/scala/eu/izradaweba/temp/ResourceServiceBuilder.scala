package eu.izradaweba.temp

import cats.data.Kleisli
import cats.data.OptionT
import cats.effect.Async
import cats.syntax.all._
import org.http4s.server.middleware.TranslateUri
import org.log4s.getLogger

import java.nio.file.Paths
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import scala.util.control.NoStackTrace
import org.http4s.server.staticcontent.CacheStrategy
import org.http4s.HttpRoutes
import org.http4s.StaticFile
import org.http4s.Response
import org.http4s.Status
import org.http4s.server.staticcontent.NoopCacheStrategy

// Note: This entire file can be removed once this issue is solved:
// https://github.com/http4s/http4s/issues/5240
class ResourceServiceBuilder[F[_]] private (
    basePath: String,
    pathPrefix: String,
    bufferSize: Int,
    cacheStrategy: CacheStrategy[F],
    preferGzipped: Boolean,
    classLoader: Option[ClassLoader],
) {
  private[this] val logger = getLogger

  private def copy(
      basePath: String = basePath,
      pathPrefix: String = pathPrefix,
      bufferSize: Int = bufferSize,
      cacheStrategy: CacheStrategy[F] = cacheStrategy,
      preferGzipped: Boolean = preferGzipped,
      classLoader: Option[ClassLoader] = classLoader,
  ): ResourceServiceBuilder[F] =
    new ResourceServiceBuilder[F](
      basePath,
      pathPrefix,
      bufferSize,
      cacheStrategy,
      preferGzipped,
      classLoader,
    )

  def withBasePath(basePath: String): ResourceServiceBuilder[F] = copy(basePath = basePath)
  def withPathPrefix(pathPrefix: String): ResourceServiceBuilder[F] =
    copy(pathPrefix = pathPrefix)

  def withCacheStrategy(cacheStrategy: CacheStrategy[F]): ResourceServiceBuilder[F] =
    copy(cacheStrategy = cacheStrategy)

  def withPreferGzipped(preferGzipped: Boolean): ResourceServiceBuilder[F] =
    copy(preferGzipped = preferGzipped)

  def withClassLoader(classLoader: Option[ClassLoader]): ResourceServiceBuilder[F] =
    copy(classLoader = classLoader)

  def withBufferSize(bufferSize: Int): ResourceServiceBuilder[F] = copy(bufferSize = bufferSize)

  def toRoutes(implicit F: Async[F]): HttpRoutes[F] = {
    val basePath = if (this.basePath.isEmpty) "/" else this.basePath
    object BadTraversal extends Exception with NoStackTrace

    Try(Paths.get(basePath)) match {
      case Success(rootPath) =>
        TranslateUri(pathPrefix)(Kleisli {
          case request if request.pathInfo.nonEmpty =>
            val segments = request.pathInfo.segments.map(_.decoded(plusIsSpace = true))
            OptionT
              .liftF(F.catchNonFatal {
                segments.foldLeft(rootPath) {
                  case (_, "" | "." | "..") => throw BadTraversal
                  case (path, segment) =>
                    path.resolve(segment)
                }
              })
              .collect {
                case path if path.startsWith(rootPath) => path
              }
              .flatMap { path =>
                StaticFile.fromResource(
                  // Note: This entire file can be removed once this issue is solved:
                  // https://github.com/http4s/http4s/issues/5240
                  path.toString.replaceAll("\\\\", "/"),
                  Some(request),
                  preferGzipped = preferGzipped,
                  classLoader,
                )
              }
              .semiflatMap(cacheStrategy.cache(request.pathInfo, _))
              .recoverWith { case BadTraversal =>
                OptionT.some(Response(Status.BadRequest))
              }
          case _ => OptionT.none
        })

      case Failure(e) =>
        logger.error(e)(
          s"Could not get root path from ResourceService config: basePath = $basePath, pathPrefix = $pathPrefix. All requests will fail."
        )
        Kleisli(_ => OptionT.pure(Response(Status.InternalServerError)))
    }
  }
}

object ResourceServiceBuilder {
  def apply[F[_]](basePath: String): ResourceServiceBuilder[F] =
    new ResourceServiceBuilder[F](
      basePath = basePath,
      pathPrefix = "",
      bufferSize = 50 * 1024,
      cacheStrategy = NoopCacheStrategy[F],
      preferGzipped = false,
      classLoader = None,
    )
}

def resourceServiceBuilder[F[_]](basePath: String): ResourceServiceBuilder[F] =
  ResourceServiceBuilder[F](basePath)
