package q2io.model

import org.specs2._
import org.specs2.scalaz.ScalazMatchers
import q2io.model.Model.Query

class ModelSpec extends Specification with ScalazMatchers { def is = s2"""

  Model Specifications
    Valid Temporal query interval should be (0, 5]  minutes $e1
    Invalid Temporal Query interval should be outside of (0, 5] minutes $e2
"""
  def e1 = Query(5*59) must beSuccessful
  def e2 = Query(5*61) must beFailing
}
