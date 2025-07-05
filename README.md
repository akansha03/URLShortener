# URL Shortener

A simple Spring Boot-based URL Shortener application that takes a long URL and returns a short version. This project is designed to help learn backend development, system design concepts, and hands-on Spring Boot coding.

---

## 🔗 Features

- Convert long URLs into shortened, shareable links
- Store mappings in a PostgreSQL database
- Auto-generate unique short codes using Base62 encoding
- Track metadata like click count, creation time, and expiry
- Redirect from short URL to the original long URL
- Clean separation of model, service, and controller layers

---

## 🚀 Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Lombok
- Maven

---

## 🏁 Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL

### Steps

1. **Clone the repo**

   ```bash
   git clone https://github.com/akansha03/URLShortener.git
   cd URLShortener
2.	Set up PostgreSQL

Create a database and configure credentials in application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

3.	Run the application
```bash
    mvn spring-boot:run
