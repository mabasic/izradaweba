package eu.izradaweba.typography

import scalatags.Text.all.*

def page(children: Seq[ConcreteHtmlTag[String]]) =
  div(
    cls := "text-base font-normal bg-content-bg dark:bg-dark-content-bg border border-solid border-theme-bg-color dark:border-dark-theme-bg-color transition p-5 rounded-xl flex flex-col",
    children
  )

def pageTitle(text: String) =
  h1(
    cls := "text-4xl font-bold",
    text
  )

def pageHeading(text: String, tag: ConcreteHtmlTag[String] = h2) =
  tag(
    cls := "text-2xl font-bold mt-5",
    text
  )

def pageSubtitle(text: String) =
  p(
    cls := "text-sm font-semibold my-2",
    text
  )

def pageParagraph(elements: String | Seq[Modifier]) =
  p(
    cls := "my-3",
    elements match
      case text: String            => text
      case elements: Seq[Modifier] => for element <- elements yield element
  )
