package eu.izradaweba.pages

enum Page(val name: String, val url: String):
  case Home extends Page("Naslovna", "/")
  case ReferenceIndex extends Page("Reference", "/reference")
  case Contact extends Page("Kontakt", "/kontakt")