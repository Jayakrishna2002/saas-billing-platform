**SaaS Billing Platform (WIP)**

A multi-tenant SaaS backend platform built using Spring Boot 3 and Java 21.

This project simulates a production-grade SaaS system with strict tenant isolation, clean architecture layering, and scalability considerations.

**🚀 Tech Stack**

* Java 21
* Spring Boot 3
* Spring Data JPA
* PostgreSQL
* Maven
* REST APIs
* Multi-Tenant (Shared Schema Model)

**🏗️ Architecture Overview**

Current implementation follows layered architecture:

` Controller → Service → Repository → Database `

Multi-Tenancy Strategy

Model: Shared Database + Shared Schema

* Each table includes a tenant_id
* All queries are tenant-scoped
* Composite unique constraint ensures isolation

Example:
` UNIQUE (tenant_id, email) `

This prevents duplicate users within the same tenant while allowing identical emails across different tenants.

**🔐 Tenant Isolation Strategy**

* Tenant ID is passed via HTTP header: 
` X-Tenant-ID: <UUID> `
* Service layer enforces tenant presence
* Repository queries are scoped using tenant_id
* Database constraint enforces data integrity

Current limitation:
Tenant header is trusted without authentication. Future iteration will introduce JWT-based tenant verification.

**📦 Domain Model**
Users Table

* id (UUID, PK)
* tenant_id (FK)
* email
* name
* status
* created_at

Composite uniqueness:
`(tenant_id, email)`

**⚠️ Concurrency Consideration**

Email uniqueness is enforced at the database level using a composite constraint.

Service-level duplicate checks are considered advisory only and do not guarantee atomic enforcement under concurrent requests.

**📈 Scalability Considerations (Planned)**

* Pageable support for large tenant datasets
* Proper indexing on (tenant_id, created_at)
* Keyset pagination for high-volume tenants
* Request-level tenant context
* Dockerization
* Kubernetes deployment
* Event-driven architecture (Kafka)

**🧠 Architectural Decisions**

1. DTO layer separates API contract from persistence model
2. Constructor injection preferred over field injection
3. Fail-fast on null tenantId
4. Database constraint guarantees uniqueness under concurrency
5. Lazy-loading avoided in DTO mapping
