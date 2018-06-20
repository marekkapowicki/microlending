# microlending

spring boot microservice provides rest API
the backend is shipped together with the [swagger documentation](http://localhost:8080/swagger-ui.html) thart
can be used to check and test the API
## Lombok
open in ide: the [lombok](open in ide: the lombok(https://projectlombok.org/) plugin is required
) plugin is required
## Getting Started

The [ports and adapters](http://www.dossier-andreas.net/software_architecture/ports_and_adapters.html) architecture is used in the application. 

* domain - is the most important and stable package. All classes in domain are package protected. The Facade class is a public gatway to the domain. It is visible for everyone and it handles the transactions
* adapter - csv import
* rest controllers

### Data model  
The application uses Spring data together with liquibase to generaate/handle tables in h2 database
#### CQRS
There are two separate sources to read and write data. 

### Installing

build the application

```
mvn clean install
```

build and execute integration test
```
 mvn clean install -Pitest
```
generate the pdf and html documentation based on swagger annotations
```
 mvn clean install -Pdocumentation
```
The documentation is available after run [html](http://localhost:8080/docs/index.html) or [pdf](http://localhost:8080/docs/index.pdf)


## Tests

The spock and groovy was used to create the test specifications. The number of tests is really low (my assuption was to finish the application in 30 hours)
* Spec - unit tests
* ITSpec - integration tests
* ProductControllerSpec - test the controller

## Running 

```
 mvn spring-boot:run
```

## Built With

* [spring boot](https://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spock](http://spockframework.org/) - testing framework
* [OpenCsv](http://opencsv.sourceforge.net/)
* [Swagger](https://swagger.io/) documentation online
* [Rest-Assured](http://rest-assured.io/) testing api
