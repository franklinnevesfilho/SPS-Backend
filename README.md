# SNB-Backend
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

## Endpoints
    TBD...

