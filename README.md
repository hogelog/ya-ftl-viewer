# ya-ftl-viwer
For easy mock development with freemarker.

## Feture
- Show freemarker (.ftl) file
- Import data from JSON
- Serve static file (js, css, png, html, …)
- Stand alone application (no servlet container)

## Example
[src/test/resources/config.json](https://github.com/hogelog/ya-ftl-viewer/blob/master/src/test/resources/config.json) and [src/test/resources/web](https://github.com/hogelog/ya-ftl-viewer/tree/master/src/test/resources/web) are example.

### Run with maven
```sh:
$ mvn clean compile exec:java
```
- access [http://localhost:9997/hoge](http://localhost:9997/hoge)

### Run with jar
```sh:
$ mvn clean package shade:shade
$ java -jar target/ya-ftl-viewer.jar src/test/resources/config.json
```
- access [http://localhost:9997/hoge](http://localhost:9997/hoge)

## Todo
- Support LiveReload
- Support YAML