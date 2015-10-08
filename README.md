# sbt-memory

Compilation memory problems with sbt 0.13.9 / scala pickling / unfiltered (windows 10, scala 2.11)

Simplified and dumbed code from real (private) project which can't get to compile even with 16G of memory heap

No problems with Intellij Idea ("IDEA" incrementality option, same problems with "SBT" incrementality option). Same problems
observed with scala-ide (not tested for this project yet)

Out of memory error with 4G heap. Ok with 8G (saw 7G of memory consumption in windows process/task util)
