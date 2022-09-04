package eu.izradaweba.js

//import org.scalajs.dom
import org.scalajs.dom.{Element, Event, EventTarget, MediaQueryList, MediaQueryListListener, MouseEvent, document, window, HTMLCollection}

//const mobileNavbar = document.getElementById("mobile-navbar")
val mobileNavbar: Element = document.getElementById("mobile-navbar")
//const mobileNavbarToggle = document.getElementById("mobile-navbar-toggle")
val mobileNavbarToggle: Element = document.getElementById("mobile-navbar-toggle")

// Flow: automatic -> dark -> light

//const themeSwitchButton = document.getElementById("theme-switch")
val themeSwitchButton: Element = document.getElementById("theme-switch")

//const themeAutomaticSvg = document.getElementById("theme-automatic")
val themeAutomaticSvg: Element = document.getElementById("theme-automatic")
//const themeDarkSvg = document.getElementById("theme-dark")
val themeDarkSvg: Element = document.getElementById("theme-dark")
//const themeLightSvg = document.getElementById("theme-light")
val themeLightSvg: Element = document.getElementById("theme-light")

//const prefersDark = window.matchMedia('(prefers-color-scheme: dark)')
val prefersDark: MediaQueryList = window.matchMedia("(prefers-color-scheme: dark)")

val darkClass = "dark"
val lightClass = "light"
val hiddenClass = "hidden"

//const addDarkClassToBody = () => {
//  document.documentElement.classList.add('dark')
//}
def addDarkClassToBody(): Unit =
  document.documentElement.classList.add(darkClass)

//const removeDarkClassFromBody = () => {
//  document.documentElement.classList.remove('dark')
//}
def removeDarkClassFromBody(): Unit =
  document.documentElement.classList.remove(darkClass)

//const setLightTheme = () => {
//  removeDarkClassFromBody()
//
//  themeAutomaticSvg.classList.add("hidden")
//  themeDarkSvg.classList.add("hidden")
//  themeLightSvg.classList.remove("hidden")
//}
def setLightTheme(): Unit =
  removeDarkClassFromBody()

  themeAutomaticSvg.classList.add(hiddenClass)
  themeDarkSvg.classList.add(hiddenClass)
  themeLightSvg.classList.remove(hiddenClass)

//const setDarkTheme = () => {
//  addDarkClassToBody()
//
//  themeAutomaticSvg.classList.add("hidden")
//  themeDarkSvg.classList.remove("hidden")
//  themeLightSvg.classList.add("hidden")
//}
def setDarkTheme(): Unit =
  addDarkClassToBody()

  themeAutomaticSvg.classList.add(hiddenClass)
  themeDarkSvg.classList.remove(hiddenClass)
  themeLightSvg.classList.add(hiddenClass)

//const setAutomaticTheme = ({ darkTheme } = { darkTheme: false }) => {
//  themeAutomaticSvg.classList.remove("hidden")
//  themeDarkSvg.classList.add("hidden")
//  themeLightSvg.classList.add("hidden")
//
//  if (darkTheme === true) {
//    addDarkClassToBody()
//
//    return
//  }
//
//  removeDarkClassFromBody()
//}
def setAutomaticTheme(darkTheme: Boolean = false): Unit =
  themeAutomaticSvg.classList.remove(hiddenClass)
  themeDarkSvg.classList.add(hiddenClass)
  themeLightSvg.classList.add(hiddenClass)

  if (darkTheme)
    addDarkClassToBody()
  else
    removeDarkClassFromBody()

