package eu.izradaweba.pages

import scalatags.Text.all._

import eu.izradaweba.{Route, references}
import eu.izradaweba.layouts.defaultLayout
import eu.izradaweba.partials.renderReferences

val index = Seq(
    renderReferences(references, "Reference", headingTag = h1),
  )

val referencesPage = defaultLayout(index, activeRoute = Route.References, metaTitle = "Reference")