name := "sbt-error"

version := "1.0"

scalaVersion := "2.11.7"

// Disabling name hashing fixes the issue
//incOptions := incOptions.value.withNameHashing(false)

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.0"

libraryDependencies += "net.databinder" %% "unfiltered-netty" % "0.8.2"

libraryDependencies += "net.databinder" %% "unfiltered-netty-websockets" % "0.8.2"

libraryDependencies += "org.scala-lang.modules" %% "scala-pickling" % "0.10.1"

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "1.4.0" // wrapper de joda-time

libraryDependencies += "org.joda" % "joda-convert" % "1.2"


    