
# SocialMedia Project

## Description

The **SocialMedia** project is a social media platform where users can create posts, upload images, comment, and like posts. Built with **Spring Boot** and **JWT authentication**, this project demonstrates backend functionality for user interactions.

## Features

- **User Authentication** with JWT (Register/Login)
- **Create Posts** with text and images
- **Comment and Like** posts

## Technologies Used

- **Spring Boot**
- **Spring Data JPA**
- **JWT Authentication**
- **MySQL** (or your preferred database)
- **Java 17**

## Installation

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/socialMedia.git
   cd socialMedia
   ```

2. Set up your database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/social_media_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. Build and run the project:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Access the app:
   ```
   http://localhost:8080
   ```

## JWT Authentication

- After logging in, you’ll receive a JWT token.
- Include the token in the `Authorization` header as `Bearer <token>` for all endpoints that require authentication.
