package nl.timmybankers.guava

import java.util

import nl.timmybankers.guava.Helpers.{listWithNullGenerator,mapWithNullValuesGenerator}
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
      val filteredPlain: util.Collection[String] = FilterNulls.filterNullsPlain(is)
      filteredPlain should contain theSameElementsAs filteredPlain.asScala
    }
  }

  "filterBlanksInMapGuava" should "filter nulls" in {
    forAll(mapWithNullValuesGenerator) { is =>
      val filtered: util.Map[String, String] = FilterNulls.filterBlanksInMapGuava(is)
      filtered.values() should not(contain(null))
    }
  }

  "filterBlanksInMapPlain" should "filter nulls" in {
    forAll(mapWithNullValuesGenerator) { is =>
      val filtered: util.Map[String, String] = FilterNulls.filterBlanksInMapPlain(is)
      filtered.values() should not(contain(null))
    }
  }

  "filterBlanksInMapPlain" should "have the same results as filterBlanksInMapGuava" in {
    forAll(mapWithNullValuesGenerator) { is =>
      val filteredGuava: util.Map[String, String] = FilterNulls.filterBlanksInMapGuava(is)
      val filteredPlain: util.Map[String, String] =  FilterNulls.filterBlanksInMapPlain(is)
      filteredPlain.asScala should contain theSameElementsAs filteredPlain.asScala
    }
  }

}
