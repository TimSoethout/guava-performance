package nl.timmybankers.guava

import java.util

import org.scalameter.api._
import scala.collection.JavaConverters._

object FilterNullsBenchMark extends PerformanceTest.OfflineReport {
  val sizes: Gen[Int] = Gen.range("size")(0, 1500000, 300000)

  val ranges: Gen[util.List[Int]] = for {
    size <- sizes
  } yield (0 until size).toList.asJava

//  override lazy val reporter = ChartReporter(ChartFactory.XYLine())

  performance of "Range" in {
    measure method "filterNullsPlain" in {
      using(ranges) in {
        input => FilterNulls.filterNullsPlain(input).asScala foreach (_ => Unit)
      }
    }
    measure method "filterNullsGuava" in {
      using(ranges) in {
        input => FilterNulls.filterNullsGuava(input).asScala foreach (_ => Unit)
      }
    }
  }
}
