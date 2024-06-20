UrlShortener
Purpose of the Application

This application is designed to convert long URLs into short, user-friendly links. This service is useful for saving characters, ease of memorization, and easy reproduction.

General Introduction

This application is a service development that transforms long URLs into short, convenient links. It is useful for saving characters, ease of memorization, and reproduction.

Users List

User: A regular user of the application.
Admin: A user with extended rights who has access to add and delete other administrators and add, delete,
and modify user information.

Benefits for Future Users and Application Owners

User: Ability to generate a short link for any long URL, track click statistics,
and manage URL bindings (long and short link pairs).
Application Owner: Ability to monetize the service through subscription mechanisms.

Core Functionality
Based on the User Stories provided in the application, the core functionalities are:

User Registration
User Authentication and Authorization
Creation of URL bindings, including with a lifetime
Modification of URL bindings
Closure and deletion of URL bindings
Search for URL bindings (by unique identifier, by user, by short URL)
Redirect to the original URL
Users can purchase a subscription
Restrict access to the service for users without an active subscription
Users can view click statistics for their URL bindings
Admins can view the top-n most popular URL bindings

Domain Model
Entities

User: Application user with fields for password, email, and role(s) for security.
Id (Long): Unique identifier
firstName (String): User's firstName
lastname (String): User's lastname
Email (String): User's email address, used as username
Password (String): User's password
Role (Enum): User's role, which defines access to application resources

Subscription: User subscription to the service.
Id (Long): Unique identifier
creationDate (LocalDate): Date of subscription creation
expirationDate (LocalDate): Subscription expiration date
status (Enum): (Paid or Unpaid)

UrlBinding: Binding of short and long URLs.
Id (Long): Unique identifier
originalUrl (String): Original long URL
baseUrl (String): Base URL of the short link
pathPrefix (String): Path prefix of the short URL
uId (String): Unique identifier of the short link
count (Long): Number of clicks on the short link

SubscriptionFee: Subscription fee.
Id (Long): Unique identifier
price (BigDecimal): Cost of the minimum subscription (per month)
creationDate (LocalDate): Date of price creation

Entity Relationships
User is related to UrlBinding as one-to-many.
User is related to Subscription as one-to-many.

Business Rules
Restrict access for users without an active subscription.

Application Core Logic
Services:

UserService: Manages application users.

Functions:
Register a new user
Authenticate and authorize users
Update user information
Delete user accounts

SubscriptionService: Manages user subscriptions to the service.

Functions:
Create a new subscription
Find the current subscription
Calculate the subscription cost

UrlBindingService: Manages the creation and management of URL bindings.

Functions:
Create a new URL binding
Modify existing URL binding parameters
Delete URL binding
Search URL bindings by various parameters
Get original URL by short link for redirection

StatisticsService: Provides click statistics for short links.

Functions:
Get click statistics for user’s short links
Get the top-n popular short links in the application
Get all users with their statistics

Data Storage with PostgreSQL
Repositories or DAO Classes

UserRepository: Interacts with user data.

Save new users
Retrieve user information
Update user data
Delete users

SubscriptionRepository: Manages user subscription data.

Save new subscriptions
Retrieve subscription information
Update subscription data
Delete subscriptions

UrlBindingRepository: Manages URL binding data.

Save new URL bindings
Retrieve URL binding information
Update URL binding data
Delete URL bindings

StatisticsRepository: Provides access to click statistics data.

Save click statistics data
Retrieve click reports

User Interaction
REST API
DTOs (Purpose and Composition)
UserDTO: Contains user information such as name, email, and role.
SubscriptionDTO: Contains subscription information such as status, creation date, and expiration date.
UrlBindingDTO: Contains URL binding information such as original URL, short URL, and expiration date.

Controllers and Endpoints

UserController:

POST /api/users/register: Register a new user.
POST /api/users/login: Authenticate a user.
GET /api/users/{id}: Get user information by ID.
PUT /api/users/{id}: Update user information.
DELETE /api/users/{id}: Delete a user.
GET /api/users/{email}: Get user information by email.
GET /api/users: Get information about all users.

SubscriptionController:

POST /api/subscriptions/create/{userId}: Create a new subscription.
GET /api/subscriptions/{id}: Get subscription by ID.
GET /api/subscriptions/user/{userId}: Get subscription by user ID.
DELETE /api/subscriptions/{subscriptionId}: Cancel a subscription.

UrlBindingController:

POST /api/url-bindings/create: Create a new URL binding.
PUT /api/url-bindings/{id}: Modify URL binding parameters.
DELETE /api/url-bindings/{id}: Delete a URL binding.
GET /api/url-bindings/{uid}: Get information by short link unique identifier (UID).
GET /api/url-bindings/getByUserId/{userId}: Get URL bindings by user ID.

StatisticsController:

GET /api/statistics/{userId}: Get statistics by user ID.
GET /api/statistics/top: Get the top-10 most popular URL bindings.
GET /api/statistics/users: Get all users with their statistics.

View Controllers
Forms
View Templates

Security with Spring Security
Authentication Method

Login form using JWT (JSON Web Token) for credential transmission and authentication confirmation.
Roles and Endpoints Accessible to These Roles

User:
/api/url-bindings/ - create, modify, and delete URL bindings
/api/statistics/ - get URL click statistics

Admin:
/api/users/ - manage users
/api/subscriptions/ - manage subscriptions

Integrations with Third-Party Services
Purpose of Integration
Payment system, file storage, etc.

API Description
Register and authenticate your application with the third-party service.

Code Quality Assurance
Unit Tests

Services, domain model classes, mappers using JUnit 5 and Mockito.
Purpose: Ensure the correct functioning of individual application components.

JUnit5: Framework for writing and running test cases.
Mockito: Library for creating mock objects in tests.

Documentation
API Documentation with Swagger and SpringDoc

Future Project Development

Provide users with the ability to reserve path prefixes for a fee (e.g., branding - /bmw/{id}).
Create several subscription options with different functionality and cost.
Develop a loyalty system with discounts for regular customers.
Connect a Telegram Bot.
Integrate with a payment system.

Technical Details
Technologies and Programming Languages
Java version 21
Spring Boot version 3.2.3

Technology Stack
Application Framework: Spring Framework 6 with Spring Boot 3
Data Storage: PostgreSQL
Data Access: Spring Data JPA based on Hibernate
User Interaction: Spring Web MVC
View Templates: Thymeleaf (future)
Object Mapping to JSON: Jackson
Security: Spring Security
Third-Party Service Integration: Spring Cloud OpenFeign (future)

Code Quality Assurance:
Unit Testing: JUnit5, Mockito
Code Coverage Assessment: IntelliJ IDEA tools
Code Review: GitHub tools
Documentation:
Code Documentation: JavaDoc (future)
API Documentation: OpenAPI v3 (Swagger), SpringDoc
