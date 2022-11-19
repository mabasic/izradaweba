package eu.izradaweba.validation

import scala.util.{Success, Failure}

import eu.izradaweba.Tag

type Email = String

case class ContactMessage(
    full_name: String,
    email_address: Email,
    subject: eu.izradaweba.Tag,
    message: String,
    gdpr_consent: Boolean
)

case class EmptyString(message: String) extends Exception(message)
case class InvalidEmail(message: String) extends Exception(message)
case class InvalidTag(message: String) extends Exception(message)
case class NoConsent(message: String) extends Exception(message)

val contactMessageValidationRules = Map(
  "full_name" -> List(validateString),
  "email_address" -> List(validateEmail),
  "subject" -> List(validateTag),
  "message" -> List(validateString),
  "gdpr_consent" -> List(validateConsent)
)

def validateConsent(consent: Boolean, fieldName: String) =
  if consent == true then Success(true)
  else Failure(NoConsent(s"Polje ${fieldName} mora biti prihvaćeno."))

def validateTag(string: String, fieldName: String) =
  Tag.from(string) match
    case Some(_) => Success(true)
    case None =>
      Failure(
        InvalidTag(
          s"Polje ${fieldName} ne sadrži ispravan subjekt."
        )
      )

def validateString(string: String, fieldName: String) =
  if string.length > 0 then Success(true)
  else Failure(EmptyString(s"Polje ${fieldName} je prazno."))

/** Taken from: https://stackoverflow.com/a/32445372
  *
  * @param email
  * @param fieldName
  * @return
  *   It returns true if success or InvalidEmail on Failure with a message.
  */
def validateEmail(email: Email, fieldName: String) =
  val emailRegex =
    """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r

  def check(e: String): Boolean = e match
    case null                                          => false
    case e if e.trim.isEmpty                           => false
    case e if emailRegex.findFirstMatchIn(e).isDefined => true
    case _                                             => false

  if check(email) == true then Success(true)
  else
    Failure(
      InvalidEmail(s"Polje ${fieldName} mora biti ispravna email adresa.")
    )
