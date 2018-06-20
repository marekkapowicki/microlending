language: java
version: oraclejdk8
open in ide: the lombok plugin is required
install: mvn clean install
install with integration tests: mvn clean install -Pitest
swagger online documentation: http://localhost:8080/swagger-ui.html
generate documentation (pdf & html): mvn clean install -Pdocumentation (available after run http://localhost:8080/docs/index.html or http://localhost:8080/docs/index.pdf)
run the application: mvn spring-boot:run
