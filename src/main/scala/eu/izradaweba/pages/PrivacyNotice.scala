package eu.izradaweba.pages

import scalatags.Text.all.*
import scalatags.Text.tags2.{abbr, address}
import eu.izradaweba.{Route, references, Config}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences
import eu.izradaweba.typography as typo

import java.net.URL

val privacyNoticePageContent = Seq(
  typo.page(
    Seq(
      typo.pageTitle("Izjava o privatnosti"),
      typo.pageSubtitle(
        "Ova obavijest obvezuje nas na zaštitu privatnosti korisnika i osobnih podataka dostavljenih putem ove web stranice."
      ),
      typo.pageParagraph(
        "Ova obavijest pruža informacije o vrstama podataka koje možemo prikupiti od vas prilikom posjeta ovoj web stranici, objašnjava kako koristimo takve podatke i opisuje korake koje poduzimamo kako bi ih zaštitili. Obavijest također opisuje mogućnosti koje imate u vezi s prikupljanjem i korištenjem vaših podataka prilikom posjeta ovoj web stranici."
      ),
      typo.pageHeading("Forme"),
      typo.pageParagraph(
        Seq(
          "Podaci dostavljeni putem kontakt obrasca (ime i prezime, adresa e-pošte, predmet, poruka, privola) bit će pohranjeni u našem e-sandučiću na neodređeno vrijeme za potrebe buduće analize i kontaktiranja. Ako želite da izbrišemo vaše podatke iz našeg e-sandučića, pošaljite nam email na ",
          typo.outboundLink(
            text = Config.emailAddress,
            url = URL(s"mailto:${Config.emailAddress}"),
            includeRel = false
          ),
          "."
        )
      ),
      typo.pageHeading("Analitika"),
      typo.pageParagraph(
        Seq(
          "Koristimo uslugu ",
          typo.outboundLink(
            url = URL("https://plausible.io/"),
            text = "Plausible"
          ),
          " u svrhu prikupljanja i analiziranja posjećenosti web stranice. Plausible je softver za web analitiku otvorenog koda, napravljen u EU, bez kolačića, praćenja i prikupljanja osobnih podataka. ",
          typo.outboundLink(
            url = URL("https://plausible.laravelista.com/izradaweba.eu"),
            text = "Statistika je javno dostupna."
          )
        )
      ),
      typo.pageHeading("Kolačići"),
      typo.pageParagraph("Ova web stranica ne koristi kolačiće."),
      typo.pageHeading("Kontakt"),
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
        " ",
        typo.outboundLink(
          text = Config.emailAddress,
          url = URL(s"mailto:${Config.emailAddress}"),
          includeRel = false
        ),
        br(),
        abbr(title := "Web", "W:"),
        " ",
        typo.outboundLink(
          url = URL("https://mariobasic.com"),
          text = "mariobasic.com",
          includeRel = false
        )
      )
    )
  )
)

def privacyNoticePage =
  defaultLayout(
    privacyNoticePageContent,
    activeRoute = Route.PrivacyNotice,
    metaTitle = "Izjava o privatnosti"
  )
