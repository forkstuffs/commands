# Commands
Object-Oriented command library for each server software.

[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)
[![rultor](https://www.rultor.com/b/yegor256/rultor)](https://www.rultor.com/p/portlek/configs)

[![Build Status](https://travis-ci.com/portlek/commands.svg?branch=master)](https://travis-ci.com/portlek/commands)
![Maven Central](https://img.shields.io/maven-central/v/io.github.portlek/commands?label=version)
## Setup

<details>
<summary>Gradle</summary>

```gradle
repositories {
    mavenCentral()
}

dependencies {
    // For the all project type
    implementation("io.github.portlek:commands-common:${version}")
    // For the bukkit projects
    implementation("io.github.portlek:commands-bukkit:${version}")
}
```
</details>

<details>
<summary>Maven</summary>

```xml
<dependencies>
    <!-- For the all project type -->
    <dependency>
      <groupId>io.github.portlek</groupId>
      <artifactId>commands-common</artifactId>
      <version>${version}</version>
    </dependency>
    <!-- For the bukkit projects -->
    <dependency>
      <groupId>io.github.portlek</groupId>
      <artifactId>commands-bukkit</artifactId>
      <version>${version}</version>
    </dependency>
</dependencies>
```
</details>