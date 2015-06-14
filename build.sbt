name := "guava-performance"

version := "1.0"

scalaVersion := "2.11.6"


libraryDependencies += "com.google.guava" % "guava" % "18.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.2"
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.4"
libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.6"

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

logBuffered := false

parallelExecution in Test := false