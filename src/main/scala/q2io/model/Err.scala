package q2io.model

/** Enumeration Errorcodes
 *
 */
object ErrorCode extends Enumeration {
  type ErrorCode = Value
  val invalidData,
      notFound,
      underflow,
      overflow,
      UnsupportedOperation,
      unknown = Value
}

import ErrorCode._

/** Err class is container for Error codes and error descriptions
 * 
 * @param code the error code
 * @param desc error code description
 */
case class Err(code: ErrorCode, desc: String)
