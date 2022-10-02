package eu.izradaweba.pages

import scalatags.Text.all.*
import scalatags.Text.tags2.{abbr, address}
import eu.izradaweba.{Route, references}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences
import eu.izradaweba.typography as typo

import java.net.URL

val index = Seq(
  div(
    cls := "text-base font-normal bg-content-bg dark:bg-dark-content-bg border border-solid border-theme-bg-color dark:border-dark-theme-bg-color transition duration-300 p-5 rounded-xl flex flex-col",
    h1(
      cls := "text-4xl font-bold",
      "Izjava o privatnosti"
    ),
    p(
      cls := "text-sm font-semibold my-2",
      "Ova obavijest obvezuje me na zaštitu privatnosti korisnika i osobnih podataka dostavljenih putem ove web stranice."
    ),
    p(
      cls := "my-3 text-base",
      "Ova obavijest pruža informacije o vrstama podataka koje mogu prikupiti od vas prilikom posjeta ovoj web stranici, objašnjava kako koristim takve podatke i opisuje korake koje poduzimam kako bi ih zaštitio. Obavijest također opisuje mogućnosti koje imate u vezi s prikupljanjem i korištenjem vaših podataka prilikom posjeta ovoj web stranici."
    ),
    h2(
      cls := "text-2xl font-bold mt-5",
      "Forme"
    ),
    p(
      cls := "my-3",
      "Podaci dostavljeni putem kontakt obrasca (ime i prezime, adresa e-pošte, predmet, poruka, privola) bit će pohranjeni u mojem e-sandučiću na neodređeno vrijeme za potrebe buduće analize i kontaktiranja. Ako želite da izbrišem vaše podatke iz mojeg e-sandučića, pošaljite mi email na ",
      typo.outboundLink(
        text = "mario@laravelista.hr",
        url = URL("mailto:mario@laravelista.hr"),
        includeRel = false
      ),
      "."
    ),
    h2(
      cls := "text-2xl font-bold mt-5",
      "Analitika"
    ),
    p(
      cls := "my-3",
      "I use a self-hosted version of ",
      typo.outboundLink(
        url = URL("https://plausible.io/"),
        text = "Plausible Analytics"
      ),
      " for the purpose of collecting and analyzing website visit frequency. It is an open source web analytics software, built in the EU, with no cookies, no tracking and no personal data collection. ",
      typo.outboundLink(url = URL("https://plausible.laravelista.com/izradaweba.eu"), text = "Stats are open to the public.")
    ),
    h2(
      cls := "text-2xl font-bold mt-5",
      "Kolačići"
    ),
    p(
      cls := "my-3",
      "Ova web stranica ne koristi kolačiće."
    ),
    h2(
      cls := "text-2xl font-bold mt-5",
      "Kontakt"
    ),
    address(
      cls := "my-3",
      b("Mario Bašić"),
      ", Laravelista",
      br(),
      "Markov Jose 1",
      br(),
      "Murter, HR 22243",
      br(),
      abbr(title := "E-mail", "E:"),
      " mario@laravelista.hr"
    )
  )
)

def privacyNoticePage =
  defaultLayout(
    index,
    activeRoute = Route.PrivacyNotice,
    metaTitle = "Izjava o privatnosti"
  )
