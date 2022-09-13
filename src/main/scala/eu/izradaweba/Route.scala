package eu.izradaweba

import org.http4s.dsl.io.*

enum Route(val name: String, val url: Path):
  case Home extends Route("Naslovna", url = Root)
  case References extends Route("Reference", url = Root / "reference")
  case Contact extends Route("Kontakt", url = Root / "kontakt")