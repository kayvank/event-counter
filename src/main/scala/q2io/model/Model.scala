package q2io.model
import scalaz._, Scalaz._

object Model {

  final case class Data(seconds: Int, cnt: Int)

  final val maxLookBack = 5 * 60
  final case class Epoch(millis: Long = System.currentTimeMillis()) {
    def asSeconds: Int = math.ceil(millis / 1000.00).toInt
  }

  final case class Query(from: Int)

  object Data {
    def apply(): Data = new Data(Epoch().asSeconds, 1)
  }

  object Query {

    def apply(from: Int)=
      (from > 0 && from <= maxLookBack)  match  {
        case true => (new Query(from)).success
        case false =>  (new Err(ErrorCode.invalidData, "badData")).failure
          }

  }
}
