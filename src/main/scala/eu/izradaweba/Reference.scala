package eu.izradaweba

import java.net.URL
import java.net.Proxy

case class Reference(
    name: String,
    tag: Tag,
    url: Option[URL] = None,
    featured: Boolean = false,
    yearMade: Int
)

object Reference:
  def getUrl(reference: Reference): URL =
    reference.url match
      case Some(url) => url
      case None      => URL("#")

val references =
  import Tag.*

  List(
    Reference(
      "Kikolina",
      WebShop,
      Some(URL("https://kikolina.hr")),
      featured = true,
      yearMade = 2022
    ),
    Reference(
      "Bonaventura",
      BookingSystem,
      Some(URL("https://bonaventura.vip")),
      featured = true,
      yearMade = 2022
    ),
    Reference(
      "Quad Murter Kornati",
      WebStandard,
      Some(URL("https://quad-murterkornati.com")),
      featured = true,
      yearMade = 2022
    ),
    Reference(
      "Jet Ski Murter Kornati",
      WebStandard,
      Some(URL("https://jetski-murterkornati.com")),
      yearMade = 2021
    ),
    Reference(
      "Meling Gradnja",
      CustomSoftware,
      Some(URL("https://meling-gradnja.hr/")),
      yearMade = 2021
    ),
    Reference(
      "Stay in Adriatic",
      CustomSoftware,
      Some(URL("https://stayinadriatic.com")),
      featured = true,
      yearMade = 2020
    ),
    Reference(
      "Futurista",
      CustomSoftware,
      Some(URL("https://futurista.hr/")),
      yearMade = 2020
    ),
    Reference(
      "Private transfers \"Karoca\"",
      CustomSoftware,
      Some(URL("https://taxiotokmurter.com/")),
      yearMade = 2019
    ),
    Reference(
      "Butcher shop \"Milina\"",
      WebStandard,
      Some(URL("https://milina.hr/")),
      yearMade = 2019
    ),
    Reference(
      "Go Visit",
      WebStandard,
      Some(URL("https://govisit.hr/")),
      yearMade = 2018
    ),
    Reference(
      "Shipyard \"Ćiro\"",
      WebStandard,
      Some(URL("https://brodogradiliste-ciro.hr/")),
      yearMade = 2018
    ),
    Reference(
      "Damex",
      WebStandard,
      Some(URL("https://damex.hr/")),
      yearMade = 2018
    ),
    Reference(
      "Dragan Bašić",
      WebStandard,
      Some(URL("https://draganbasic.com/")),
      yearMade = 2018
    ),
    Reference(
      "VisitMurter",
      DirectoryListing,
      Some(URL("https://visitmurter.hr")),
      featured = true,
      yearMade = 2017
    ),
    Reference(
      "Car service \"Antušina\"",
      WebStandard,
      Some(URL("https://antusina.hr/")),
      yearMade = 2017
    ),
    Reference(
      "Apartments \"Milina\"",
      WebStandard,
      Some(URL("https://apartmanimilina.com/")),
      yearMade = 2017
    ),
    Reference(
      "Adriatica Consult",
      WebStandard,
      Some(URL("http://adriaticaconsult.com/")),
      yearMade = 2016
    ),
    Reference(
      "Studio \"Renata\"",
      WebStandard,
      Some(URL("http://studio-renata.hr/")),
      yearMade = 2016
    ),
    Reference(
      "Real estate agency \"Treva\"",
      CustomSoftware,
      Some(URL("https://nekretnine-treva.com/")),
      yearMade = 2016
    ),
    Reference(
      "Apartments \"Kornat\"",
      CustomSoftware,
      Some(URL("https://apartmani-kornat.com/")),
      yearMade = 2015
    ),
    Reference(
      "Apartments \"Slanica\"",
      CustomSoftware,
      Some(URL("https://murter-apartments.com/")),
      yearMade = 2015
    ),
    Reference(
      "Villa \"Moj San\"",
      CustomSoftware,
      Some(URL("http://apartmani-zablace.com/")),
      yearMade = 2015
    ),
    Reference(
      "G.I.M. Gase",
      BusinessApp,
      Some(URL("https://gimgase.hr")),
      featured = true,
      yearMade = 2015
    ),
    Reference(
      "Dance studio \"Plesni Koraci\"",
      WebStandard,
      Some(URL("https://plesnikoraci.com/")),
      yearMade = 2014
    ),
    Reference(
      "Hotel \"Murter\"",
      CustomSoftware,
      Some(URL("https://hotelmurter.com/")),
      yearMade = 2014
    ),
    Reference(
      "Real estate agency \"Adriatica\"",
      CustomSoftware,
      Some(URL("https://adriatica-nekretnine.hr/")),
      yearMade = 2014
    ),
    Reference(
      "Tourist agency \"Murterin\"",
      CustomSoftware,
      Some(URL("https://murterin.com/")),
      yearMade = 2013
    ),
    Reference(
      "Real estate agency \"Duos\"",
      CustomSoftware,
      Some(URL("https://duos-croatia.com/")),
      yearMade = 2013
    ),
    Reference(
      "Online store \"Daba\"",
      WebShop,
      Some(URL("https://daba.hr/")),
      yearMade = 2013
    ),
    Reference(
      "Virmanix",
      BusinessApp,
      Some(URL("https://virmanix.damex.hr/")),
      yearMade = 2013
    ),
    Reference(
      "Villa \"Renata\"",
      WebStandard,
      Some(URL("https://firstclassmurter.com/")),
      yearMade = 2012
    ),
    Reference(
      "Attorney \"Vjeran Paić\"",
      WebStandard,
      Some(URL("https://odvjetnik-vjeran-paic.com/")),
      yearMade = 2011
    )
  )
