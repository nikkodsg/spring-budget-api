# spring-budget-api
A Spring Boot based REST API for expense and budget tracking. 

## Techonologies
- Java
- Spring Boot
- Maven
- PostgreSQL
- Flyway

## Features
- Transactions - Track your incomes and expenses with description, amount, date, and category.
- Categories - Group your transactions into categories.
- Budgets - Create your own budget for each categories with a set period (Weekly, Monthly, Yearly, One Time).
- Accounts - Create your own account with an email and a password.

## Local Setup
### Database
- Install PostgreSQL in your local machine.
- You can run PostgreSQL with docker with this command:
```
docker run --name postgres-0 -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres:alpine
```
- Create `budgetapp` database. You may choose your own name but be sure to update the spring.datasource.url property in your application properties file.

### SMTP Server
- Install and run [MailDev](https://github.com/maildev/maildev) to have a fake SMTP running in your local. This will be used on the user registration process.

### Running the project
- Clone the project to your local machine.
- Inside the root directory, build the project using:
```
mvn clean install
```
- To run the project from a command line in a Terminal window, you can use the `java -jar` command. `-Dspring.profiles.active=dev` will set the active profile for development.
```
java -jar -Dspring.profiles.active=dev target/spring-budget-api.jar
```
or run using Maven:
```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
- Once the server is up and running, you may now access the REST APIs over the following base path:  `http://localhost:8080/api`
