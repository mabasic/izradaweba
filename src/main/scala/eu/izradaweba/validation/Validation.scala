package eu.izradaweba.validation

import scala.util.{Success, Failure, Try}

import eu.izradaweba.Tag
import org.http4s.UrlForm

type Email = String

case class EmptyString(message: String) extends Exception(message)
case class InvalidEmail(message: String) extends Exception(message)
case class InvalidTag(message: String) extends Exception(message)
case class NoConsent(message: String) extends Exception(message)
case class Unknown(message: String) extends Exception(message)

type ValidationRule = (Option[String], String) => Try[Boolean]
type ValidationRules = Map[String, List[ValidationRule]]

type ValidationResults = Map[String, List[Try[Boolean]]]
type ValidatedData = Map[String, String]
type ValidationErrors = Map[String, List[Throwable]]

def validateConsent(value: Option[String], fieldName: String) =
  val failure = Failure(NoConsent(s"Polje ${fieldName} mora biti prihvaćeno."))

  value match
    case Some(value) =>
      if value == "on" then Success(true)
      else failure
    case None => failure

def validateTag(value: Option[String], fieldName: String) =
  val failure = Failure(
    InvalidTag(s"Polje ${fieldName} ne sadrži ispravan subjekt.")
  )

  value match
    case Some(value) =>
      Tag.from(value) match
        case Some(_) => Success(true)
        case None    => failure
    case None => failure

def validateString(value: Option[String], fieldName: String) =
  val failure = Failure(EmptyString(s"Polje ${fieldName} je prazno."))

  value match
    case Some(value) =>
      if value.length > 0 then Success(true)
      else failure
    case None => failure

/** Taken from: https://stackoverflow.com/a/32445372
  *
  * @param email
  * @param fieldName
  * @return
  *   It returns true if success or InvalidEmail on Failure with a message.
  */
def validateEmail(email: Option[Email], fieldName: String) =
  val failure =
    Failure(
      InvalidEmail(s"Polje ${fieldName} mora biti ispravna email adresa.")
    )

  val emailRegex =
    """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r

  def check(e: String): Boolean = e match
    case null                                          => false
    case e if e.trim.isEmpty                           => false
    case e if emailRegex.findFirstMatchIn(e).isDefined => true
    case _                                             => false

  email match
    case Some(email) =>
      if check(email) == true then Success(true)
      else failure
    case None => failure

case class ValidationStatus(
    status: Boolean,
    data: ValidatedData,
    errors: ValidationErrors
)

def validate(
    rules: ValidationRules,
    data: UrlForm,
    fieldNames: Map[String, String] = Map()
): ValidationStatus =
  val results = rules.map((key, rules) =>
    val value = data.getFirst(key)

    val fieldName =
      if fieldNames.contains(key) then fieldNames(key)
      else key

    (
      key,
      for rule <- rules yield rule(value, fieldName)
    )
  )

  val validatedData: ValidatedData = results
    .filter((key, result) =>
      result
        .filter(_ match
          case Success(_) => false
          case Failure(_) => true
        )
        .length == 0
    )
    .map((key, _) => (key, data.getFirstOrElse(key, "")))

  val validationErrors: ValidationErrors = results
    .filter((key, result) =>
      result
        .filter(_ match
          case Success(_) => false
          case Failure(_) => true
        )
        .length > 0
    )
    .map((key, errors1) =>
      (
        key,
        errors1.map(_ match
          case Failure(exception) => exception
          case _                  => Unknown("Unknown validation error")
        )
      )
    )

  if validationErrors.size == 0 then
    ValidationStatus(true, validatedData, validationErrors)
  else ValidationStatus(false, validatedData, validationErrors)
