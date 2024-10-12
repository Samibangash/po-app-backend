# Purchase Order Management System - Backend

This is the backend of the Purchase Order Management System built using Spring Boot. It provides REST APIs for managing Purchase Orders (POs), user authentication with JWT, and handling the approval workflow.

## Features

- User authentication and JWT-based authorization
- REST API for managing Purchase Orders
- Multi-step Purchase Order approval workflow
- Role-based access control
- PDF generation for Purchase Orders

## Tech Stack

- Java 23+
- Spring Boot 3.x
- MySql (or any database)
- Liquibase for database migrations
- JWT for security

## Prerequisites

- Java 23+
- Maven 3+
- MySql or any supported database

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Samibangash/po-app-backend.git
   cd po-app-backend
   ```

2. Set up the database

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/po_database
spring.datasource.username=your-username
spring.datasource.password=your-password
```

3. Build the application

```bash
mvn clean install
```

4. Run the application

```bash
mvn spring-boot:run
```

## API Endpoints

## API Documentation

### Authentication

#### POST `/api/auth/login`

- **Description**: Logs in a user and returns a JWT token.
- **Request Body**:
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Response**:
  ```json
  {
    "status": "Success",
    "data": {
      "jwt": "string",
      "user": {
        "id": "integer",
        "username": "string",
        "role": "string"
      }
    }
  }
  ```

#### POST `/api/auth/register`

- **Description**: Registers a new user.
- **Request Body**:
  ```json
  {
    "username": "string",
    "password": "string",
    "role": "string"
  }
  ```
- **Response**:
  ```json
  {
    "status": "Success",
    "data": {
      "id": "integer",
      "username": "string",
      "role": "string"
    }
  }
  ```

### Users

#### GET `/api/auth/users?role={role}`

- **Description**: Retrieves all users by role.
- **Request Parameters**:
  - `role` (string): The role of the users to fetch.
- **Response**:
  ```json
  {
    "status": "Success",
    "data": [
      {
        "id": "integer",
        "username": "string",
        "role": "string"
      }
    ]
  }
  ```

### Purchase Orders

#### POST `/api/purchase-orders`

- **Description**: Create a new purchase order.
- **Request Body**:
  ```json
  {
    "description": "string",
    "totalAmount": "number",
    "items": [
      {
        "name": "string",
        "quantity": "number",
        "price": "number"
      }
    ]
  }
  ```
- **Response**:
  ```json
  {
    "status": "Success",
    "data": {
      "id": "integer",
      "description": "string",
      "totalAmount": "number",
      "status": "string",
      "items": [
        {
          "name": "string",
          "quantity": "number",
          "price": "number"
        }
      ]
    }
  }
  ```

#### GET `/api/purchase-orders`

- **Description**: Fetch all purchase orders or filter by status.
- **Request Parameters** (optional):
  - `status` (string): The status to filter purchase orders by (e.g., Pending, Approved, Rejected).
- **Response**:
  ```json
  {
    "status": "Success",
    "data": [
      {
        "id": "integer",
        "description": "string",
        "totalAmount": "number",
        "status": "string",
        "items": [
          {
            "name": "string",
            "quantity": "number",
            "price": "number"
          }
        ]
      }
    ]
  }
  ```

#### GET `/api/purchase-orders/{id}`

- **Description**: Fetch a purchase order by its ID.
- **Request Parameters**:
  - `id` (long): The ID of the purchase order.
- **Response**:
  ```json
  {
    "status": "Success",
    "data": {
      "id": "integer",
      "description": "string",
      "totalAmount": "number",
      "status": "string",
      "items": [
        {
          "name": "string",
          "quantity": "number",
          "price": "number"
        }
      ]
    }
  }
  ```

#### PUT `/api/purchase-orders/{id}/approve`

- **Description**: Approve a purchase order.
- **Request Parameters**:
  - `id` (long): The ID of the purchase order.
- **Response**:
  ```json
  {
    "status": "Success",
    "data": {
      "id": "integer",
      "description": "string",
      "totalAmount": "number",
      "status": "Approved",
      "items": [
        {
          "name": "string",
          "quantity": "number",
          "price": "number"
        }
      ]
    }
  }
  ```

#### PUT `/api/purchase-orders/{id}/reject`

- **Description**: Reject a purchase order.
- **Request Parameters**:
  - `id` (long): The ID of the purchase order.
- **Response**:
  ```json
  {
    "status": "Success",
    "data": {
      "id": "integer",
      "description": "string",
      "totalAmount": "number",
      "status": "Rejected",
      "items": [
        {
          "name": "string",
          "quantity": "number",
          "price": "number"
        }
      ]
    }
  }
  ```

#### GET `/api/po/generate-pdf/{id}`

- **Description**: Generate a PDF for a purchase order by its ID.
- **Request Parameters**:
  - `id` (long): The ID of the purchase order.
- **Response**: The PDF file as a downloadable attachment.
  - Content-Type: `application/pdf`
  - Content-Disposition: `attachment; filename="PO-{id}.pdf"`

---

### Project Setup

- To run the backend, use the following command:
  ```bash
  ./mvnw spring-boot:run
  ```
- To run the frontend:
  ```bash
  npm install
  ng serve
  ```

---

### Technologies Used

- **Backend**: Spring Boot, Spring Security, JWT
- **Frontend**: Angular
- **Database**: MySQL
- **PDF Generation**: iTextPDF
- **Authentication**: JWT (JSON Web Token)
