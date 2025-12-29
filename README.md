<!-- FILE: README.md -->
# Person Management System - REST Frontend

## Project Description
This is a frontend web application that consumes a RESTful backend API for managing persons. The application allows you to perform CRUD operations (Create, Read, Update, Delete) on person records through a modern, responsive web interface.

## Technologies Used
- **Frontend:** HTML5, CSS3, JavaScript (ES6+)
- **CSS Framework:** Bootstrap 5.1.3
- **Backend:** JEE/JAX-RS (Java REST API)
- **Database:** MySQL with JPA/Hibernate
- **Build Tool:** Maven
- **Application Server:** Apache Tomcat

## Features
- ✅ List all persons
- ✅ Add new person
- ✅ Edit existing person
- ✅ Delete person (with confirmation)
- ✅ Search person by name
- ✅ Responsive design
- ✅ Form validation
- ✅ Real-time feedback with alerts

## Project Structure
Exam_SOA/
├── src/
│ ├── com.revision.api/
│ │ └── RestRouter.java
│ ├── com.revision.entities/
│ │ ├── Person.java
│ ├── com.revision.service/
│ │ ├── PersonService.java
│ │ └── PersonServiceImpl.java
│ └── META-INF/
│ └── persistence.xml
├── WebContent/
│ ├── index.html
│ ├── css/
│ │ └── style.css
│ ├── js/
│ │ └── app.js
│ └── WEB-INF/
│ └── web.xml
├── pom.xml
└── README.md



## Setup Instructions

### Prerequisites
1. Java JDK 8 or higher
2. Apache Tomcat 9 or higher
3. MySQL Server
4. Maven 3.6 or higher

### Database Setup
1. Create a MySQL database:
   ```sql
   CREATE DATABASE revision_db;


## REST API Endpoints
- GET /api/persons - Get all persons

- GET /api/persons/{id} - Get person by ID

- GET /api/persons/search/{name} - Search persons by name

- POST /api/persons - Add new person

- PUT /api/persons/{id} - Update person

- DELETE /api/persons/{id} - Delete person

### Example utilisé:
- http://localhost:8080/Exam_SOA_V2/exercice/revision/api/persons/1

# Lien GitHub:
https://github.com/zShniter/Exam_SOA_V2

# Lien Video:
https://drive.google.com/file/d/1r5dHgbKcke0J6PqwmhUbovLPuDK_1Xi4/view?usp=drive_link
