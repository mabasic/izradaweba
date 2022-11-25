package eu.izradaweba.validation

import org.scalactic.*

import eu.izradaweba.Tag

type Email = String

case class ValidationError(inputName: String, message: ErrorMessage)

/** Ignore Good value since if it is good it should already be true.
  *
  * @param input
  * @param fieldName
  */
def parseConsent(
    input: Option[String],
    inputName: String,
    fieldName: String
): Boolean Or One[ValidationError] =
  input match
    case Some(value) =>
      value match
        case "on" => Good(true)
        case _ =>
          Bad(
            One(
              ValidationError(
                inputName,
                s"Polje ${fieldName} mora biti prihvaćeno."
              )
            )
          )
    case None =>
      Bad(One(ValidationError(inputName, s"Polje ${fieldName} je obavezno.")))

def parseTag(
    input: Option[String],
    inputName: String,
    fieldName: String
): Tag Or One[ValidationError] =
  input match
    case Some(value) =>
      Tag.from(value) match
        case Some(tag) => Good(tag)
        case None =>
          Bad(
            One(
              ValidationError(
                inputName,
                s"Polje ${fieldName} ne sadrži ispravan subjekt."
              )
            )
          )
    case None =>
      Bad(One(ValidationError(inputName, s"Polje ${fieldName} je obavezno.")))

def parseString(
    input: Option[String],
    inputName: String,
    fieldName: String
): String Or One[ValidationError] =
  input match
    case Some(value) =>
      if value.length > 0 then Good(value)
      else
        Bad(
          One(
            ValidationError(
              inputName,
              s"Polje ${fieldName} ne smije biti prazno."
            )
          )
        )
    case None =>
      Bad(One(ValidationError(inputName, s"Polje ${fieldName} je obavezno.")))

/** Taken from: https://stackoverflow.com/a/32445372
  *
  * @param email
  * @param fieldName
  * @return
  *   It returns true if success or InvalidEmail on Failure with a message.
  */
def checkEmail(e: String): Boolean =
  val emailRegex =
    """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r

  e match
    case null                                          => false
    case e if e.trim.isEmpty                           => false
    case e if emailRegex.findFirstMatchIn(e).isDefined => true
    case _                                             => false

def parseEmail(
    input: Option[String],
    inputName: String,
    fieldName: String
): String Or One[ValidationError] =
  input match
    case Some(email) =>
      if checkEmail(email) == true then Good(email)
      else
        Bad(
          One(
            ValidationError(
              inputName,
              s"Polje ${fieldName} mora biti ispravna email adresa."
            )
          )
        )
    case None =>
      Bad(One(ValidationError(inputName, s"Polje ${fieldName} je obavezno.")))
