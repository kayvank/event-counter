Temporal Event-Counter
---
A small Scala library which helps to track the number of events that happened during a specified window of time.

## Overview
The library supports two operations: 
- Client should be able to signal that a single event happened (e.g. a page was served)
- Client should be able to request the number of events that happened over a user-specified amount of time until current time. You may limit the supported timespan to a reasonable upper bound, like 5 minutes. 
P
## Getting Started
Include event-counter in your project by adding the following to your build.sbt file:
```
libraryDependencies += "xxx.xxx" %% event-counter % "1.0-SNAPSHOT"
```
## Prerequisites
- [jdk-8](https://openjdk.java.net/install "jdk")better
- [sbt](https://www.scala-sbt.org/1.0/docs/Setup.html "sbt")
- [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git "git")
## Installing
```
git clone git@github.com:kayvank/event-counter.git 
```
## Running the tests
```
sbt clean test
```
## Api docs
to generate the [scaladocs](https://docs.scala-lang.org/style/scaladoc.html) for the Api:

```
sbt doc
```
## References
