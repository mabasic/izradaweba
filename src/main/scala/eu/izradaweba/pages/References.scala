package eu.izradaweba.pages

import scalatags.Text.all._

import eu.izradaweba.{Route, references}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences

val index = Seq(
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
  defaultLayout(index, activeRoute = Route.References, metaTitle = "Reference")
