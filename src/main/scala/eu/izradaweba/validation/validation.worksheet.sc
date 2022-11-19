import eu.izradaweba.validation.*
import scala.util.{Success, Failure}

val error = validateString("", "full_name")  /*>  : Try[Boolean] = Failure(eu.izradaweba.validation.EmptyString: Polje full_nam…  */

// val response = error match 
//   case Failure(error2) => error2.getMessage()
//   case _ => "" 

validateString("ajme", "full_name")  /*>  : Try[Boolean] = Success(true)  */

validateEmail("test@test.com", "email_address")  /*>  : Try[Boolean] = Success(true)  */
validateEmail("test@test.", "email_address")  /*>  : Try[Boolean] = Failure(eu.izradaweba.validation.InvalidEmail: Polje email_…  */

validateTag("directory-listing", "subject")  /*>  : Try[Boolean] = Success(true)  */
validateTag("directorylisting", "subject")  /*>  : Try[Boolean] = Failure(eu.izradaweba.validation.InvalidTag: Polje subject ne…  */

