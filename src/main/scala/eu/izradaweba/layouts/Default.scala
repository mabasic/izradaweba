package eu.izradaweba.layouts

import scalatags.Text.all.*
import scalatags.Text.tags2.title
import scalatags.Text.svgTags.{
  circle,
  defs,
  g,
  linearGradient,
  path,
  rect,
  stop,
  svg
}
import scalatags.Text.svgAttrs.{
  cx,
  cy,
  d,
  fill,
  gradientTransform,
  gradientUnits,
  offset,
  r,
  stroke,
  strokeLinecap,
  strokeLinejoin,
  strokeWidth,
  transform,
  viewBox,
  x,
  x1,
  x2,
  y,
  y1,
  y2,
  preserveAspectRatio
}
import eu.izradaweba.{Route, svgs, Config}
import org.http4s.Uri.Path
import eu.izradaweba.generativeBigSurWaves.{generate => generateBigSurWaves}
import eu.izradaweba.{typography => typo}

import java.net.URL

def bg(mode: ConcreteHtmlTag[String]) =
  div(
    cls := "fixed right-0 top-0 w-full h-full",
    span(
      cls := "absolute left-0 top-0 w-full h-screen backdrop-saturate-[3] bg-video-bg dark:hidden"
    ),
    mode
  )

def bgGenerativeBigSurWaves =
  bg(
    generateBigSurWaves
  )

def bgGenerativeBigSurWavesJS =
  bg(
    svg(
      id := "canvas",
      cls := "w-screen h-screen",
      viewBox := "0 0 1920 1080",
      preserveAspectRatio := "xMaxYMid slice"
    )
  )

val bgVideo =
  bg(
    video(
      attr("width") := "320",
      attr("height") := "240",
      attr("autoplay") := true,
      attr("loop") := true,
      attr("muted") := true,
      cls := "w-full h-full object-cover",
      source(
        src := "/assets/video/7btrrd.mp4",
        `type` := "video/mp4"
      ),
      "Your browser does not support the video tag."
    )
  )

val popup =
  div(
    id := "pop-up",
    cls := "absolute py-7 px-10 top-1/2 left-1/2 transition duration-300 z-[100] bg-popup-bg flex flex-col -translate-y-2/4 -translate-x-2/4 overflow-y-auto w-4/5 sm:w-[500px] whitespace-normal opacity-0 invisible rounded-md bg-popup-bg dark:bg-dark-popup-bg shadow-lg text-black dark:text-white transition duration-500 transform-gpu",
    div(
      cls := "pb-5 border-b border-b-solid border-b-theme-color dark:border-b-dark-border-color flex justify-between items-center",
      "Update This App",
      svg(
        cls := "w-6 mr-0 close-pop-up cursor-pointer",
        width := "24",
        height := "24",
        fill := "none",
        stroke := "currentColor",
        strokeWidth := "2",
        strokeLinecap := "round",
        strokeLinejoin := "round",
        circle(
          cx := "12",
          cy := "12",
          r := "10"
        ),
        path(
          d := "M15 9l-6 6M9 9l6 6"
        )
      )
    ),
    div(
      cls := "text-sm font-normal my-5 whitespace-normal leading-7",
      "Adjust your selections for advanced options as desired before continuing.",
      a(
        cls := "text-theme-color dark:text-dark-theme-color underline",
        href := "#",
        "Learn more"
      )
    ),
    div(
      cls := "flex items-center text-sm font-normal",
      input(
        `type` := "checkbox",
        id := "check1",
        cls := "mr-2"
      ),
      label(
        cls := "flex items-center",
        `for` := "check1",
        "Import previous settings and preferences"
      )
    ),
    div(
      cls := "flex items-center text-sm font-normal mr-0 mt-5 mb-10",
      input(
        `type` := "checkbox",
        id := "check2",
        cls := "mr-2"
      ),
      label(
        cls := "flex items-center",
        `for` := "check2",
        "Remove old versions"
      )
    ),
    div(
      cls := "mt-auto ml-auto",
      button(
        cls := "bg-content-button-color border-0 text-[#ffffff] cursor-pointer whitespace-nowrap transition duration-300 mt-4 py-1.5 px-6 rounded-2xl text-sm mt-0 bg-transparent mr-2 text-button-inactive dark:text-dark-button-inactive border border-solid border-button-inactive dark:border-dark-button-inactive close-pop-up",
        "Cancel"
      ),
      button(
        cls := "bg-content-button-color dark:bg-dark-content-button-color border-0 text-[#ffffff] cursor-pointer whitespace-nowrap transition duration-300 mt-4 py-1.5 px-6 rounded-2xl text-sm mt-0 hover:bg-content-button-hover-color dark:hover:bg-content-button-hover-color",
        "Continue"
      )
    )
  )

val logo =
  div(
    cls := "w-52 shrink-0",
    a(
      href := Route.Home.url.toString,
      raw(svgs.logo)
    )
  )

