## Problem
When using the fully-qualified name of a class declared in a dependency that is transitively resolved, the `maven-dependency-plugin:analyze-only` goal fails. The goal insists that the transitive dependency is "Used undeclared", i.e. it should be declared as a first-class dependency. This should not be the case as the class is never loaded, used, or otherwise interacted with. This code just happens to declare a string with the value of a fully-qualified class name.

Scenarios where the build does NOT fail:
* A fully-qualified class name is used within the context of a larger string literal. See an example [here](src/main/java/com/mttjj/maven/dependency/plugin/demo/Demo.java#L17).
* A fully-qualified class name is used but the class does not come from a dependency anywhere in the project's dependency tree. See an example [here](src/main/java/com/mttjj/maven/dependency/plugin/demo/Demo.java#L20).

## Development Environment
```
Apache Maven 3.8.4 (9b656c72d54e5bacbed989b64718c159fe39b537)
Maven home: C:\maven\apache-maven-3.8.4
Java version: 11.0.12, vendor: Eclipse Foundation, runtime: C:\java\jdk11
Default locale: en_US, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

The build succeeds when using `maven-dependency-plugin` version `3.0.0` or earlier. Build fails with this error when using `maven-dependency-plugin` version `3.0.1` or later.

## Dependency Tree Output
```
[INFO] --- maven-dependency-plugin:3.0.1:tree (default-cli) @ maven-dependency-plugin-demo ---
[INFO] com.mttjj:maven-dependency-plugin-demo:jar:1.0-SNAPSHOT
[INFO] \- com.google.guava:guava:jar:31.0.1-jre:compile
[INFO]    +- com.google.guava:failureaccess:jar:1.0.1:compile
[INFO]    +- com.google.guava:listenablefuture:jar:9999.0-empty-to-avoid-conflict-with-guava:compile
[INFO]    +- com.google.code.findbugs:jsr305:jar:3.0.2:compile
[INFO]    +- org.checkerframework:checker-qual:jar:3.12.0:compile
[INFO]    +- com.google.errorprone:error_prone_annotations:jar:2.7.1:compile
[INFO]    \- com.google.j2objc:j2objc-annotations:jar:1.3:compile
```

## Maven Output
```
[INFO] --- maven-dependency-plugin:3.0.1:analyze-only (default) @ maven-dependency-plugin-demo ---
[WARNING] Used undeclared dependencies found:
[WARNING]    org.checkerframework:checker-qual:jar:3.12.0:compile
[INFO] Add the following to your pom to correct the missing dependencies:
[INFO]
<dependency>
  <groupId>org.checkerframework</groupId>
  <artifactId>checker-qual</artifactId>
  <version>3.12.0</version>
</dependency>
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.857 s
[INFO] Finished at: 2022-01-07T09:28:35-06:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-dependency-plugin:3.0.1:analyze-only (default) on project maven-dependency-plugin-demo: Dependency problems found -> [Help 1]
```

## Steps to recreate
1. Run `mvn clean install` on the project