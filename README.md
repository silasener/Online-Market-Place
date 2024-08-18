# Online Marketplace Application

This is a robust and feature-rich online marketplace built using the **Spring Boot** framework. The platform allows users to browse, purchase, and manage their favorite products while also providing powerful tools for administrators to manage products, users, and sellers effectively.

## Key Features and Functionality

### User Features:

- **Secure User Authentication and Authorization**  
  The platform uses **Spring Security** to implement role-based access control (Admin and User), ensuring users have secure access to their accounts. Unauthorized actions are blocked based on user roles.

- **Browse and Search Products**  
  Users can easily browse through products with advanced filtering options such as product categories, price ranges, and more to help them find exactly what they're looking for.

- **Favorites Management**  
  Users can save their favorite products in a personalized list for quick access and easy navigation to preferred items.

- **Block Unwanted Sellers**  
  To enhance user experience and maintain a safe shopping environment, users can block sellers they do not wish to interact with, effectively hiding those sellers’ products from the user's view.

- **View Available Products**  
  Users can view the products they are eligible to purchase, automatically filtering out items from blocked sellers.

### Administrative Features:

- **Comprehensive Product Management**  
  Administrators have full control over the product inventory, allowing them to add, update, or delete products as needed.

- **User Management**  
  Admins can manage user accounts, including the creation, updating, and deletion of user profiles. This helps maintain a secure and well-functioning user base.

- **Seller Management**  
  Admins can manage seller accounts, ensuring only verified and legitimate sellers are part of the marketplace. Seller profiles can be created, updated, and deleted efficiently.

- **Advanced Filtering for Products**  
  Administrators can filter product listings based on multiple criteria (e.g., product categories, price ranges, popularity) to better manage the inventory.

- **Bulk Management for Users and Sellers**  
  Admins can manage large groups of users or sellers by their IDs, streamlining administrative tasks such as bulk deletions or updates.

- **Generate Sample Users for Testing**  
  Administrators can generate sample user accounts for testing or demo purposes, making it easy to test the functionality in different scenarios.

## Technical Stack

This application is built with the following technologies:

- **Spring Boot**  
  Provides the backbone for building and running the application quickly with minimal configuration.

- **Spring Security**  
  Handles user authentication and authorization, ensuring secure access to various application functionalities.

- **Spring Data JPA**  
  Simplifies database interactions, making it easier to perform CRUD operations and query data with an object-oriented approach.

- **MySQL**  
  A reliable and scalable relational database for persisting user, product, and transaction data.

- **Lombok**  
  Reduces boilerplate code by auto-generating common Java methods like getters, setters, constructors, etc.

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

   Update the `application.properties` file with your MySQL credentials:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your-database
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. **Build and Run the Application**

   Build and run the application using Maven:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   The application will be available at `http://localhost:8080`.

### API Documentation

The application exposes a RESTful API for programmatic access to its features. A detailed API documentation, including endpoints, request and response formats, and authentication requirements, is available. You can also use tools like **Swagger** for exploring the API.

## Contribution Guidelines

We welcome contributions! Follow these steps to contribute:

1. Fork the repository.

2. Create a new branch for your feature or bug fix:

   ```bash
   git checkout -b feature/your-feature-name
   ```

3. Implement your changes and write comprehensive tests.

4. Push your branch to your forked repository:

   ```bash
   git push origin feature/your-feature-name
   ```

5. Submit a pull request with a detailed explanation of your changes.
