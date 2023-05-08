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
- Modular-System - Create your own Modules!
- Compatible with [PointsAPI](https://github.com/teraprath/PointsAPI)

![image](https://i.ibb.co/mckgGBV/Screenshot-2023-05-08-221512.png)
Example Module: https://github.com/teraprath/ExampleModule/

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
public class TestModule extends Module {

    public Module() {
        super(new ModuleItem());
    }
    
    @Override
    public void onEnable() {

        // Startup logic
        
        registerListener(new TestListener());
        registerSubCommand(new TestSubCommand());
        
    }

    @Override
    public void onDisable() {
        // Shutdown logic
    }
    
    @Override
    public void onItem(PlayerInteractEvent e) {
    
        // Add your code
       
    }

}

```
Create a `module.yml` in your `resources` folder:
```yaml
name: TestModule
version: 1.0
main: org.yourpackage.TestModule
author: YourName
```

Visit [wiki](https://github.com/teraprath/Lobby/wiki/) page to see usage guide.(soon)
