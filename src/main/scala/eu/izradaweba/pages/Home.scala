package eu.izradaweba.pages

import scalatags.Text.all.*
import scalatags.Text.attrs.src
import scalatags.Text.tags.br
import scalatags.Text.svgTags.{path, svg}
import scalatags.Text.svgAttrs.{d, viewBox}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.svgs
import scalatags.Text

import java.net.URL

val heroSection =
  div(
    cls := "flex flex-col-reverse sm:flex-row items-center w-full justify-between px-10 py-7 rounded-xl bg-content-wrapper-header",
    div(
      cls := "max-w-sm",
      div(
        cls := "flex items-center m-0 text-lg font-medium",
        svg(
          cls := "w-7 mr-3.5",
          xmlns := "http://www.w3.org/2000/svg",
          viewBox := "0 0 576 512",
          path(
            d := "M528 0h-480C21.5 0 0 21.5 0 48v320C0 394.5 21.5 416 48 416h192L224 464H152C138.8 464 128 474.8 128 488S138.8 512 152 512h272c13.25 0 24-10.75 24-24s-10.75-24-24-24H352L336 416h192c26.5 0 48-21.5 48-48v-320C576 21.5 554.5 0 528 0zM512 288H64V64h448V288z"
          )
        ),
        h1("Izrada Web stranica")
      ),
      div(
        cls := "text-sm font-normal mt-4 leading-7 text-content-text-color overflow-hidden text-ellipsis",
        "Grab yourself 10 free images from Adobe Stock in a 30-day free trial plan and find perfect image, that will help you with your new project."
      ),
      button(
        cls := "bg-content-button-color dark:bg-dark-content-button-color border-0 text-[#ffffff] cursor-pointer transition duration-300 whitespace-nowrap mt-4 rounded-2xl py-2 px-6 hover:bg-content-button-hover-color dark:hover:bg-dark-content-button-hover-color",
        "Pošaljite upit"
      )
    ),
    img(
      cls := "w-44 -mt-6 object-cover object-center mb-4 sm:mb-0",
      src := "/assets/img/glass.png"
    )
  )

enum Tag(val tag: String):
  case WebShop extends Tag("web-shop")
  case BusinessApp extends Tag("business-app")
  case DirectoryListing extends Tag("directory-listing")
  case BookingSystem extends Tag("booking-system")
  case CustomSoftware extends Tag("custom-software")
  case WebStandard extends Tag("web-standard")

  def getSvg: Text.TypedTag[String] =
    this match {
      case WebShop => svgs.webshop
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
    Reference("Jet Ski Murter Kornati", WebStandard, Some(URL("https://kikolina.hr"))),
  )

val featuredReferences =
  def tag(tag: Tag) =
    span(
      cls := "ml-auto w-32 relative font-normal md:hidden text-xs",
      tag.toString
    )

  val seeMoreButton =
    button(
      cls := "border-0 cursor-pointer whitespace-nowrap transition duration-300 mt-4 py-1.5 px-6 rounded-2xl font-normal mt-0 text-sm bg-transparent mr-2 text-button-inactive dark:text-dark-button-inactive border border-solid border-button-inactive dark:border-dark-button-inactive hidden sm:block hover:text-black hover:border-black dark:hover:text-white dark:hover:border-white open-pop-up",
      "Pogledajte"
    )

  def itemHeading(reference: Reference) =
    val url = reference.url match
      case Some(url) => url.toString
      case None => "#"

    div(
      a(
        href := url,
        rel := "noopener nofollow",
        target := "_blank",
        reference.name
      ),
      br(),
      tag(reference.tag)
    )

  def itemTag(tag: Tag) =
    div(
      cls := "ml-auto w-32 relative font-normal hidden md:inline text-sm",
      tag.toString
    )

  val itemActions =
    div(
      cls := "flex items-center justify-end ml-auto w-44",
      seeMoreButton
    )

  div(
    cls := "mt-7 flex flex-col",
    div(
      cls := "text-content-title-color dark:text-dark-content-title-color mb-3.5 flex justify-between",
      h2("Istaknute reference"),
      div(
        cls := "hidden sm:block self-center",
        a(
          cls := "text-xs hover:text-black dark:hover:text-white",
          href := "#",
          "Pogledajte sve reference"
        )
      )
    ),
    ul(
      cls := "flex flex-col w-full h-full justify-around bg-theme-bg-color dark:bg-dark-content-bg pl-0 m-0 rounded-xl border border-solid border-theme-bg-color dark:border-dark-theme-bg-color",
      for reference <- references yield
        li(
          cls := "transition duration-300 transform-gpu first:rounded-t-xl last:rounded-b-xl hover:bg-theme-bg-color dark:hover:bg-dark-theme-bg-color flex items-center w-full h-full whitespace-nowrap py-2.5 px-4 text-base border-t border-t-solid border-t-border-color dark:border-t-dark-border-color first:border-0",
          div(
            cls := "flex items-center w-36",
            reference.tag.getSvg,
            itemHeading(reference)
          ),
          itemTag(reference.tag),
          itemActions
        )
    )
  )

trait Item:
  val name: String
  val description: String
  val tag: Tag

case class Service(name: String, description: String, tag: Tag) extends Item

val services =
  import Tag._

  List(
    Service(
      name = "Web stranice",
      description = "Edit, master and create fully professional videos",
      tag = WebStandard
    ),
    Service(
      name = "Softver po narudžbi",
      description = "Edit, master and create fully professional videos",
      tag = CustomSoftware
    ),
    Service(
      name = "Poslovne aplikacije",
      description = "Edit, master and create fully professional videos",
      tag = BusinessApp
    ),
  )

case class Product(name: String, description: String, tag: Tag) extends Item

val products =
  import Tag._

  List(
    Product(
      name = "Internet trgovina",
      description = "Edit, master and create fully professional videos",
      tag = WebShop
    ),
    Product(
      name = "Sustav za rezervacije",
      description = "Edit, master and create fully professional videos",
      tag = BookingSystem
    ),
    Product(
      name = "Sustav za izlistaj",
      description = "Edit, master and create fully professional videos",
      tag = DirectoryListing
    ),
  )

def itemSection(title: String, items: List[Item]) =
  val serviceButton =
    button(
      cls := "bg-content-button-color dark:bg-dark-content-button-color border-0 text-[#ffffff] cursor-pointer whitespace-nowrap transition duration-300 mt-4 py-1.5 px-6 rounded-2xl font-normal mt-0 text-sm hover:bg-content-button-hover-color dark:hover:bg-dark-content-button-hover-color",
      "Pošaljite upit"
    )

  div(
    cls := "mt-7 flex flex-col",
    div(
      cls := "text-content-title-color dark:text-dark-content-title-color mb-3.5",
      h2(title),
    ),
    div(
      cls := "grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 w-full",
      for item <- items yield
        div(
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
            item.description
          ),
          div(
            cls := "flex items-center justify-end ml-auto mt-4",
            serviceButton
          )
        )
    )
  )

val home =
  div(
    cls := "flex flex-col text-theme-color dark:text-dark-theme-color px-5 py-4 sm:px-10 sm:py-7 h-full overflow-auto bg-theme-bg-color dark:bg-dark-theme-bg-color",
    heroSection,
    featuredReferences,
    itemSection("Usluge", services),
    itemSection("Proizvodi", products)
  )

val homePage = defaultLayout(Seq(home))