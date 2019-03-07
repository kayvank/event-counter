package q2io.model

import scala.collection.immutable.Queue

/** An immutable covariant Ring buffer interface
 *
 * @param capacity  capacity of Ring
 * @param size number of elements in the Ring
 * @param queue immutable queue backing up the Ring 
 *
 */
case class Ring[+A](capacity: Int, size: Int, queue: Queue[A]) {

  /** Returns a new immutable queue with element add the end of Ring
   * @tparam element to insert
   * @return a tuple of [[scala.Option]] of first element of Ring that may have been evicted as a result of Ring reaching its capacity, and the new immutable queue 
   */
  def push[B >: A](elem: B): (Option[A], Ring[B]) =
    if(size < capacity) 
      (None, Ring(capacity, size+1, queue.enqueue(elem))) 
    else
      queue.dequeue match {
        case (e, q) => (Some(e), Ring(capacity, size, q.enqueue(elem)))
      }

  /** Returns coproduct of Error or the 2-tuple of the popped element and the new ring with that element removed
   * @return [[scala.Either]] [[q2io.model.Err]], if Ring is empty, or the 2-tuple of the popped element and the new ring with that element removed
   * @see  [[scala.Either]] 
 */
  def pop: Either[Err, (A, Ring[A])] = popOption match {
    case Some( (e, q) ) => Right( (e, q) )
    case None => Left(Err(ErrorCode.underflow, "attempting to pop an empty queue"))
  }

  /** Returns [[scala.Option]] of the 2-tuple of the popped element and the new ring with that element removed
   * @see [[scala.Option]]
   */
  def popOption: Option[(A, Ring[A])] = 
    queue.dequeueOption.map {
      case (e, q) => (e, Ring(capacity, size - 1, q))
    }
}

/** 
 *
 */

object Ring {
  def empty[A](capacity: Int): Ring[A] = Ring(capacity, 0, Queue.empty)

  def apply[A](capacity: Int)(xs: A*): Ring[A] = {
    val es = if (xs.size <= capacity) xs else xs.takeRight(capacity)
    Ring(capacity, es.size, Queue(es: _*))
  }
}
