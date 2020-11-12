![](https://img.shields.io/travis/luismanuelamengual/NeoGroup-Warp.svg) 
![](https://img.shields.io/github/license/luismanuelamengual/NeoGroup-Warp.svg)
![](https://img.shields.io/maven-central/v/com.github.luismanuelamengual/NeoGroup-Warp.svg)
![](https://img.shields.io/github/forks/luismanuelamengual/NeoGroup-Warp.svg?style=social&label=Fork)
![](https://img.shields.io/github/stars/luismanuelamengual/NeoGroup-Warp.svg?style=social&label=Star)
![](https://img.shields.io/github/watchers/luismanuelamengual/NeoGroup-Warp.svg?style=social&label=Watch)
![](https://img.shields.io/github/followers/luismanuelamengual.svg?style=social&label=Follow)

# NeoGroup-Warp

Warp is an open source, easy to use web framework for java.

Installation
---------------

Para la instalación de Warp se debe ingresar el siguiente bloque de código en el archivo pom.xml

````xml
<dependency>
    <groupId>com.github.luismanuelamengual</groupId>
    <artifactId>NeoGroup-Warp</artifactId>
    <version>{version}</version>
</dependency>
````

Getting started
---------------

El siguiente ejemplo muestra como poner a correr un servidor Warp escuchando en el puerto 8080.

````java
package example;

import org.neogroup.warp.WarpApplication;

public class Main {

    public static void main(String[] args) {
        WarpApplication application = new WarpApplication(8080, true);
        application.start();
    }
}
````

Controllers
---------------

Resources
---------------

Models
---------------

Views
---------------

Properties
---------------

Logging
---------------

Localization
---------------

Deployment
---------------
