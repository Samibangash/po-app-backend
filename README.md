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
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
    "data": {
      "jwt": "string",
      "user": {
        "id": "integer",
        "username": "string",
        "password": "string (hashed)",
        "role": "integer",
        "roleName": "string or null"
      }
    },
    "success": true
  }
  ```

#### POST `/api/auth/register`

- **Description**: Registers a new user.
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
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
    "data": {
      "id": "integer",
      "username": "string",
      "password": "string (hashed)",
      "role": "integer",
      "roleName": "string or null"
    },
    "success": true
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
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
    "data": [
      {
        "id": "integer",
        "username": "string",
        "role": "string"
      }
    ],
    "success": true
  }
  ```

### Purchase Orders

#### POST `/api/po/create`

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
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
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
    },
    "success": true
  }
  ```

#### GET `/api/po`

- **Description**: Fetch all purchase orders or filter by status.
- **Request Parameters** (optional):
  - `status` (string): The status to filter purchase orders by (e.g., Pending, Approved, Rejected).
- **Response**:

  ```json
  {
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
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
    ],
    "success": true
  }
  ```

#### GET `/api/po/{id}`

- **Description**: Fetch a purchase order by its ID.
- **Request Parameters**:
  - `id` (long): The ID of the purchase order.
- **Response**:
  ```json
  {
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
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
    ],
    "success": true
  }
  ```

#### PUT `/api/po/{id}/status?status=Approve`

- **Description**: Approve a purchase order.
- **Request Parameters**:
  - `id` (long): The ID of the purchase order.
- **Response**:

  ```json
  {
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
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
    },
    "success": true
  }
  ```

#### PUT `/api/po/{id}/reject`

- **Description**: Reject a purchase order.
- **Request Parameters**:
  - `id` (long): The ID of the purchase order.
- **Response**:

  ```json
  {
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
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
    },
    "success": true
  }
  ```

### Approval Workflow

#### POST `/api/workflow/create`

- **Description**: Create a new approval workflow for a Purchase Order.
- **Request Parameters**:
  - `poId` (Long): The ID of the Purchase Order.
  - `userId` (Long): The ID of the user.
  - `approvalLevel` (Integer): The approval level for the workflow.
- **Response**:

  ```json
  {
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
    "data": {
      "id": "integer",
      "poId": "integer",
      "userId": "integer",
      "approvalLevel": "integer",
      "status": "string"
    },
    "success": true
  }
  ```

## Workflow Approval API

### **PUT** `/api/workflow/{workflowId}/approve`

- **Description**: Approves the current level of the approval workflow for a specified Purchase Order (PO).

- **Request Parameters**:

  - `workflowId` (Long): The ID of the workflow to be approved.

- **Response**:

  ```json
  {
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
    "data": {
      "id": "integer",
      "approvalLevel": "integer",
      "status": "Approved",
      "user": {
        "id": "integer",
        "username": "string",
        "role": "integer",
        "roleName": "string"
      },
      "purchaseOrder": {
        "id": "integer",
        "description": "string",
        "totalAmount": "decimal or null",
        "status": "string or null",
        "items": "array of items or null",
        "approvalWorkflows": "array of approval workflows or null",
        "poNumber": "string or null"
      }
    },
    "success": true
  }
  ```

  ### **POST** `/api/workflow/create`

- **Description**: Creates a new approval workflow for a specified Purchase Order (PO).
- **Request Parameters (Query Params)**:

  - `poId` (Long): The ID of the Purchase Order for which the approval workflow is being created.
  - `userId` (Long): The ID of the user responsible for the approval at the specified level.
  - `approvalLevel` (Integer): The level of approval (e.g., 1 for Team Lead, 2 for Department Manager, 3 for Finance Manager).

- **Response**:

  ```json
  {
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
    "data": {
      "id": "integer",
      "user": {
        "id": "integer",
        "username": "string",
        "role": "integer",
        "roleName": "string"
      },
      "purchaseOrder": {
        "id": "integer",
        "description": "string",
        "totalAmount": "decimal",
        "status": "string",
        "items": [
          {
            "id": "integer",
            "itemName": "string",
            "quantity": "integer",
            "price": "decimal",
            "purchaseOrder": "integer"
          }
        ],
        "approvalWorkflows": [
          {
            "id": "integer",
            "user": {
              "id": "integer",
              "username": "string",
              "role": "integer",
              "roleName": "string"
            },
            "purchaseOrder": "integer",
            "approvalLevel": "integer",
            "status": "string"
          }
        ],
        "poNumber": "string"
      },
      "approvalLevel": "integer",
      "status": "string"
    },
    "success": true
  }
  ```

  ## Get Workflow Details API

### **GET** `/api/workflow/{workflowId}`

- **Description**: Retrieves the details of a specific approval workflow for a Purchase Order (PO) based on the provided workflow ID.

- **Path Parameters**:

  - `workflowId` (Long): The ID of the workflow to retrieve.

- **Response**:
  ```json
  {
    "responseStatus": "Success",
    "responseCode": 200,
    "errorType": null,
    "stackTrace": null,
    "timestamp": "ISO 8601 formatted timestamp",
    "data": [
      {
        "id": "integer",
        "approvalLevel": "integer",
        "status": "string",
        "user": {
          "id": "integer",
          "username": "string",
          "role": "integer",
          "roleName": "string"
        },
        "purchaseOrder": {
          "id": "integer",
          "description": "string",
          "totalAmount": "decimal or null",
          "status": "string or null",
          "items": "array of items or null",
          "approvalWorkflows": "array of approval workflows or null",
          "poNumber": "string or null"
        }
      }
    ],
    "success": true
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
