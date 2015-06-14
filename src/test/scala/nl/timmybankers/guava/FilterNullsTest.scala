package nl.timmybankers.guava

import java.util

import nl.timmybankers.guava.Helpers.listWithNullGenerator
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, ShouldMatchers}

import scala.collection.JavaConverters._

class FilterNullsTest extends FlatSpec with ShouldMatchers with PropertyChecks {

  "filterNullsGuava" should "filter nulls" in {
    forAll(listWithNullGenerator) { is =>
      val filtered: util.Collection[String] = FilterNulls.filterNullsGuava(is)
      filtered should not(contain(null))
    }
  }

  "filterNullsPlain" should "filter nulls" in {
    forAll(listWithNullGenerator) { is =>
      val filtered: util.Collection[String] = FilterNulls.filterNullsPlain(is)
      filtered should not(contain(null))
    }
  }

  "filterNullsPlain" should "have the same results as filterNullsGuava" in {
    forAll(listWithNullGenerator) { is =>
      val filteredGuava: util.Collection[String] = FilterNulls.filterNullsGuava(is)
      val filteredJava: util.Collection[String] = FilterNulls.filterNullsPlain(is)
      filteredJava should contain theSameElementsAs filteredJava.asScala
    }
  }

}
