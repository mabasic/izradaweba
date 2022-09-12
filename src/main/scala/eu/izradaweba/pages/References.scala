package eu.izradaweba.pages

import scalatags.Text.all._

import eu.izradaweba.{Page, references}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences

val index =
  div(
    cls := "flex flex-col text-theme-color dark:text-dark-theme-color px-5 py-4 sm:px-10 sm:py-7 h-full overflow-auto bg-theme-bg-color dark:bg-dark-theme-bg-color",
    renderReferences(references, "Reference"),
  )

val referencesPage = defaultLayout(Seq(index), activePage = Page.References)