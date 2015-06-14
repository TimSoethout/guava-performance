package nl.timmybankers.guava

import java.util

import org.scalacheck.Arbitrary._
import org.scalacheck.Gen

import scala.collection.JavaConverters._

object Helpers {
  /**
   * Generator for java Collections with null values.
   */
  val listWithNullGenerator: Gen[util.Collection[String]] =
    for {
      list <- arbitrary[List[Option[String]]]
    // trick to have nulls
    } yield list.map(_.orNull).asJavaCollection
}
