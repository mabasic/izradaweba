package eu.izradaweba.pages

import scalatags.Text.all.*
import scalatags.Text.attrs.src
import scalatags.Text.tags.br
import scalatags.Text.svgTags.{path, svg}
import scalatags.Text.svgAttrs.{d, viewBox, fill}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.{Route, Reference, Tag, references, svgs}
import eu.izradaweba.partials.renderReferences
import eu.izradaweba.typography as typo

import java.net.URL

val heroSection =
  div(
    cls := "flex flex-col-reverse md:flex-row items-center w-full justify-between px-10 py-7 rounded-xl bg-content-wrapper-header",
    div(
      cls := "max-w-sm",
      div(
        cls := "flex items-center m-0 text-lg font-medium text-dark-theme-color",
        svg(
          cls := "w-7 mr-3.5",
          xmlns := "http://www.w3.org/2000/svg",
          viewBox := "0 0 576 512",
          path(
            fill := "currentColor",
            d := "M528 0h-480C21.5 0 0 21.5 0 48v320C0 394.5 21.5 416 48 416h192L224 464H152C138.8 464 128 474.8 128 488S138.8 512 152 512h272c13.25 0 24-10.75 24-24s-10.75-24-24-24H352L336 416h192c26.5 0 48-21.5 48-48v-320C576 21.5 554.5 0 528 0zM512 288H64V64h448V288z"
          )
        ),
        h1("Izrada Web stranica")
      ),
      div(
        cls := "text-sm font-normal mt-4 leading-7 text-content-text-color overflow-hidden text-ellipsis",
        "Web stranice prilagođene svim uređajima, optimizirane za tražilice, sa Lighthouse rezultatom u prosjeku preko 90/100, ",
        span(
          cls := "font-semibold",
          "po početnoj cijeni od 299,99€"
        ),
        "."
      ),
      a(
        href := Route.Contact.url.toString + s"?subject=${Tag.WebStandard.tag}",
        cls := "inline-block bg-content-button-color dark:bg-dark-content-button-color border-0 text-[#ffffff] cursor-pointer transition duration-300 whitespace-nowrap mt-4 rounded-2xl py-2 px-6 hover:bg-content-button-hover-color dark:hover:bg-dark-content-button-hover-color",
        "Pošaljite upit"
      )
    ),
    img(
      attr("width") := 300,
      attr("height") := 229,
      cls := "mb-4 md:mb-0 w-[300px]",
      src := "/assets/img/web-icon.svg",
      alt := "3D illustration of a website layout"
    )
  )

trait Item:
  val name: String
  val tag: Tag
  val description: Option[String]
  val descriptionHtml: Option[ConcreteHtmlTag[String]]

case class Service(
    name: String,
    tag: Tag,
    description: Option[String] = None,
    descriptionHtml: Option[ConcreteHtmlTag[String]] = None
) extends Item

val services =
  import eu.izradaweba.Tag.*

  List(
    Service(
      name = "Web stranice",
      tag = WebStandard,
      description = Some(
        "Izrada responzivnih web stranica prilagođenih svim uređajima, optimizirane za tražilice, sa Lighthouse rezultatom u prosjeku preko 90/100, po početnoj cijeni od 299,99€."
      )
    ),
    Service(
      name = "Softver po narudžbi",
      description = Some(
        "Izrada mobilnih aplikacija za iOS i android OS. Izrada skripti, desktop konzolnih i GUI aplikacija za Windows, macOS i Linux OS. Izrada web aplikacija, stranica, API-ja i serverless funkcija."
      ),
      tag = CustomSoftware
    ),
    Service(
      name = "Poslovne aplikacije",
      description = Some(
        "Izrada poslovnih aplikacija uključuje analizu poslovanja, detekciju poslovnih procesa, optimizaciju postojećih procesa i izradu informacijskog sustava."
      ),
      tag = BusinessApp
    )
  )

case class Product(
    name: String,
    tag: Tag,
    description: Option[String] = None,
    descriptionHtml: Option[ConcreteHtmlTag[String]] = None
) extends Item

val products =
  import eu.izradaweba.Tag.*

  List(
    Product(
      name = "Internet trgovina",
      descriptionHtml = Some(
        span(
          "U skladu sa Hrvatskim zakonom i u suradnji sa servisom za vođenje paušalnog obrta ",
          typo.outboundLink("Paušalko", URL("https://pausalko.com")),
          " donosimo vam Internet trgovinu potpuno prilagođenu vašim potrebama."
        )
      ),
      tag = WebShop
    ),
    Product(
      name = "Sustav za rezervacije",
      description = Some(
        "Iznamljujete apartmane ili kuću i želite ne ovisiti o drugim servisima za booking? Na jednom mjestu imajte web stranicu za prikupljanje gostiju i sustav za upravljanje rezervacijama."
      ),
      tag = BookingSystem
    ),
    Product(
      name = "Sustav za izlistaj",
      description = Some(
        "Geografski informacijski sustav (GIS) sa sustavom za upravljanje podacima (CMS), interaktivnom web stranicom i mobilnom aplikacijom, koji se može primjeniti na razna područja."
      ),
      tag = DirectoryListing
    )
  )

def itemSection(title: String, items: List[Item]) =
  def ctaButton(tag: Tag) =
    a(
      href := Route.Contact.url.toString + s"?subject=${tag.tag}",
      cls := "bg-content-button-color dark:bg-dark-content-button-color border-0 text-[#ffffff] cursor-pointer whitespace-nowrap transition duration-300 mt-4 py-1.5 px-6 rounded-2xl font-normal mt-0 text-sm hover:bg-content-button-hover-color dark:hover:bg-dark-content-button-hover-color",
      "Pošaljite upit"
    )

  div(
    cls := "mt-7 flex flex-col",
    div(
      cls := "text-content-title-color dark:text-dark-content-title-color mb-3.5",
      h2(title)
    ),
    div(
      cls := "grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 w-full",
      for item <- items
      yield div(
        cls := "font-medium bg-content-bg dark:bg-dark-content-bg border border-solid border-theme-bg-color dark:border-dark-theme-bg-color transition duration-300 p-5 rounded-xl hover:bg-theme-bg-color dark:hover:bg-dark-theme-bg-color hover:scale-[1.02] transform-gpu flex flex-col",
        div(
          cls := "flex items-center",
          item.tag.getSvg,
          div(
            h3(item.name),
            span(
              cls := "text-xs font-normal",
              item.tag.toString
            )
          )
        ),
        div(
          cls := "text-sm grow font-normal leading-6 mt-5 pb-5 border-b border-b-solid border-b-border-color dark:border-b-dark-border-color",
          item.descriptionHtml match
            case Some(description) => description
            case None =>
              item.description match
                case Some(description) => description
                case None              => ""
        ),
        div(
          cls := "flex items-center justify-end ml-auto mt-4",
          ctaButton(item.tag)
        )
      )
    )
  )

val featuredReferences =
  renderReferences(
    references
      .filter(_.featured)
      .sortBy(_.name)
      .reverse
      .sortBy(_.yearMade)
      .reverse
      .take(6),
    "Istaknute reference",
    isFeatured = true
  )

val home = Seq(
  heroSection,
  featuredReferences,
  itemSection("Usluge", services),
  itemSection("Proizvodi", products)
)

def homePage = defaultLayout(
  home,
  activeRoute = Route.Home,
  metaTitle = "Izrada web stranica",
  metaDescription = Some(
    "Web stranice prilagođene svim uređajima, optimizirane za tražilice, sa Lighthouse rezultatom u prosjeku preko 90/100, po početnoj cijeni od 299,99€."
  )
)
