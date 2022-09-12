package eu.izradaweba

import scalatags.Text.all.s
import scalatags.Text.TypedTag
import java.net.URL

import eu.izradaweba.svgs

enum Tag(val tag: String):
  case WebShop extends Tag("web-shop")
  case BusinessApp extends Tag("business-app")
  case DirectoryListing extends Tag("directory-listing")
  case BookingSystem extends Tag("booking-system")
  case CustomSoftware extends Tag("custom-software")
  case WebStandard extends Tag("web-standard")

  def getSvg: TypedTag[String] =
    this match {
      case WebShop => svgs.webShop
      case BusinessApp => svgs.businessApp
      case DirectoryListing => svgs.directoryListing
      case BookingSystem => svgs.bookingSystem
      case CustomSoftware => svgs.customSoftware
      case WebStandard => svgs.webStandard
    }

  override def toString: String =
    s"#$tag"

case class Reference(name: String, tag: Tag, url: Option[URL] = None)

val references =
  import Tag._

  List(
    Reference("Kikolina", WebShop, Some(URL("https://kikolina.hr"))),
    Reference("Bonaventura", BookingSystem, Some(URL("https://bonaventura.vip"))),
    Reference("VisitMurter", DirectoryListing, Some(URL("https://visitmurter.hr"))),
    Reference("Stay in Adriatic", CustomSoftware, Some(URL("https://stayinadriatic.com"))),
    Reference("G.I.M. Gase", BusinessApp, Some(URL("https://gimgase.hr"))),
    Reference("Jet Ski Murter Kornati", WebStandard, Some(URL("https://jetski-murterkornati.com"))),
  )