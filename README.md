# SPS-Backend
This is our E-commerce API that will interact with our front end application via HTTP requests.

**How to use?**

To set up the api and run it locally, we need to establish a couple environment variables for our application.

Below is the *application.yml* file where we defined the names for our env variables. 

${ env-variable-name }

```yml
    spring:

       #------ Database configurations --------
        datasource:
            url : ${DB_URL}
            username : ${DB_USERNAME}
            password : ${DB_PASSWORD}
            driver-class-name : com.mysql.cj.jdbc.Driver

        jpa :
            hibernate :
                ddl-auto : create-drop 
            show-sql : true

       #-------- EMAIL Configuration --------
    mail :
      host : ${EMAIL_HOST}
      port : ${EMAIL_PORT}
      username : ${EMAIL_USERNAME}
      password : ${EMAIL_PASSWORD}

      properties :
        mail :
          smtp :
            auth : true
            starttls :
              enable : true
              required : true

```
We then need to define them.
- go to: run > edit configurations
- Create a configuration, if not present, and add the value of the variables in the Environment variables section:
<img src="https://i.imgur.com/rTpQrf0.gif" alt="Environment Variables"/>
- Once defined you can run the application flawlessly.

## Software Engineering Key Points
While creating the application I designed a response factory. The response factory is able to use the flexible JsonNode class
to interpret different requests and return a malleable object.

For this project I decided to expand my knowledge in Spring Security and design my own User Entity Class,
which generates a JWT token, and implements my own verification methods.
I Included in this project an email verification feature, which allows the users in the website to have an extra level of security.

The project however is still missing key components:
    - end to end payments.
    - Adding pictures for each listing
    - Sending OTP via sms

Hopefully in the future I will be able to continue developing this application and be able to officially deploy it.

## Endpoints
Some endpoints for this application include:

    ADMIN 'base_url/admin'
        GET Request
        - '/get-listing/{listing_id}
                returns the specific listing with the ID
        
        GET Request
        - '/all-listings'
                returns a list of all listings
        
        GET Request
        - '/all-users
                returns a list of all users
    
    AUTHENTICATION 'base_URL/
        POST Request
        - '/login'
                The body must include the Userlogin DTO
                returns the user entity if valid as well as a JWT

        POST Request
        - '/register'
                The body includes the UserRegistration DTO

        GET Request
        - '/register/confirm-account/tokenId=?{tokenId}
                The Token is passed in as a param
                this will return a JWT that is used for every request
    
        GET Request
        - '/verify-otp/{otp}
                The OTP is passed in as a path variable
                Returns if valid a jwt is not it will return an error message.
