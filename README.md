[Restaurant Voting Application](https://github.com/stadnikov/restaurant-voting)
===============================

#### <u>About</u>

Here you can find voting app for the restaurant with the best menu for today

#### <u>Build and Deploy the Project</u>

    mvn clean install

This is a Spring Boot project, so you can deploy it by simply using the main class: VoteforlunchApplication.java

Once deployed, you can access the app at: http://localhost:8080

Mind that this application is backend only.

Link to Swagger: http://localhost:8080/swagger-ui.html

#### <u>Software Requirement Specification</u>

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote for a restaurant they want to have lunch at today
* Only one vote counted per user
* If user votes again the same day:
* If it is before 11:00 we assume that he changed his mind.
* If it is after 11:00 then it is too late, vote can't be changed
* Each restaurant provides a new menu each day.

-------------------------------------------------------------
- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 3.x, Lombok, H2, Caffeine Cache, SpringDoc OpenApi 2.x, Mapstruct, Liquibase 
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui/index.html)  
Credentials:

```
User:  user@yandex.ru / password
Admin: admin@gmail.com / admin
Guest: guest@gmail.com / guest
```
