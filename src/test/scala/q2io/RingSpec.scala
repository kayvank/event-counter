package q2io.model

import org.specs2._
  /** Ring Specification. Specs are written as test cases
 * 
 */
class RingSpec extends Specification { def is = s2"""
  Ring buffer specifications
     ring sotres element of any type $spec1
     ring capacity > 0 and is fixed when ring is created $spec2
     ring size may vary between zero(empty) and full $spec3
     new elements are added to the end of ring and the ring size is increased $spec4
     in a full ring, oldest element is discarded before the new element is added $spec5
      poping an elment from non-empty ring removes the oldest element and decreases the size by one $spec6
     attempting to pop from an empty ring will result in program crash $spec7
"""
  def spec1 = true === false //TODO complete me!
  def spec2 = true === false //TODO complete me!
  def spec3 = true === false //TODO complete me!
  def spec4 = true === false //TODO complete me!
  def spec5 = true === false //TODO complete me!
  def spec6 = true === false //TODO complete me!
  def spec7 = true === false //TODO complete me!

}
