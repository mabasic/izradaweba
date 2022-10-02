package eu.izradaweba.typography

import eu.izradaweba.Route
import org.http4s.Uri.Path
import scalatags.Text.all.*

import java.net.URL

/** It returns a search engine optimized HTML `a` tag which points to an
  * external URL.
  *
  * @param text
  *   The text which is displayed on the link.
  * @param url
  *   The URL to which the link points to.
  * @param includeRel
  *   By setting this to false the link does not include the SEO optimization.
  *   Useful when the outgoing link points to your own website.
  * @return
  *   It returns a scalatags tag.
  */
def outboundLink(text: String, url: URL, includeRel: Boolean = true) =
  a(
    cls := "underline",
    href := url.toString,
    if includeRel then rel := "nofollow noopener"
    else "",
    text
  )

def link(text: String, path: Path) =
  a(
    cls := "underline mr-2",
    href := path.toString,
    text
  )

def routeLink(route: Route) =
  link(route.name, route.url)