def menuItem(
    text: String,
    href: String = "#",
    isActive: Boolean = false,
    isMobile: Boolean = false
) =
  val activeCls =
    "py-4 px-7 no-underline border-b border-b-solid transition duration-300 hover:text-theme-color dark:hover:text-dark-theme-color hover:border-b-2 hover:border-b-solid hover:border-b-theme-color dark:hover:border-b-dark-theme-color text-theme-color dark:text-dark-theme-color border-b-2 border-b-theme-color dark:border-b-dark-theme-color font-medium"
  val inactiveCls =
    "py-4 px-7 no-underline text-inactive-color dark:text-dark-inactive-color border-b border-b-solid border-b-transparent transition duration-300 hover:text-theme-color dark:hover:text-dark-theme-color hover:border-b-2 hover:border-b-solid hover:border-b-theme-color dark:hover:border-b-dark-theme-color font-medium"

  val activeClsMobile =
    "no-underline text-theme-color dark:text-dark-theme-color p-2 font-semibold text-base transition duration-300 rounded-md hover:bg-hover-menu-bg dark:hover:bg-dark-hover-menu-bg"
  val inactiveClsMobile =
    "no-underline text-theme-color dark:text-dark-theme-color p-2 font-normal text-base transition duration-300 rounded-md hover:bg-hover-menu-bg dark:hover:bg-dark-hover-menu-bg"

  val itemClass =
    (isMobile, isActive) match
      case (true, true)   => activeClsMobile
      case (true, false)  => inactiveClsMobile
      case (false, true)  => activeCls
      case (false, false) => inactiveCls

  a(
    cls := itemClass,
    attr("href") := href,
    text
  )

def menu(activeRoute: Route) =
  div(
    cls := "items-center hidden md:flex",
    menuItem(
      Route.Home.name,
      href = Route.Home.url.toString,
      isActive = activeRoute == Route.Home
    ),
    menuItem(
      Route.References.name,
      href = Route.References.url.toString,
      isActive = activeRoute == Route.References
    ),
    menuItem(Route.Contact.name)
  )

val dot =
  div(
    cls := "w-1.5 h-1.5 bg-inactive-color dark:bg-dark-inactive-color group-hover:bg-theme-color dark:group-hover:bg-dark-theme-color rounded-full my-0 mx-0.5"
  )

val mobileNavbarToggle =
  button(
    id := "mobile-navbar-toggle",
    cls := "md:hidden flex py-3.5 px-2.5 rounded-lg group hover:bg-hover-menu-bg dark:hover:bg-dark-hover-menu-bg focus:bg-hover-menu-bg dark:focus:bg-dark-hover-menu-bg",
    dot,
    dot,
    dot
  )

def navbar(activeRoute: Route) =
  div(
    cls := "flex items-center justify-between shrink-0 h-14 w-full border-b border-solid border-border-color dark:border-dark-border-color px-7 py-0 whitespace-nowrap",
    logo,
    menu(activeRoute),
    mobileNavbarToggle
  )

def mobileNavbar(activeRoute: Route) =
  div(
    id := "mobile-navbar",
    cls := "overflow-auto p-6 shrink-0 hidden md:hidden",
    div(
      cls := "side-wrapper",
      div(
        cls := "mb-3.5 text-inactive-color dark:text-dark-inactive-color",
        "Navigacija"
      ),
      div(
        cls := "flex flex-col whitespace-nowrap",
        menuItem(
          Route.Home.name,
          isMobile = true,
          href = Route.Home.url.toString,
          isActive = activeRoute == Route.Home
        ),
        menuItem(
          Route.References.name,
          isMobile = true,
          href = Route.References.url.toString,
          isActive = activeRoute == Route.References
        ),
        menuItem(Route.Contact.name, isMobile = true)
      )
    )
  )

val overlay =
  div(
    id := "overlay-app",
    cls := "w-full h-full fixed left-0 top-0 pointer-events-auto opacity-0 invisible transition duration-300 bg-overlay-app transition duration-500 transform-gpu"
  )

