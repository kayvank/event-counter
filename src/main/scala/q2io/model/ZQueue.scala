package q2io.model

import scalaz._
import Scalaz._
import scalaz.zio._
import scala.concurrent._
import scala.util.{Left, Right, Try}
import scalaz.zio.stream.{Stream => S}

object ZQueue {

    import Model._

    implicit val monoid: Monoid[Data] = Monoid.instance(
      (a, b) => Data((b.seconds >= a.seconds) ? b.seconds | a.seconds, a.cnt |+| b.cnt),
      Data(0, 0)
    )
    final val slidingWindow: Int = maxLookBack

    lazy val zQueue: UIO[Queue[Data]] = Queue.sliding[Data](slidingWindow)

    def eventStore(data: Data): Unit = {
      val _ = for {
        q  <- zQueue
        v2 <- q.poll
        _ = v2.foreach(x =>
          (x.seconds == data.seconds) ? q.offer(x.copy(cnt = x.cnt + 1)) | {
            q.offer(x)
            q.offer(data)
        })
      } yield (q)
    }

  def streamWhile(__zQueue: UIO[Queue[Data]]) (f: Data => Boolean ): UIO[List[Data]]=
   pollWhile(__zQueue)(f).flatMap(s => s.takeAll)


    def pollWhile(__zQueue: UIO[Queue[Data]])(f: Data => Boolean) = {

      def helper(_zq: UIO[Queue[Data]] ):UIO[Queue[Data]] =
        for {
          q <- __zQueue
          p <- q.poll
          __zq <- _zq
          u <-(p.isDefined && f(p.get))match {
            case true =>  __zq.offer(p.get)
              helper(_zq)
            case false => _zq
          }
        } yield (u)

      helper(Queue.sliding(slidingWindow))
    }

    private def count(query: Query): UIO[Data] =
      for {
        q1 <- zQueue
        q  = q1
        s1 <- q.poll
        s2 = s1.filter(_.seconds > query.from)
        s3 = s2.foldLeft(Data(0, 0))(_ |+| _)
        l  = s3.seconds
      } yield (s3)

    def blockingCount(query: Query): Either[Throwable, Data] = {
      val runtime = new DefaultRuntime {}
      Try(runtime.unsafeRun(count(query))) match {
        case scala.util.Success(d) => Right(d)
        case scala.util.Failure(e) => Left(e)
      }
    }

    def futureCount(query: Query): Future[Data] = {
      val runtime = new DefaultRuntime {}
      runtime.unsafeRunToFuture(count(query))
    }

  }
