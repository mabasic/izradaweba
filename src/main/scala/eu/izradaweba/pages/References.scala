package eu.izradaweba.pages

import scalatags.Text.all.*

import eu.izradaweba.{Route, references}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences

val referencesPageContent = Seq(
  renderReferences(
    references
      .sortBy(_.name)
      .reverse
      .sortBy(_.yearMade)
      .reverse,
    "Reference",
    headingTag = h1
  )
)

def referencesPage =
  defaultLayout(
    referencesPageContent,
    activeRoute = Route.References,
    metaTitle = "Reference"
  )
