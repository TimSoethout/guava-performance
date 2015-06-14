package nl.timmybankers.guava

import java.util
import java.util.concurrent.TimeUnit

import com.google.common.base.Stopwatch
import org.scalacheck.Gen

import scala.collection.JavaConverters._
import scala.concurrent.duration._

object PerformanceTest extends App {

  // First compare Null filter on list
  compare[util.Collection[String]](Helpers.listWithNullGenerator, "Guava", {
    input: util.Collection[String] =>
      FilterNulls.filterNullsGuava[String](input).asScala foreach (_ => Unit)
  }, "Plain", {
    input: util.Collection[String] =>
      FilterNulls.filterNullsPlain[String](input).asScala foreach (_ => Unit)
  })

  // Now the real use case on Map
  compare(Helpers.mapWithNullValuesGenerator, "Guava", {
    input: util.Map[String, String] =>
      FilterNulls.filterBlanksInMapGuava[String](input).asScala foreach (_ => Unit)
  }, "Plain", { input: util.Map[String, String] =>
    FilterNulls.filterBlanksInMapPlain[String](input).asScala foreach (_ => Unit)
  })

  def compare[T](generator: Gen[T], firstLabel: String, first: T => Unit, secondLabel: String, second: T => Unit) = {
    // warm up JVM
    0 to 1000 foreach {
      _ =>
        val input: T = generator.sample.get
        first(input)
        second(input)
    }

    val count = 1000

    val inputs: Seq[T] =
      0 to count map {
        _ => generator.sample.get
      }

    val (firstWins, secondWins, totalFirst, totalSecond): (Int, Int, Duration, Duration) = inputs.foldLeft((0, 0, 0.millis: Duration, 0.millis: Duration)) {
      case ((prevFirstWins, prevSecondWins, prevTotalFirst: Duration, prevTotalSecond: Duration), input) =>
        //        println(s"run for input with ${input.size}")

        val firstTime = time {
          first(input)
        }
        println(s"$firstLabel took: \t\t $firstTime")

        val secondTime = time {
          second(input)
        }
        println(s"$secondLabel took: $secondTime")

        println(s"Difference (second-first): ${secondTime - firstTime}")

        if (secondTime < firstTime) {
          (prevFirstWins, prevSecondWins + 1, prevTotalFirst + firstTime, prevTotalSecond + secondTime)
        } else {
          (prevFirstWins + 1, prevSecondWins, prevTotalFirst + firstTime, prevTotalSecond + secondTime)
        }

    }
    println(s"$firstLabel fastest #$firstWins; $secondLabel fastest #$secondWins")
    println(s"$firstLabel mean ${totalFirst / count}; $secondLabel mean ${totalSecond / count}")
  }


  def time(function: => Unit): Duration = {
    val timer = Stopwatch.createUnstarted()

    timer.start()
    function
    timer.stop()

    timer.elapsed(TimeUnit.NANOSECONDS).nanos
  }

}
