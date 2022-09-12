package eu.izradaweba

import org.http4s.dsl.io.*

enum Page(val name: String, val url: Path):
  case Home extends Page("Naslovna", url = Root)
  case References extends Page("Reference", url = Root / "reference")
  case Contact extends Page("Kontakt", url = Root / "kontakt")