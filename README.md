# Template Spring with security

This is a template for a CRUD api using:

* Java 17
* Spring Boot
* PostgreSQL
* Liquibase
* Swagger
* Docker

## Docker

If you want to run it with docker:

```bash
git clone https://github.com/heavynimbus/Spring-template.git
cd Spring-template

docker-compose up -d --build
```

Services will open the following ports:

| service     | ports      |
|-------------|------------|
| postgres-db | 10000:5432 |
| api-backend | 10080:8080 |

## API

A swagger documentation is available at http://localhost:10080/swagger-ui.html

### Database

The database is initialized by Liquibase to be able to do migrations in production, and Spring Data JPA at the runtime.

There are two existing entities:

* `account` table that contains all registered users
* `role` table that contains the different roles

These tables are joined by a many-to-many relation.

### Security

The api is secured by JWT token. You have to call `/login` with good credentials to retrieve a token. All tokens are
signed and checked.

There are 3 roles:

* `ADMIN` allows the user to access to `/admin/**` resources
* `STAFF` allows the user to access to `/staff/**` resources
* `USER` allows the user to access to `/user/**` resources

An account can have multiple roles.


**Generate token**

![image](https://user-images.githubusercontent.com/45078901/161392870-bd399b6e-52e5-4de7-a2bb-c19b4c71fbbb.png)
![image](https://user-images.githubusercontent.com/45078901/161392881-95636bbd-494f-4f07-a0c7-2b65666f84a5.png)


**Validating token**

![image](https://user-images.githubusercontent.com/45078901/161392902-02241f7e-98ff-4894-9733-96e61421a84e.png)

[See Javainuse post about spring-boot jwt](https://www.javainuse.com/spring/boot-jwt)

