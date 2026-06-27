# SaaS Billing Platform

A production-oriented multi-tenant SaaS backend built using Spring Boot. This project focuses on scalable architecture, tenant isolation, security, and enterprise backend development practices.

---

# Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* OpenAPI / Swagger

## Planned

* Spring Security
* JWT Authentication
* Redis
* Docker
* AWS EC2
* Spring Cloud Gateway
* OpenFeign
* Apache Kafka

---

# Features

## Multi-Tenant Architecture

* Shared database, shared schema
* Tenant isolation using `tenant_id`
* Tenant-aware repository queries

---

## User Management

* Create User
* Update User
* Retrieve Users (Pagination)
* Soft Delete
* Email normalization
* DTO validation

---

## Tenant Management

* Create Tenant
* Retrieve Tenant
* List Tenants

---

## Tenant Membership

Supports many-to-many relationship between Users and Tenants.

Features:

* Automatic membership creation
* Role assignment
* Role updates
* Membership soft delete
* Last ADMIN validation

Roles:

* ADMIN
* MEMBER

Business Rules:

* First user in a tenant becomes ADMIN.
* Subsequent users become MEMBER.
* The last ADMIN cannot be removed or demoted.

---

# Exception Handling

Custom exception hierarchy:

* BaseApplicationException
* NotFoundException
* UserNotFoundException
* TenantNotFoundException
* TenantMembershipNotFound
* LastAdminDeletionException

Centralized exception handling using `@RestControllerAdvice`.

---

# Pagination

Implemented using Spring Data `Pageable`.

Example:

GET `/users?page=0&size=10`

---

# Soft Delete

Entities are logically deleted instead of being removed from the database.

Benefits:

* Auditability
* Data recovery
* Referential integrity

---

# Database Constraints

Implemented composite unique constraints to ensure data integrity.

Examples:

* `(tenant_id, email)`
* `(tenant_id, user_id)`

---

# Project Structure

```
controller/
service/
repository/
dto/
entity/
exception/
config/
```

---

# Current Architecture

```
Client
    │
    ▼
Controller
    │
    ▼
Service
    │
    ▼
Repository
    │
    ▼
Database
```

---

# Roadmap

## Completed

* Multi-tenancy
* User Module
* Tenant Module
* Tenant Membership Module
* Pagination
* Soft Delete
* Global Exception Handling
* DTO Validation

## In Progress

* Spring Security
* JWT Authentication

## Planned

* Redis Caching
* Docker
* AWS Deployment
* Spring Cloud Gateway
* OpenFeign
* Kafka Integration
* Microservices

---

# Learning Objectives

This project demonstrates:

* Multi-tenant SaaS architecture
* REST API design
* Database design
* JPA and Hibernate
* Exception handling
* Pagination
* Soft delete
* Role-based access design
* Enterprise backend development

---

# Future Improvements

* JWT-based authentication
* Redis caching
* Docker containerization
* AWS deployment
* Event-driven architecture using Kafka
* Microservice decomposition