def setupUI(): Unit =
  //mobileNavbarToggle.addEventListener("click", () => {
  //  mobileNavbar.classList.toggle("hidden")
  //})
  mobileNavbarToggle.addEventListener("click", { (_: Event) =>
    mobileNavbar.classList.toggle(hiddenClass)
  })

  //const closedPopup = document.getElementById("pop-up");
  val closedPopup: Element = document.getElementById("pop-up")
  //const overlayApp = document.getElementById("overlay-app");
  val overlayApp: Element = document.getElementById("overlay-app")

  //const popupCloseTriggers = closedPopup.getElementsByClassName("close-pop-up");
  val popupCloseTriggers: HTMLCollection[Element] = closedPopup.getElementsByClassName("close-pop-up")

  //Array.from(popupCloseTriggers).forEach((popupCloseTrigger) => {
  //  popupCloseTrigger.addEventListener("click", () => {
  //    overlayApp.classList.add("invisible");
  //    overlayApp.classList.add("opacity-0");
  //    closedPopup.classList.add("invisible");
  //    closedPopup.classList.add("opacity-0");
  //  });
  //});
  popupCloseTriggers.foreach({ (popupCloseTrigger: Element) =>
    popupCloseTrigger.addEventListener("click", { (_: MouseEvent) =>
      overlayApp.classList.add("invisible")
      overlayApp.classList.add("opacity-0")
      closedPopup.classList.add("invisible")
      closedPopup.classList.add("opacity-0")
    })
  })

  //const popupOpenTriggers = document.getElementsByClassName("open-pop-up");
  val popupOpenTriggers: HTMLCollection[Element] = document.getElementsByClassName("open-pop-up")

  //Array.from(popupOpenTriggers).forEach((popupOpenTrigger) => {
  //  popupOpenTrigger.addEventListener("click", () => {
  //    overlayApp.classList.remove("invisible");
  //    overlayApp.classList.remove("opacity-0");
  //    closedPopup.classList.remove("invisible");
  //    closedPopup.classList.remove("opacity-0");
  //  });
  //});
  popupOpenTriggers.foreach({ (popupOpenTrigger: Element) =>
    popupOpenTrigger.addEventListener("click", { (_: MouseEvent) =>
      overlayApp.classList.remove("invisible")
      overlayApp.classList.remove("opacity-0")
      closedPopup.classList.remove("invisible")
      closedPopup.classList.remove("opacity-0")
    })
  })

  //prefersDark.addListener(event => {
  //  if ('theme' in localStorage) {
  //    return
  //  }
  //
  //  // Prefers dark theme.
  //  if (event.matches) {
  //    setAutomaticTheme({ darkTheme: true })
  //
  //    return
  //  }
  //
  //  setAutomaticTheme({ darkTheme: false })
  // })
  prefersDark.asInstanceOf[EventTarget].addEventListener("change", { (e: MediaQueryList) =>
    val theme = window.localStorage.getItem("theme")

    theme match
      case null => ()
      case _ =>
        if (e.matches)
          setAutomaticTheme(darkTheme = true)
        else
          setAutomaticTheme()
  })

  //// On page load or when changing themes, best to add inline in `head` to avoid FOUC
  //if (localStorage.theme === 'light') {
  //  setLightTheme()
  //} else if (localStorage.theme === 'dark') {
  //  setDarkTheme()
  //} else if (prefersDark.matches) {
  //  setAutomaticTheme({ darkTheme: true })
  //} else {
  //  setAutomaticTheme({ darkTheme: false })
  //}
  val localStorageTheme = window.localStorage.getItem("theme")

  if (localStorageTheme == lightClass)
    setLightTheme()
  else if (localStorageTheme == darkClass)
    setDarkTheme()
  else if (prefersDark.matches)
    setAutomaticTheme(darkTheme = true)
  else
    setAutomaticTheme()

  //themeSwitchButton.addEventListener("click", () => {
  //  // automatic -> dark
  //  if (!('theme' in localStorage)) {
  //    setDarkTheme()
  //
  //    // Whenever the user explicitly chooses dark mode
  //    localStorage.theme = 'dark'
  //
  //    return
  //  }
  //
  //  // dark -> light
  //  if (localStorage.theme === "dark") {
  //    setLightTheme()
  //
  //    // Whenever the user explicitly chooses light mode
  //    localStorage.theme = 'light'
  //
  //    return
  //  }
  //
  //  // light -> automatic (dark/light)
  //  if (localStorage.theme === "light") {
  //
  //    if (prefersDark.matches) {
  //      setAutomaticTheme({ darkTheme: true })
  //    } else {
  //      setAutomaticTheme({ darkTheme: false })
  //    }
  //
  //    // Whenever the user explicitly chooses to respect the OS preference
  //    localStorage.removeItem('theme')
  //
  //    return
  //  }
  //});
  themeSwitchButton.addEventListener("click", { (_: Event) =>
    // automatic -> dark
    val theme = window.localStorage.getItem("theme")

    if (theme == null)
      setDarkTheme()

      // Whenever the user explicitly chooses dark mode
      window.localStorage.setItem("theme", darkClass)

    // dark -> light
    else if (theme == darkClass)
      setLightTheme()

      // Whenever the user explicitly chooses light mode
      window.localStorage.setItem("theme", lightClass)

    // light -> automatic (dark/light)
    else if (theme == lightClass)
      if (prefersDark.matches)
        setAutomaticTheme(darkTheme = true)
      else
        setAutomaticTheme()

      // Whenever the user explicitly chooses to respect the OS preference
      window.localStorage.removeItem("theme")

    end if
  })

@main def web(): Unit =
  document.addEventListener("DOMContentLoaded", { (_: MouseEvent) =>
    setupUI()
  })