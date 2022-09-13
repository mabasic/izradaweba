package eu.izradaweba

import java.net.URL

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