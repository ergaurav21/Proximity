# Getting Started

This is a POC for Proximity Technical test. 

Technology Used
1- Java 11

2- Spring Boot

3- Spring security With JWT

4- Docker for containerization

5- Mysql for database

6- Maven for Build tool

7- Lombok to reduce boiler-plate code(To configure this application on an IDE such as IntelliJ, Lombok annotation processor needs to be enabled)

8- Swagger for documentation

9- Junit & Mockito for testing

Refer to env/database.sql for database structure.

To build application

Prerequisite
     Docker Engine should be up and running to run this application as docker container

1 - docker-compose up -d

#For API Documentation

Swagger documentation can be accessed on the below url

   http://localhost:8080/swagger-ui.html


#For JWT Token

http://localhost:8080/authenticate

Request Type: Post
Request Body : 

{
"username":"admin",
"password": "admin"
}




#Improvements

1- Caching- We can utilize Redis for caching the data. To minimize database calls to fetch data.

2- Due to time constraint, I have implemented all the api's as a monolithic architecture, Ideally I would break it in to micro-service 
architecture.
For Ex: User service, main service, analytic service etc.

3- More test coverage.




