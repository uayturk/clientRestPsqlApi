
***ASSESMENT***

<br/>ClientRestPsqlApi is the transaction RESTful service Api with Spring Boot and PostgreSQL database. ClientRestPsqlApi is designed to manage transaction for Debit and Credit accounts. There is a clients has Credit and Debit accounts. ClientRestPsqlApi manages transactions and their account types. If any client make transaction over balance, client's account type change by system and over balance added to changed account type balance. If any client make transaction and client's balance is available, transaction change is applied to the balance and account type remains the same. Transations,account and client informations,accout type information are saved to the postgreSQL. ClientRestPsqlApi also allows the get accounts by using id and clientId. New accounts can be registered, update or delete existing accounts. About clients, you can get all clients or can get specific clients by using client Id specifically. New clients can be registered, update or delete existing clients. About transaction, you can get transaction by using account Id. saveTransaction method is save transactions which are completed.

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
