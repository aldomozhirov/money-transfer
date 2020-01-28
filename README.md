# Money Transfer RESTful API

A Java RESTful API for money transfers between users accounts (including data model and the backing implementation)

### Explicit requirements
1. You can use Java, Scala or Kotlin.
2. Keep it simple and to the point (e.g. no need to implement any authentication).
3. Assume the API is invoked by multiple systems and services on behalf of end users.
4. You can use frameworks/libraries if you like (except Spring), but don't forget about
requirement #2 – keep it simple and avoid heavy frameworks.
5. The datastore should run in-memory for the sake of this test.
6. The final result should be executable as a standalone program (should not require
a pre-installed container/server).
7. Demonstrate with tests that the API works as expected.

### Technologies
- Java 8
- Maven
- JUnit 4
- Jetty Container
- Apache HTTP Client (for unit tests)

### How to run
```sh
mvn exec:java
```

### Available Endpoints

| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| POST | /user/create | create a new user | 
| DELETE | /user/{id} | delete user | 
| GET | /user/{id} | get user by id | 
| GET | /user/all | get all users | 
| POST | /account/create | create a new user | 
| DELETE | /account/{id} | delete account | 
| GET | /account/{id} | get account by id | 
| GET | /account/user/{id} | get accounts by user id | 
| GET | /account/{id}/balance | get account balance by id | 
| GET | /account/all | get all accounts |
| POST | /transaction | perform money transfer |
| GET | /transaction/{id} | get transaction by id |
| GET | /transaction/account/{id} | get transactions by account id |
| GET | /transaction/account/{id}/outcome | get outcome transactions by account id |
| GET | /transaction/account/{id}/income | get income transactions by account id | 
| GET | /transaction/user/{id} | get transactions by user id |
| GET | /transaction/user/{id}/outcome | get outcome transactions by user id |
| GET | /transaction/user/{id}/income | get income transactions by user id | 
| GET | /transaction/all | get all transactions | 

### Http Status
- 200 OK 
- 400 Bad Request
- 404 Not Found 
- 500 Internal Server Error 