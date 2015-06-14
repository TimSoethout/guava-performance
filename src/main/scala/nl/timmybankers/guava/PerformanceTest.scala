package nl.timmybankers.guava

import java.util
import java.util.concurrent.TimeUnit

import com.google.common.base.Stopwatch

import scala.concurrent.duration._

object PerformanceTest extends App {
  // warm up JVM
  0 to 1000 foreach {
    _ =>
      val input: util.Collection[String] = Helpers.listWithNullGenerator.sample.get
      FilterNulls.filterNullsGuava(input)
      FilterNulls.filterNullsPlain(input)
  }

  val inputs: Seq[util.Collection[String]] =
    0 to 1000 map {
      _ => Helpers.listWithNullGenerator.sample.get
    }

  val (javaWins, guavaWins): (Int, Int) = inputs.foldLeft((0, 0)) {
    case ((prevJavaWins, prevGuavaWins), input) =>
      println(s"run for input with ${input.size}")

      val guavaTime = time {
        FilterNulls.filterNullsGuava(input)
      }
      println(s"Guava took: \t\t $guavaTime")

      val javaTime = time {
        FilterNulls.filterNullsPlain(input)
      }
      println(s"Plain  took: $javaTime")

      println(s"Difference (java-guava): ${javaTime - guavaTime}")
      if (javaTime > guavaTime) {
        (prevJavaWins, prevGuavaWins + 1)
      } else {
        (prevJavaWins + 1, prevGuavaWins)
      }
  }
  
  println(s"Plain fastest #$javaWins; Guava fastest #$guavaWins")

  def time(function: => Unit): Duration = {
    val timer = Stopwatch.createUnstarted()

    timer.start()
    function
    timer.stop()

    timer.elapsed(TimeUnit.NANOSECONDS).nanos
  }

}
