# HonyaApp-Distributed-Application 

## About this project
This is an Ecommerce Application for online sales of Books, where users can adds books to the cart and buy books of their choice and can also rate the books.
Application is developed using Java, Spring and React. Using Spring Cloud Microservices and Spring Boot Framework extensively to make this application distributed. 

## Frontend Checkout Flow
![CheckOutFlow](https://user-images.githubusercontent.com/14878408/103235826-06d5ca00-4969-11eb-87c8-ce618034b4f3.gif)

## Backend Services Available
```
Api Gateway Service       : 8765
Eureka Discovery Service  : 8761
Account Service           : 4001
Billing Service           : 5001
Catalog Service           : 6001
Order Service             : 7001
Payment Service           : 8001
```

## Swagger API sec is available under folder 
```
/api-spec
```
## Open Source Libs used and respective versions verified with 
```
Java Version: openjdk 17
Node Version : v10.24.1
npm Version: 6.14.12
```
## Run this project front end in Local Machine

Navigate to `honya-frontend-react-app` folder
Run below commnads to start Frontend React Application

```
npm install
npm start
```
<hr>

To Get `access_token` for the user, you need `clientId` and `clientSecret`

```
clientId : '93ed453e-b7ac-4192-a6d4-c45fae0d99ac'
clientSecret : 'client.devd123'
```

There are 2 users in the system currently. 
ADMIN, NORMAL USER

```
Admin 
userName: 'admin.admin'
password: 'admin.devd123'
```

```
Normal User 
userName: 'devd.cores'
password: 'cores.devd123'
```

*To get the accessToken (Admin User)* 

```curl 93ed453e-b7ac-4192-a6d4-c45fae0d99ac:client.devd123@localhost:4001/oauth/token -d grant_type=password -d username=admin.admin -d password=admin.devd123```

<hr>
