# Purchase Order Management System - Backend

This is the backend of the Purchase Order Management System built using Spring Boot. It provides REST APIs for managing Purchase Orders (POs), user authentication with JWT, and handling the approval workflow.

## Features

- User authentication and JWT-based authorization
- REST API for managing Purchase Orders
- Multi-step Purchase Order approval workflow
- Role-based access control
- PDF generation for Purchase Orders

## Tech Stack

- Java 17+
- Spring Boot 3.x
- PostgreSQL (or any database)
- Liquibase for database migrations
- JWT for security

## Prerequisites

- Java 17+
- Maven 3+
- PostgreSQL or any supported database

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Samibangash/po-app-backend.git
   cd po-app-backend
   ```

   mvn clean install

   mvn spring-boot:run

   spring.datasource.url=jdbc:mysql://localhost:3306/po_database
   spring.datasource.username=your-username
   spring.datasource.password=your-password
