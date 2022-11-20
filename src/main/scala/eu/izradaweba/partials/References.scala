package eu.izradaweba.partials

import eu.izradaweba.{Route, Reference, Tag}
import scalatags.Text.all.*
import java.net.URL

def renderReferences(
    references: List[Reference],
    heading: String,
    headingTag: ConcreteHtmlTag[String] = h2,
    isFeatured: Boolean = false
) =
  def tag(tag: Tag) =
    span(
      cls := "ml-auto w-32 relative font-normal md:hidden text-xs",
      tag.toString
    )

  /** Opens a modal pop-up with generic text. Incomplete.
    * This is not used at the moment, but it can be used for something.
    */
  val seeMoreButton =
    button(
      cls := "border-0 cursor-pointer whitespace-nowrap transition duration-300 mt-4 py-1.5 px-6 rounded-2xl font-normal mt-0 text-sm bg-transparent mr-2 text-button-inactive dark:text-dark-button-inactive border border-solid border-button-inactive dark:border-dark-button-inactive hidden sm:block hover:text-black hover:border-black dark:hover:text-white dark:hover:border-white open-pop-up",
      "Pogledajte"
    )

  def externalWebsiteLink(url: URL) =
    a(
      cls := "border-0 cursor-pointer whitespace-nowrap transition duration-300 mt-4 py-1.5 px-6 rounded-2xl font-normal mt-0 text-sm bg-transparent mr-2 text-button-inactive dark:text-dark-button-inactive border border-solid border-button-inactive dark:border-dark-button-inactive hidden sm:block hover:text-black hover:border-black dark:hover:text-white dark:hover:border-white",
      href := url.toString,
      target := "_blank",
      rel := "nofollow noopener",
      "Pogledajte"
    )

  def itemHeading(reference: Reference) =
    div(
      a(
        href := Reference.getUrl(reference).toString,
        rel := "noopener nofollow",
        target := "_blank",
        reference.name
      ),
      br(),
      tag(reference.tag)
    )

  def itemTag(tag: Tag) =
    div(
      cls := "ml-auto w-32 relative font-normal hidden lg:inline text-sm",
      tag.toString
    )

  def itemActions(url: URL) =
    div(
      cls := "flex items-center justify-end ml-auto w-44",
      externalWebsiteLink(url)
    )

  div(
    cls := "mt-7 flex flex-col",
    div(
      cls := "text-content-title-color dark:text-dark-content-title-color mb-3.5 flex justify-between",
      headingTag(heading),
      if isFeatured then
        div(
          cls := "self-center", // hidden sm:block
          a(
            cls := "text-xs hover:text-black dark:hover:text-white",
            href := Route.References.url.toString,
            "Pogledajte sve reference"
          )
        )
      else ""
    ),
    ul(
      cls := "flex flex-col w-full h-full justify-around bg-theme-bg-color dark:bg-dark-content-bg pl-0 m-0 rounded-xl border border-solid border-theme-bg-color dark:border-dark-theme-bg-color",
      for reference <- references
      yield li(
        cls := "transition duration-300 transform-gpu first:rounded-t-xl last:rounded-b-xl hover:bg-theme-bg-color dark:hover:bg-dark-theme-bg-color flex items-center w-full h-full whitespace-nowrap py-2.5 px-4 text-base border-t border-t-solid border-t-border-color dark:border-t-dark-border-color first:border-0",
        div(
          cls := "flex items-center w-36",
          reference.tag.getSvg,
          itemHeading(reference)
        ),
        itemTag(reference.tag),
        itemActions(Reference.getUrl(reference))
      )
    )
  )
