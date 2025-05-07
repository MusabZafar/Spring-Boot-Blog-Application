
# Spring Boot Blog Application
The **Spring Boot Blog Application** is a simple blog management system built using **Java**, **Spring Boot**, and **JPA (Java Persistence API)**. This project is designed to manage blog posts, categories, comments, and user roles. It includes features like validation, error handling, and complex relationships (many-to-one, one-to-many, many-to-many) between entities. The application also supports **pagination**, **security**, and **RESTful APIs** for CRUD operations.

The application uses **PostgreSQL** as the database and is containerized with **Docker** for easy deployment.

## Features
- **JPA (Java Persistence API)**: Manages database interactions with entities such as Post, Category, Comment, and User.
- **Spring Security**: Protects REST APIs with JWT (JSON Web Token) authentication and authorization.
- **Swagger 3.0**: Provides automatic API documentation.
- **Validation**: Validates inputs using JSR-303/JSR-380 annotations.
- **Error Handling**: Handles errors and provides meaningful error messages.
- **Pagination**: Supports pagination for retrieving a limited number of blog posts.
- **Relationships**: Implements many-to-one, one-to-many, and many-to-many relationships between entities.
- **PostgreSQL**: Uses PostgreSQL as the database for storing application data.
- **Docker**: The application is containerized using Docker for simplified deployment.

## Technologies Used
- **Spring Boot** (Version 3.3.2)
- **Java 17**
- **Spring Data JPA**
- **Spring Security** (JWT authentication)
- **PostgreSQL** (Database)
- **Swagger UI** (API documentation)
- **ModelMapper** (For object mapping)
- **Lombok** (For reducing boilerplate code)
- **Docker** (Containerization)

## Project Structure
The project follows a layered architecture with a clear separation of concerns:

```
com.springboot.blogApp
│
├── config
│   └── SecurityConfig.java           # Configures Spring Security
│
├── controller
│   ├── AuthController.java           # Handles authentication-related requests
│   ├── CategoryController.java       # Handles category-related requests
│   ├── CommentController.java        # Handles comment-related requests
│   └── PostController.java           # Handles post-related requests
│
├── entity
│   ├── Category.java                 # Entity class for categories
│   ├── Comment.java                  # Entity class for comments
│   ├── Post.java                     # Entity class for blog posts
│   ├── Role.java                     # Entity class for roles
│   └── User.java                     # Entity class for users
│
├── exception
│   ├── BlogAPIException.java         # Custom exception for API errors
│   ├── GlobalExceptionHandler.java   # Handles global exceptions
│   └── ResourceNotFound.java         # Handles resource not found errors
│
├── repository
│   ├── CategoryRepository.java       # Repository for category data
│   ├── CommentRepository.java        # Repository for comment data
│   ├── PostRepository.java           # Repository for post data
│   ├── RoleRepository.java           # Repository for role data
│   └── UserRepository.java           # Repository for user data
│
├── security
│   ├── CustomUserDetailsService.java # Custom user details service
│   ├── JwtAuthenticationEntryPoint.java # Handles JWT errors
│   ├── JwtAuthenticationFilter.java # Filters authentication tokens
│   └── JwtTokenProvider.java        # JWT token creation and validation
│
├── service
│   ├── impl
│   │   ├── AuthService.java          # Service for authentication logic
│   │   ├── CategoryService.java      # Service for category logic
│   │   ├── CommentService.java       # Service for comment logic
│   │   └── PostService.java          # Service for post logic
│
└── utils
    └── AppConstants.java             # Constants used throughout the application
```

## API Documentation

You can access the Swagger-generated documentation for the REST APIs at:

```
http://localhost:8080/swagger-ui/
```

The Swagger UI will provide detailed documentation about the available endpoints, request/response formats, and other information about how to interact with the application’s API.

## Security

The application uses **JWT** (JSON Web Tokens) for authentication and authorization:
- The **AuthController** handles user registration and login.
- Upon successful login, the application generates a JWT token that can be used to authenticate subsequent API requests.

### 1. Login Endpoint:
```bash
POST /api/auth/login
```
Use this endpoint to obtain a JWT token.

### 2. Secure Endpoint:
```bash
GET /api/posts
```
This endpoint requires a valid JWT token in the `Authorization` header.

## Database Configuration

The application uses **PostgreSQL** for data storage. The connection is configured in the `application.yml` file as follows:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/blogdb
    username: yourusername
    password: yourpassword
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update  # use 'none' in production
    show-sql: true
```

Make sure you have PostgreSQL installed and running, and replace `yourusername` and `yourpassword` with your actual database credentials.

## Relationships

The application utilizes different types of relationships between entities:

### 1. **Many-to-One** (Post to Category):
A **Post** is associated with a **Category**. A category can have multiple posts, but each post belongs to one category.

```java
@ManyToOne
@JoinColumn(name = "category_id", nullable = false)
private Category category;
```

### 2. **One-to-Many** (Category to Post):
A **Category** can have multiple **Posts**. This is represented in the `Category` entity as:

```java
@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
private List<Post> posts;
```

### 3. **Many-to-Many** (Post to Tag):
A **Post** can have multiple **Tags**, and each **Tag** can be associated with multiple **Posts**.

```java
@ManyToMany
@JoinTable(
  name = "post_tag", 
  joinColumns = @JoinColumn(name = "post_id"), 
  inverseJoinColumns = @JoinColumn(name = "tag_id"))
private List<Tag> tags;
```

## Error Handling

**Global Exception Handling** is provided to handle various exceptions throughout the application. If a resource is not found or validation fails, the application will return appropriate error messages with corresponding HTTP status codes.

### Common Error Responses:
- **Validation Error**: `400 Bad Request`
- **Resource Not Found**: `404 Not Found`

## License

This project is licensed under the **Apache 2.0 License**. See the [LICENSE](https://www.javaguides.net/license) file for more information.

## Contact

For any questions or support, feel free to contact:

- **Name:** Musab
- **Email:** musabzafar03@gmail.com
