# Care and Cure Application

## Overview
**Care and Cure** is a comprehensive healthcare management system designed to simplify patient and doctor interactions. The application provides modules for patient registration, medical history tracking, profile management, and appointment scheduling, along with admin features for efficient system management.

This project is developed using **Java Full-Stack Technology**, leveraging robust backend and intuitive frontend frameworks for a seamless user experience.

## Features
- **Patient Portal**:
  - Registration and Login.
  - View and update patient profiles.
  - Access to medical history.
- **Admin Portal**:
  - Manage patient records.
  - Monitor system performance.
- **Doctor Portal**:
  - Appointment scheduling and tracking.
  - Patient profile and history view.

---

## Technology Stack
### Backend
- **Java 17**: Core programming language.
- **Spring Boot**: Backend framework for RESTful APIs and business logic.
- **Hibernate/JPA**: ORM for database interaction.
- **MySQL**: Database for storing application data.
- **Thymeleaf**: Server-side templating for rendering dynamic HTML pages.

### Frontend
- **HTML5/CSS3**: Structure and styling of the web application.
- **JavaScript**: Interactive and responsive components.

### Additional Tools
- **Maven**: Build and dependency management.
- **Git**: Version control.

---

## Installation

### Prerequisites
1. **Java JDK** (17 or above).
2. **Maven** (for build automation).
3. **MySQL** (Database setup).
4. **Mailtrap** (For email functionality).
5. An IDE like IntelliJ IDEA or Eclipse.

---

### Steps to Setup
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/CareAndCureApp.git
   ```
2. **Navigate to the Project Directory**:
   ```bash
   cd CareAndCureApp
   ```
3. **Configure Database**:
   - Update the database configurations in `application.properties`:
     ```properties
      spring.application.name=PatientModule
      server.port=8084
      
      spring.datasource.url=jdbc:mysql://localhost:3307/HospitalApp?createDatabaseIfNotExist=true
      spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=true
      
      #write mysql username and password
      spring.datasource.username=root
      spring.datasource.password=root
     ```
   - Create a MySQL database named `HospitalApp`.

4. **Install Dependencies**:
   Run the following command in the project directory:
   ```bash
   mvn clean install
   ```

5. **Run the Application**:
   Start the application using:
   ```bash
   mvn spring-boot:run
   ```

6. **Access the Application**:
   Open your browser and navigate to:
   - `http://localhost:8083/` for the homepage.

---

## Folder Structure
```plaintext
CareAndCureApp/
│
├── src/
│   ├── main/
│   │   ├── java/com/careandcure/
│   │   │   ├── controller/     # Controllers for handling requests
│   │   │   ├── model/          # Entity models
│   │   │   ├── service/        # Business logic services
│   │   │   ├── repository/     # Repositories for database operations
│   │   ├── resources/
│   │   │   ├── templates/      # Thymeleaf HTML templates
│   │   │   ├── static/         # CSS, JS, and image files
│   │   │   ├── application.properties
│   │   │   ├── data.sql        # Sample data for testing
│   ├── test/
│       ├── java/               # Test cases
│
├── pom.xml                     # Maven configuration
├── README.md                   # Documentation
```

---

## API Endpoints

### Patient Module
| Endpoint                | HTTP Method | Description                        |
|-------------------------|-------------|------------------------------------|
| `/patientRegistration`  | POST        | Register a new patient            |
| `/findPatientById`      | GET         | Search for a patient by ID        |
| `/updatePatient`        | PUT         | Update patient details            |

### Admin Module
| Endpoint             | HTTP Method | Description                     |
|----------------------|-------------|---------------------------------|
| `/adminLogin`        | POST        | Login as admin                 |
| `/viewAllPatients`   | GET         | View all patient records       |

---

## Future Enhancements
1. **Doctor Module**: Add functionality for doctors to manage appointments and prescriptions.
2. **Mobile Responsiveness**: Enhance UI for mobile users.
3. **Cloud Deployment**: Deploy to AWS or Google Cloud for wider accessibility.
4. **Advanced Security**: Implement OAuth2 and JWT authentication.

---

## Contributing
Contributions are welcome! Follow the [CONTRIBUTING.md](CONTRIBUTING.md) guide to get started.

---

## Acknowledgments
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Thymeleaf Documentation](https://www.thymeleaf.org/)
- [Bootstrap](https://getbootstrap.com/)
