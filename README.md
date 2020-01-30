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

### How to run app

##### Using Maven
```sh
mvn exec:java
```

##### From executable jar
```sh
java -jar money-transfer-app.jar
```

### How to run unit tests

##### REST controllers
```sh
mvn surefire:test -Dtest=UserControllerTest
mvn surefire:test -Dtest=AccountControllerTest
mvn surefire:test -Dtest=TransactionControllerTest
```

##### Internal services
```sh
mvn surefire:test -Dtest=UserServiceTest
mvn surefire:test -Dtest=AccountServiceTest
mvn surefire:test -Dtest=TransactionServiceTest
```

### Available Endpoints

| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| POST | /user/create | create a new user |
| PUT | /user/{id} | update user |  
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
| DELETE | /transaction/{id} | revert money transfer | 
| GET | /transaction/{id} | get transaction by id |
| GET | /transaction/account/{id} | get transactions by account id |
| GET | /transaction/account/{id}/outcome | get outcome transactions by account id |
| GET | /transaction/account/{id}/income | get income transactions by account id | 
| GET | /transaction/user/{id} | get transactions by user id |
| GET | /transaction/user/{id}/outcome | get outcome transactions by user id |
| GET | /transaction/user/{id}/income | get income transactions by user id | 
| GET | /transaction/all | get all transactions | 

### Entities JSON structure

##### User
```sh
{
    "id": 1,
    "firstName": "Aleksei",
    "lastName": "Domozhirov",
}
```

##### Account
```sh
{
    "id": 1,
    "userId": 1,
    "currencyCode": "USD",
    "balance": 100.0
}
```

##### Transaction
```sh
{
    "id": 1,
    "sourceAccountId": 1,
    "targetAccountId": 2,
    "amount": 30.0
}
```

### Server Errors

All handled errors returns as an Error JSON object in response. For example:
 ```sh
 {
     "status": 400,
     "message": "Cannot find account by id=1"
 }
 ```

### Constraints
- User cannot be deleted if there are related accounts with this User
- Supported currency codes are: USD, EUR, PLN, RUB
- Transaction amount should be positive
- Transaction cannot be reverted if ether source or target Account have been deleted
- Money transfer is not allowed between accounts with different currency 

### Http Status
- 200 OK 
- 400 Bad Request
- 404 Not Found 
- 500 Internal Server Error 