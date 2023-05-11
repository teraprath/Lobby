<!--suppress HtmlDeprecatedAttribute -->
<div align="center">

[![](https://jitpack.io/v/teraprath/Lobby.svg)](https://jitpack.io/#teraprath/Lobby)
<div>
    <h1>Lobby</h1>
    <p>Modular Plugin for Spigot 1.19+<p>
</div>
</div>

## Features

- Easy to use and lightweight
- World & Player Protection
- 100% Configurable
- Modular-System (Custom Modules)
- Premade Modules (Navigator, Player-Hider, Cosmetics & more) [SOON]
- Compatible with [PointsAPI](https://github.com/teraprath/PointsAPI)

## Implementation

You can see the latest version [here](https://github.com/teraprath/Lobby/releases/latest).

**Using Maven:**

````xml

<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
````

````xml

<dependency>
    <groupId>com.github.teraprath</groupId>
    <artifactId>Lobby</artifactId>
    <version>INSERT_VERSION_HERE</version>
</dependency>
````

**Using Gradle:**
````groovy
repositories {
    maven { url 'https://jitpack.io' }
}
````
````
dependencies {
    implementation 'com.github.teraprath:Lobby:INSERT_VERSION_HERE'
}
````

## Create Module
An example code for a custom module.


```java
public class ExampleModule extends Module {

    public ExampleModule() {
        super(new ModuleItem(itemstack, defaultSlot));
    }

    @Override
    public void onEnable() {

        // Register events
        registerListener(listener);

        // Register sub-commands
        registerSubCommand("example", subCommand);

    }

    @Override
    public void onDisable() {
        // Shutdown logic
    }

    @Override
    public void onItemClick(PlayerInteractEvent e) {
        // Module-Item interact logic
    }

}

```
Create a `module.yml` in your `resources` folder:
```yaml
name: TestModule
version: 1.0
main: org.example.ExampleModule
author: YourName
```
Full Example Module: https://github.com/teraprath/ExampleModule/

Visit [wiki](https://github.com/teraprath/Lobby/wiki/) page to see usage guide.(soon)
