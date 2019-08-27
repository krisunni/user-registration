# User Registration Microservice
User API service with the following features.
- Create, Read, Update, Delete User
- Filtered Users with List of not null columns and Start Index and Page Size

# Prereqsuites

- [Maven]
- [Docker]
- [Docker Compose]

# Development Guide
To Start development server please run the following
`` ./mvnw spring-boot:run``
If you are using IDE
Run the `main()` in `UserApplication.java`

By Default Development will use non-persisting H2 database. 
To Use PostgreSQL use prod profile.
Prod profile looks for the following envrioment variables for Database connections.
```
DB_URL=jdbc:postgresql://postgresql:5432/user
DB_USER=user
DB_PASSWORD=user
```
# Production Guide
1. Build the project using maven `mvn clean package`
2. Run following command to start server in detached mode.
```
docker-compose up -d
```
# Swagger URLs
1. Swagger UI
`http://[HOSTNAME]:[PORT]/swagger-ui.html`
- For local development
`http://localhost:8080/swagger-ui.html`
2. Swagger Specs
`http://[HOSTNAME]:[PORT]/v2/api-docs?group=user-api`
- For local development
`http://localhost:8080/v2/api-docs?group=user-api`

[Maven]: https://maven.apache.org/
[Docker]: https://www.docker.com/
[Docker Compose]: https://docs.docker.com/compose/
