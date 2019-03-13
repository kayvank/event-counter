package q2io.api

import q2io.model.Model.{Data, Query}
import q2io.model._
import scalaz._, Scalaz._

class Api {

  def signal(): Unit = ZQueue.eventStore(Data())

  def temporalQuery(from: Int) =
    Query(from).map( q => ZQueue.blockingCount(q) ) match {
      case Success(e) =>  e match {
        case Right(Data(s, c)) => \/-(c)
        case Left(_e: Throwable) =>  -\/(Err(ErrorCode.ConcurrencyError, _e.getMessage))
      }
      case Failure(e) => -\/(e)
    }
  def temporalQueryOpt(from: Int) = temporalQuery(from).toOption

}
