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
    // For the bukkit projects
    implementation("io.github.portlek:commands-bukkit:${version}")
}
```
</details>

<details>
<summary>Maven</summary>

```xml
<dependencies>
    <!-- For the bukkit projects -->
    <dependency>
      <groupId>io.github.portlek</groupId>
      <artifactId>commands-bukkit</artifactId>
      <version>${version}</version>
    </dependency>
</dependencies>
```
</details>

## Usage

### Creating a command

<details>
<summary>Bukkit</summary>

```java
public final class CreatingCommandExample {
    
    private final Plugin plugin;
    
    public CreatingCommandExample(@NotNull final Plugin plugin) {
        this.plugin = plugin;
    }

    void creation() {
        final BukkitCommandRegistry bukkitCommandRegistry = new BukkitCommandRegistry(this.plugin);
        final BasicCmd testCommand = new BasicCmd("test-command")
            .aliases("test-aliases")
            .permission("plugin.test-command.main")
            .guard(context ->
                true)
            .execute(context -> {
                // executes /test-command
            })
            .createSub("message", subCmd -> subCmd
                .permission("plugin.test-command.message")
                .executePrevious()
                .createSub("player-argument", playerSub -> playerSub
                    .type(BukkitArgType.players())
                    .execute(context -> {
                        // executes /test-command message <online-players>
                    })))
            .createSub("test-sub", sub -> sub
                .permission("plugin.test-command.test-sub")
                .execute(context -> {
                    // executes /test-command test-sub
                })
                .createSub("test-sub-sub", subsub -> subsub
                    .permission("plugin.test-command.test-sub.sub")
                    .execute(context -> {
                        // executes /test-command test-sub test-subsub
                    })))
            .createSub(new BasicSubCmd("test-sub-2"), subCmd -> subCmd
                .permission("plugin.test-command.test-sub-2")
                .type(ArgType.literal("asd", "dsa", "sdda"))
                .execute(context -> {
                    // executes /test-command [asd|dsa|sdda]
                }));
        bukkitCommandRegistry.register(testCommand);
    }
    
}
```

The result will be like that;

```yml
test: 'test'
test-section:
  test-section-string: 'test'
```
</details>
