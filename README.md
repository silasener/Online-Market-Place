# Online Marketplace

This project implements a web application for an Online Marketplace using the Spring Boot framework for the backend and ReactJS for the frontend. The application utilizes Spring Security for authentication and authorization, Hibernate with JPA for database operations, and MySQL as the database. 

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Usage](#usage)

## Overview

The Online Marketplace application is designed to handle products, sellers, users, and their interactions, including features such as user authentication, product management, and favorite/blacklist functionalities. It distinguishes between Admin and regular users, with Admins having additional privileges.


## Features

### Functional Requirements

- **Admins and Users**: Users can log in to the system.
- **Admin Capabilities**:
  - Add, delete, and update products, users, and sellers.
  - Search and filter products, sellers, and users.
- **User Capabilities**:
  - View a list of products.
  - Add/remove products to/from the favorite list.
  - Add/remove sellers to/from the blacklist.
  - Search products by name and filter by category and brand.

### Technical Requirements

- Two user roles: Admin & End-user.
- Spring Security for authentication and authorization.
- Hibernate with JPA annotations for database operations.
- MySQL for database storage.
- ReactJS for the frontend with routing, API handling, and state management.


## Technologies

- **Backend**:
  - Spring Boot
  - Spring Security
  - Hibernate with JPA
  - MySQL
- **Frontend**:
  - ReactJS
  - React Router
  - Axios
  - React Hooks (e.g., useEffect, useState)


## Project Structure

- **Model**: Entity classes representing database tables (e.g., User, Product, Seller).
- **Repository**: Interfaces for data access operations (e.g., UserRepository, ProductRepository).
- **Service**: Business logic and service layer (e.g., UserService, ProductService).
- **Controller**: Handles HTTP requests and responses (e.g., UserController, ProductController).
- **DTO**: Data Transfer Objects for communication between frontend and backend (e.g., UserDto, ProductDto).
- **Mapper**: Converts between entity classes and DTOs (e.g., UserMapper, ProductMapper).
- **Exception**: Custom exceptions for error handling (e.g., UserNotFoundException).
- **Security**: Configuration for authentication and authorization (e.g., WebSecurityConfig, UserAuthDetails).


## Getting Started

Follow the steps below to run the Online Marketplace application locally.

### Prerequisites

Make sure you have the following installed on your machine:

- Java 11 or later
- Maven 3.6+
- MySQL

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-username/OnlineMarketPlace.git
   cd OnlineMarketPlace
   ```

2. **Configure the Database**

   Create a new MySQL database:

   ```sql
   CREATE DATABASE : your-database;
   ```

   Update the `application.yaml` file with your MySQL credentials:

   ```properties
   spring:
    datasource:
      url: jdbc:mysql://localhost:3306/your-database
      username: your-username
      password: your-password
   ```

3. **Build and Run the Application**

   Build and run the application using Maven:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   The application will be available at `http://localhost:8080`.


## Configuration

Update the application.yaml file in the backend directory to configure database connection and other settings. Ensure that the spring.datasource.url, spring.datasource.username, and spring.datasource.password fields are set correctly.

## Usage

### Access the Application

Open the following URLs in your browser:

- **Backend:** [http://localhost:8080](http://localhost:8080)

### API Endpoints

#### Login

- **POST** `/api/v1/login`  
  Authenticate a user.

#### Products

- **GET** `/api/v1/products`  
 **Description**: Retrieve a list of products.
- **GET** `/api/v1/products/{id}`  
 **Description**: Retrieve a product by its ID.

#### Sellers

- **GET** `/api/v1/users/sellers`  
 **Description**: Retrieve a list of sellers.
- **GET** `/api/v1/users/sellers/{id}`  
 **Description**: Retrieve a seller by their ID.

#### Favorite List

- **GET** `/api/v1/users/{userId}/favorites`  
 **Description**: Retrieve the favorite products of a user.
- **POST** `/api/v1/users/{userId}/favorites/{productId}`  
**Description**: Add a product to the user's favorite list.
- **DELETE** `/api/v1/users/{userId}/favorites/{productId}`  
 **Description**: Remove a product from the user's favorite list.

#### Black List

- **POST** `/api/v1/users/{userId}/block-seller`
  **Description**: Block a seller for a user.
- **GET** `/api/v1/users/{userId}/blocked-sellers`
  **Description**: Retrieve the blocked sellers for a user.





