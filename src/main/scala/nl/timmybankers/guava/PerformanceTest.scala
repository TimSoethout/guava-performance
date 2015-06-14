package nl.timmybankers.guava

import java.util.concurrent.TimeUnit

import com.google.common.base.Stopwatch
import org.scalacheck.Gen

import scala.concurrent.duration._

object PerformanceTest extends App {


  // First compare Null filter on list
  compare(Helpers.listWithNullGenerator, "Guava", {
    FilterNulls.filterNullsGuava[String]
  }, "Plain", {
    FilterNulls.filterNullsPlain[String]
  })

  // Now the real use case on Map
  compare(Helpers.mapWithNullValuesGenerator, "Guava", {
    FilterNulls.filterBlanksInMapGuava[String]
  }, "Plain", {
    FilterNulls.filterBlanksInMapPlain[String]
  })

  def compare[T](generator: Gen[T], firstLabel: String, first: T => T, secondLabel: String, second: T => T) = {
    // warm up JVM
    0 to 1000 foreach {
      _ =>
        val input: T = generator.sample.get
        first(input)
        second(input)
    }

    val inputs: Seq[T] =
      0 to 1000 map {
        _ => generator.sample.get
      }

    val (firstWins, secondWins): (Int, Int) = inputs.foldLeft((0, 0)) {
      case ((prevFirstWins, prevSecondWins), input) =>
        //        println(s"run for input with ${input.size}")

        val firstTime = time {
          first(input)
        }
        println(s"$firstLabel took: \t\t $firstTime")

        val secondTime = time {
          second(input)
        }
        println(s"$secondLabel  took: $secondTime")

        println(s"Difference (second-first): ${secondTime - firstTime}")
        if (secondTime < firstTime) {
          (prevFirstWins, prevSecondWins + 1)
        } else {
          (prevFirstWins + 1, prevSecondWins)
        }

    }
    println(s"$firstLabel fastest #$firstWins; $secondLabel fastest #$secondWins")
  }


  def time(function: => Unit): Duration = {
    val timer = Stopwatch.createUnstarted()

    timer.start()
    function
    timer.stop()

    timer.elapsed(TimeUnit.NANOSECONDS).nanos
  }

}
