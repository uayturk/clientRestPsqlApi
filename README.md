
***ASSESMENT***

<br/>Transaction RESTful service Api with embedded apache tomcat as servlet container. Used Spring Boot as framework, PostgreSQL used as database.

***RUN***

Firstly,you should complete installation of postgres before the running our service, afterwards you need to package it with;

```mvn clean package```

You'll see that there're controller tests.

For the run tests;

```mvn test```

If you wanna change default configuration,parameters set in src/main/resources/application.properties you need to give a new properties file with the following parameter;

```java -jar target/assessment-1.0.0-SNAPSHOT.jar --spring.config.location=file:////home/ufuk/my_application.properties```

***Swagger UI***

<br/>By default this assesment will be executed on 8080 port and you'll see the entire endpoints from http://localhost:8080/swagger-ui.html

***IDE***

<br/>For this service we used smart IDE intellij and you can easily start our spring boot application from ```src/main/java/com.ufuk.clientRestPsqlApi/ClientRestPsqlApiApplication``` class.
