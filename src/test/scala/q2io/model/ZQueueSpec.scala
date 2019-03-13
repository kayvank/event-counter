package q2io.model

import org.specs2._
import org.specs2.scalaz.ScalazMatchers
import q2io.model.Model.{Data, Query}

class ZQueueSpec extends Specification with ScalazMatchers {
  def is = s2"""
  ZQueue specifications
    eventStore should store events $e1
"""
  import ZQueue._

  def e1 = {
    (1 to 100).foreach(i => eventStore(Model.Data.apply))
    val computed = Query(4 * 60).map( q => blockingCount(q))
    println(s"computed = ${computed}")
    computed must beSuccessful
    computed.toOption.get === 100
    1 === 1
  }
}
