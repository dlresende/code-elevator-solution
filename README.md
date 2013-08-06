# The Code Elevator Game
My solution using an embedded Java server for the [Code Elevator Game](https://github.com/xebia-france/code-elevator). The [event](http://blog.xebia.fr/2013/07/15/techevent-code-elevator-un-concours-de-programmation/) toke place at Xebia France on 22 Jully 2013.

## Install
- `mvn clean install`

## Run
- `mvn exec:java -Dexec.mainClass="net.diegolemos.codeelevator.MyHttpServer"`

The server listens on port 8082: http://localhost:8082/test
