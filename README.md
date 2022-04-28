# member-registration
This project made by Java Spring Boot with JASON Web Tokens authentication.

This is member registration separated by salary with 3 tiers
1. more than 50,000฿ is Platinum
2. between 30,000฿ to 50,000฿ is Gold
3. between 15,000฿ to 29,999฿ is Silver

If user has salary less than 15,000฿ will be rejected with error code RGTE1002 (salary lower than 15000)

The registration service have 7 Api services as follows :
1. POST - /register = register user with member type classify
2. POST - /login = login user to use any service
3. GET - /v1/user/list = get all user profile
4. GET - /v1/user/{reference-code} = get user profile by reference code
5. PUT - /v1/user/update = update user profile
6. POST - /v1/wallet/top-up = top-up money to user wallet
7. GET - /v1/wallet/{reference-code} = get wallet data

***NOTICE All api services exclude register and login need to authen first because it use Baerar token autorization. 

Setup guide
1. Database setup

I have default setting database with my cloud MySQL DB. The setting in "/src/main/resources/application.properties". You can change it with your database. It will auto create table when you run at first time by Hibernate ddl auto.

2. Postman collection

You can import postman collection and environment in "/src/main/postman" that's all api services collection.

3. Run Spring boot

After you config database and import postman collection, you can run project from "src/main/java/com/test/registation/RegistationSystemApplication.java"
