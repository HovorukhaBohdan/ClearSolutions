# Clear Solutions Test Task

#### This project implements a RESTful API for managing users using Spring Boot.
#### The API manages a User resource with the following fields:
* _Email (required): Must follow a valid email format._
* _First name (required): String value._
* _Last name (required): String value._
* _Birthdate (required): LocalDate value, must be earlier than the current date._
* _Address (optional): String value._
* _Phone number (optional): String value._

#### The API provides the following functionality:
1. _Create user (POST /users/registration): Registers a new user. Users must be 18 years old or older (age limit is configurable in application.properties)._
2. _Update user partially (PATCH /users/{id}): Updates specific fields of a user identified by ID._
3. _Update user completely (PUT /users/{id}): Replaces all fields of a user identified by ID._
4. _Delete user (DELETE /users/{id}): Deletes a user identified by ID._
5. _Search for users by birth date range (GET /users): Retrieves a list of users born between a specified date range. The "from" date must be before the "to" date._

#### Implementation Details:
* _The code uses Spring Boot for web application development._
* _User data is not persisted in a database (in-memory storage is used for this test)._
* _Unit tests are included using Spring's testing framework._
* _Error handling is implemented for REST requests using appropriate HTTP status codes._
* _Error handling is implemented for REST requests using appropriate HTTP status codes._

#### Running the application:
1. _Install Java and Maven if not already installed._
2. _Clone or download this project._
3. _Open a terminal in the project directory._
4. _Run mvn clean install to build the project._
5. _Run mvn spring-boot:run to start the application._

#### Credentials for ```api/h2-console```:
* _username: sa_
* _password: sa_
