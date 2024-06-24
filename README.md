# URL Shortener Service

## Project Description

This application is a service designed to convert long URLs into short, user-friendly links. This helps save characters and makes URLs easier to remember and reproduce.

## User Roles

- **User** - A regular user of the application.
- **Admin** - A user with extended privileges who can manage other administrators and user information.

## Benefits for Users and Application Owner

- **User**:
  - Generate short links for any long URL.
  - Track click statistics.
  - Manage URL bindings (long and short links).
  
- **Application Owner**:
  - Monetize the service through subscription plans.

## Key Features

1. User registration
2. User authentication and authorization
3. Creation of URL bindings, including setting expiration time
4. Editing URL bindings
5. Closing and deleting URL bindings
6. Searching for URL bindings (by unique identifier, user, short URL)
7. Redirecting to the original long URL
8. Users can purchase subscriptions
9. Restricting access for users without an active subscription
10. Users can view click statistics for their URL bindings
11. Admins can view the top 10 most popular URL bindings

## Future Development

1. Allow users to reserve unique URL path prefixes for a fee (e.g., branding - /bmw/{id}).
2. Create multiple subscription plans with different features and pricing.
3. Develop a loyalty system with discounts for regular customers.
4. Integrate a Telegram Bot.
5. Integrate with a payment system.

## Technology Stack

| Functionality                        | Technologies                          |
|--------------------------------------|---------------------------------------|
| **Primary Framework**                | <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="Spring" width="40" height="40"/> Spring Framework 6 with Spring Boot 3 |
| **Data Storage**                     | <img src="https://www.vectorlogo.zone/logos/postgresql/postgresql-icon.svg" alt="PostgreSQL" width="40" height="40"/> PostgreSQL                            |
| **Data Access**                      | <img src="https://hibernate.org/images/hibernate.svg" alt="Hibernate" width="40" height="40"/> Spring Data JPA repositories based on Hibernate |
| **User Interaction**                 | <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="Spring" width="40" height="40"/> Spring Web MVC <img src="https://www.vectorlogo.zone/logos/thymeleaf/thymeleaf-icon.svg" alt="Thymeleaf" width="40" height="40"/> Thymeleaf <img src="https://www.vectorlogo.zone/logos/json/json-icon.svg" alt="Jackson" width="40" height="40"/> Jackson    |
| **Security**                         | <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="Spring Security" width="40" height="40"/> Spring Security                       |
| **Third-Party Service Integration**  | <img src="https://www.vectorlogo.zone/logos/feignio/feignio-icon.svg" alt="Feign" width="40" height="40"/> Spring Cloud OpenFeign                |
| **Code Quality Assurance**           | <img src="https://junit.org/junit5/assets/img/junit5-logo.png" alt="JUnit5" width="40" height="40"/> JUnit5, Mockito                       |
| **Code Coverage Evaluation**         | <img src="https://www.jetbrains.com/idea/img/idea_logo.svg" alt="IntelliJ IDEA" width="40" height="40"/> IntelliJ IDEA tools                   |
| **Code Review**                      | <img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" alt="GitHub" width="40" height="40"/> GitHub tools                          |
| **Deployment**                       | <img src="https://www.vectorlogo.zone/logos/docker/docker-icon.svg" alt="Docker" width="40" height="40"/> Docker  (Future)                              |
| **Documentation**                    | <img src="https://www.vectorlogo.zone/logos/openapis/openapis-icon.svg" alt="OpenAPI" width="40" height="40"/> JavaDoc (future), OpenAPI v3 (Swagger), SpringDoc |

## Entity Relationship Diagram

![uml](https://github.com/Anvarjon7/UrlShortener/assets/142890754/15feeb0f-912a-4505-b4c8-a4edd2559ce6)


## User Stories

- **User**:
  - As a user, I want to register in the application.
  - As a user, I want to log in using my email and password to access my account.
  - As a user, I want to recover a forgotten password via email.
  - As a user, I want to view and edit my profile information.
  - As a user, I want to purchase or cancel a subscription.
  - As a user, I want to choose a payment method when purchasing a subscription.
  - As a user, I want to reserve a unique URL path prefix.
  - As a user, I want to close and delete URL bindings.
  - As a user, I want to set an expiration time for my URL bindings.
  - As a user, I want to convert a long URL into a short URL.
  - As a user, I want to receive an error message if I provide an invalid URL.
  - As a user, I want to be redirected to the original URL when visiting a short URL.
  - As a user, I want to analyze click statistics for my URL bindings.

- **Admin**:
  - As an admin, I want to view the top-n most popular URL bindings.
  - As an admin, I want to search for URL bindings by unique identifier, user, or short URL.
  - As an admin, I want to manage other administrators and user information.
  - As an admin, I want to restrict service access for users without an active subscription.