val footer =
  div(
    cls := "flex flex-col lg:flex-row justify-between text-theme-color dark:text-dark-theme-color py-5 px-10 items-start lg:items-center",
    div(
      cls := "text-xs",
      "©️ Mario Bašić 2011-2022. Sva prava pridržana. Napravljeno sa ",
      typo.outboundLink("Scala", URL("https://scala-lang.org")),
      " i ",
      typo.outboundLink("Tailwind CSS", URL("https://tailwindcss.com")),
      "."
    ),
    div(
      cls := "text-xs mt-2 lg:mt-0 flex items-center justify-between",
      div(
        cls := "mr-2",
        typo.routeLink(Route.PrivacyNotice),
        typo.routeLink(Route.Credits),
        typo.outboundLink(
          "Izvorni kod",
          URL("https://github.com/mabasic/izradaweba"),
          includeRel = false
        )
      ),
      button(
        id := "theme-switch",
        cls := "p-2 rounded-full flex focus-visible:outline-none",
        svg(
          id := "theme-dark",
          fill := "currentColor",
          cls := "hidden w-6 shrink-0 dark:fill-dark-light-svg dark:stroke-dark-light-svg transition duration-500",
          viewBox := "0 0 512 512",
          xmlns := "http://www.w3.org/2000/svg",
          path(
            d := "M32 256c0-123.8 100.3-224 223.8-224c11.36 0 29.7 1.668 40.9 3.746c9.616 1.777 11.75 14.63 3.279 19.44C245 86.5 211.2 144.6 211.2 207.8c0 109.7 99.71 193 208.3 172.3c9.561-1.805 16.28 9.324 10.11 16.95C387.9 448.6 324.8 480 255.8 480C132.1 480 32 379.6 32 256z"
          )
        ),
        svg(
          id := "theme-light",
          fill := "currentColor",
          cls := "hidden w-6 shrink-0 dark:fill-dark-light-svg dark:stroke-dark-light-svg transition duration-500",
          viewBox := "0 0 512 512",
          xmlns := "http://www.w3.org/2000/svg",
          path(
            d := "M256 159.1c-53.02 0-95.1 42.98-95.1 95.1S202.1 351.1 256 351.1s95.1-42.98 95.1-95.1S309 159.1 256 159.1zM509.3 347L446.1 255.1l63.15-91.01c6.332-9.125 1.104-21.74-9.826-23.72l-109-19.7l-19.7-109c-1.975-10.93-14.59-16.16-23.72-9.824L256 65.89L164.1 2.736c-9.125-6.332-21.74-1.107-23.72 9.824L121.6 121.6L12.56 141.3C1.633 143.2-3.596 155.9 2.736 164.1L65.89 256l-63.15 91.01c-6.332 9.125-1.105 21.74 9.824 23.72l109 19.7l19.7 109c1.975 10.93 14.59 16.16 23.72 9.824L256 446.1l91.01 63.15c9.127 6.334 21.75 1.107 23.72-9.822l19.7-109l109-19.7C510.4 368.8 515.6 356.1 509.3 347zM256 383.1c-70.69 0-127.1-57.31-127.1-127.1c0-70.69 57.31-127.1 127.1-127.1s127.1 57.3 127.1 127.1C383.1 326.7 326.7 383.1 256 383.1z"
          )
        ),
        svg(
          id := "theme-automatic",
          fill := "currentColor",
          cls := "w-6 shrink-0 dark:fill-dark-light-svg dark:stroke-dark-light-svg transition duration-500",
          viewBox := "0 0 512 512",
          xmlns := "http://www.w3.org/2000/svg",
          path(
            d := "M512 256C512 397.4 397.4 512 256 512C114.6 512 0 397.4 0 256C0 114.6 114.6 0 256 0C397.4 0 512 114.6 512 256zM256 64V448C362 448 448 362 448 256C448 149.1 362 64 256 64z"
          )
        )
      )
    )
  )

// For historic purposes, the ability to choose the background mode.
enum BgMode:
  // This is the default which came with the codepen.
  case Video
  // This is my code which generates big sur waves svg based on the JS codepen
  case GenerativeBigSurWaves
  // This is the codepen code which generates big sur waves svg
  case GenerativeBigSurWavesJS

import BgMode.*

def defaultLayout(
    children: Seq[ConcreteHtmlTag[String]],
    activeRoute: Route,
    metaTitle: String,
    bgMode: BgMode = GenerativeBigSurWaves
) =

  def getBodyClass =
    bgMode match
      case Video => "bg-[url('/assets/img/bg.jpg')] bg-cover bg-center"
      case _     => ""

  doctype("html")(
    html(
      lang := "hr",
      head(
        title(metaTitle + " | IzradaWeba"),
        meta(charset := "UTF-8"),
        meta(
          name := "viewport",
          content := "width=device-width, initial-scale=1.0"
        ),
        link(href := "/assets/css/index.css", rel := "stylesheet"),
        Config.analyticsEnabled match
          case true => script(src := "https://plausible.laravelista.com/js/plausible.js", async, defer, attr("data-domain") := "izradaweba.eu")
          case _ => "",
        script(src := "/assets/js/main.js"),
        bgMode match
          case GenerativeBigSurWavesJS =>
            script(
              src := "/assets/libs/generativeBigSurWaves.mjs",
              `type` := "module"
            )
          case _ => ""
      ),
      body(
        cls := s"flex items-center sm:p-8 w-full min-h-screen justify-center flex-col $getBodyClass font-sans",
        bgMode match
          case Video                   => bgVideo
          case GenerativeBigSurWaves   => bgGenerativeBigSurWaves
          case GenerativeBigSurWavesJS => bgGenerativeBigSurWavesJS
        ,
        popup,
        div(
          cls := "bg-theme-bg-color grow dark:bg-dark-theme-bg-color max-w-7xl overflow-hidden w-full sm:rounded-xl backdrop-blur-lg flex flex-col",
          navbar(activeRoute),
          mobileNavbar(activeRoute),
          div(
            cls := "flex flex-col grow w-full font-medium",
            div(
              cls := "flex flex-col text-theme-color dark:text-dark-theme-color px-5 py-4 sm:px-10 sm:py-7 h-full overflow-auto bg-theme-bg-color dark:bg-dark-theme-bg-color grow",
              children
            ),
            footer
          )
        ),
        overlay
      )
    )
  )
