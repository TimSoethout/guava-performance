package nl.timmybankers.guava

import java.util

import org.scalameter.api._

import scala.collection.JavaConverters._

object FilterNullsBenchMark extends PerformanceTest.OfflineReport {
  val sizes: Gen[Int] = Gen.exponential("size")(1, 1500000, 2)
  //  val nullIndices = Gen.range("nullIndices")(0, 150000, 10)

  val lists: Gen[util.List[String]] =
    sizes.map(size => (0 until size)
      .zipWithIndex.map { case (i: Int, v: Int) => if (i % 9 == 0) null else v.toString }
      .toList.asJava)

  val maps: Gen[util.Map[String,String]] =
    sizes.map(size => (0 until size)
      .map(i => i.toString -> (if (i % 9 == 0) null else i.toString))
      .toMap.asJava)
  
  //  override lazy val reporter = ChartReporter(ChartFactory.XYLine())

  performance of "FilterNulls" in {
//    measure method "filterNullsPlain" in {
//      using(ranges) in {
//        input => FilterNulls.filterNullsPlain(input) //.asScala foreach (_ => Unit)
//      }
//    }
//    measure method "filterNullsGuava" in {
//      using(ranges) in {
//        input => FilterNulls.filterNullsGuava(input) //.asScala foreach (_ => Unit)
//      }
//    }

    measure method "filterNullsPlainStrictEvaluation" in {
      using(lists) in {
        input => FilterNulls.filterNullsPlain(input).asScala foreach (_ => Unit)
      }
    }
    measure method "filterNullsGuavaStrictEvaluation" in {
      using(lists) in {
        input => FilterNulls.filterNullsGuava(input).asScala foreach (_ => Unit)
      }
    }

    measure method "filterBlanksInMapPlain" in {
      using(maps) in {
        input => FilterNulls.filterBlanksInMapPlain(input).asScala foreach (_ => Unit)
      }
    }
    measure method "filterBlanksInMapGuava" in {
      using(maps) in {
        input => FilterNulls.filterBlanksInMapGuava(input).asScala foreach (_ => Unit)
      }
    }
  }
}
