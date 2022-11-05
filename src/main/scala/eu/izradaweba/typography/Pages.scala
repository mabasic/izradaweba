package eu.izradaweba.typography

import scalatags.Text.all.*

def page(children: Seq[ConcreteHtmlTag[String]]) =
  div(
    cls := "text-base font-normal bg-content-bg dark:bg-dark-content-bg border border-solid border-theme-bg-color dark:border-dark-theme-bg-color transition duration-300 p-5 rounded-xl flex flex-col",
    children
  )

def pageTitle(text: String) =
  h1(
    cls := "text-4xl font-bold",
    text
  )
