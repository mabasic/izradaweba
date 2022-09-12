package eu.izradaweba.partials

import eu.izradaweba.pages.Page
import eu.izradaweba.{Reference, Tag}
import scalatags.Text.all.*

def renderReferences(references: List[Reference], heading: String, isFeatured: Boolean = false) =
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
      h2(heading),
      if isFeatured then
        div(
          cls := "hidden sm:block self-center",
          a(
            cls := "text-xs hover:text-black dark:hover:text-white",
            href := Page.ReferenceIndex.url,
            "Pogledajte sve reference"
          )
        )
      else
        ""
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