package eu.izradaweba

object Config {
  val analyticsEnabled = sys.env.getOrElse("ANALYTICS_ENABLED", "false") match
    case "true" => true
    case _      => false
  
  val emailAddress = "mario@laravelista.hr"
}
