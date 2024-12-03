# Dynamic Form Builder

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Configuration](#configuration)
- [Endpoints](#endpoints)
- [Usage](#usage)
- [Contributing](#contributing)

## Introduction

- Dynamic Form Builder is a powerful and flexible web application designed to ***help users easily create, customize, and manage forms dynamically.*** This project allows you to build forms with custom schemas, submit form data, and track submissions, all through a simple and user-friendly interface.

- With this tool, you can create forms for a variety of use cases, including surveys, registrations, feedback collection, and more, without needing to write complex code. The application provides ***CRUD (Create, Read, Update, Delete)*** functionalities for form templates and submissions, making it highly adaptable to your business or personal needs.

## Features

- ***Dynamic Form Creation:*** Easily create forms with custom fields and configurations.
- ***Submission Management:*** Track and manage form submissions efficiently, ensuring reliable data storage and retrieval.
- ***Error Handling & Notifications:*** Receive automatic email notifications for errors or issues during form creation, updates, or submissions.
- ***Scalable & Customizable:*** The form schema is dynamic, allowing you to tailor the structure and functionality to meet specific requirements.
- ***API-First Design:*** Offers RESTful APIs to enable seamless integration with other systems and services.
- ***Dynamic Form Schema:*** Supports customizable form structures, allowing users to define fields, types, and validations dynamically using JSON.
- ***JSON Structure Validation:*** Ensures the provided form schema and submission data adhere to predefined standards for correctness.
- ***Field Validation:*** Validates input fields (e.g., required, data types, format) during form submission to ensure accurate and reliable data.
- ***Submission Data Validation:*** Ensures all required fields in the schema are correctly filled before accepting submissions.
- ***Error Notifications:*** Notifies developers of backend errors or validation failures via email.
- ***CRUD Operations:*** Supports full CRUD functionality for form templates and associated submissions.
- ***Test Coverage:*** Includes comprehensive unit tests to ensure application stability and correctness across scenarios.
- ***Schema Consistency:*** Detects and prevents duplicate or invalid field definitions in schemas to maintain integrity.
- ***Real-Time Feedback:*** Provides actionable error messages and feedback for developers during schema creation and API testing.

## Technologies

- ***Java 17:*** The application is built using Java 17 for modern, efficient, and secure backend development.
- ***Spring Boot 3.x:*** Framework for building stand-alone, production-grade Spring-based applications.
- ***Spring Data JPA:*** Provides integration with databases and simplifies the use of JPA for data persistence.
- ***Swagger:*** For automatic API documentation and easy exploration of API endpoints.
- ***Logback:*** A robust logging framework used for logging errors, warnings, and other application events.
- ***Javax Mail:*** For email functionality, such as sending notifications on errors or form submissions.
- ***WebJars:*** Used for managing frontend libraries and assets in a Spring Boot application.
- ***JSON Validator:*** Ensures the form schema follows the correct structure before form creation or submission.
- ***H2 Database (for Development):*** In-memory database for local testing and development.
- ***MySQL (for Production):*** Relational database for production deployment to store form templates and submissions.
- ***Maven:*** Dependency management and build tool used to manage project dependencies and lifecycle.
- ***JUnit 5:*** For writing unit tests to ensure the correctness of application logic.

## Prerequisites

- Java 17 or higher
- Maven 3.6. or higher
- MySQL database (for production)

## Setup

****1.Clone the repository:****

   ```bash
   git clone https://github.com/your-username/Dynamic_Form_Builder.git
cd Dynamic_Form_Builder
   ```
##### Build the project:

* __`mvn clean install`__
##### Run the application:

* __`mvn spring-boot:run`__
## Configuration
##### Application Properties
Configure your email and database settings in `src/main/resources/application.properties`.

### properties
```java
# Email configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-email-password

# Database configuration (H2 for development)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# auth
spring.security.user.name=admin
spring.security.user.password=admin

# Swagger 
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```
# MySQL Configuration
#### For production, configure MySQL settings:

#### properties

```java
spring.datasource.url=jdbc:mysql://localhost:3306/mail_archive_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

## Endpoints
##### Create Form Endpoint
* **URL:**`/api/forms`
* **Method:** POST

****Request Body:****
~~~json
{
    "formName": "User Registration Form",
    "formSchema": {
        "fields": [
            {
                "name": "firstName",
                "type": "text",
                "label": "First Name",
                "required": true
            },
            {
                "name": "lastName",
                "type": "text",
                "label": "Last Name",
                "required": true
            },
            {
                "name": "email",
                "type": "email",
                "label": "Email Address",
                "required": true
            }
        ]
    }
}
~~~

****Response Body:****

~~~json
{
    "id": "UVp25lt",
    "formName": "User Registration Form",
    "formSchema": "{\"fields\":[{\"name\":\"firstName\",\"type\":\"text\",\"label\":\"First Name\",\"required\":true},{\"name\":\"lastName\",\"type\":\"text\",\"label\":\"Last Name\",\"required\":true},{\"name\":\"email\",\"type\":\"email\",\"label\":\"Email Address\",\"required\":true}]}",
    "createdBy": "user",
    "createdAt": "2024-12-02T23:04:16.9436008",
    "updatedAt": null,
    "updatedBy": null,
    "formSchemaAsMap": {
        "fields": [
            {
                "name": "firstName",
                "type": "text",
                "label": "First Name",
                "required": true
            },
            {
                "name": "lastName",
                "type": "text",
                "label": "Last Name",
                "required": true
            },
            {
                "name": "email",
                "type": "email",
                "label": "Email Address",
                "required": true
            }
        ]
    }
}
~~~

##### Form Submission Endpoint
* **URL:** `/api/forms/{formTemplateId}/submissions`
* **Method:** `POST`

****Request Body:****

```json   
    {
    "submissionData": {
        "firstName":"John",
        "lastName": "Doe",
        "email": "john.doe@example.com"
    }
}
```
****Response Body:****
```json
{
    "id": "UVpD2h7",
    "formTemplate": {
        "id": "UVoD0FT",
        "formName": "User Registration Form",
        "formSchema": "{\"fields\":[{\"name\":\"firstName\",\"type\":\"text\",\"label\":\"First Name\",\"required\":true},{\"name\":\"lastName\",\"type\":\"text\",\"label\":\"Last Name\",\"required\":true},{\"name\":\"email\",\"type\":\"email\",\"label\":\"Email Address\",\"required\":true}]}",
        "createdBy": "user",
        "createdAt": "2024-12-02T19:46:19.507959",
        "updatedAt": null,
        "updatedBy": null,
        "formSchemaAsMap": {
            "fields": [
                {
                    "name": "firstName",
                    "type": "text",
                    "label": "First Name",
                    "required": true
                },
                {
                    "name": "lastName",
                    "type": "text",
                    "label": "Last Name",
                    "required": true
                },
                {
                    "name": "email",
                    "type": "email",
                    "label": "Email Address",
                    "required": true
                }
            ]
        }
    },
    "submissionData": {
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com"
    },
    "submittedAt": "2024-12-02T23:49:25.6253161",
    "updatedAt": null,
    "updatedBy": null,
    "submittedBy": "admin"
}
```
##### Get All Forms Endpoint
* **URL:** `/api/forms`
* **Method:** `GET`

****Response Body:****

```json
[
    {
        "id": "UVii9zq",
        "formName": "Feedback Form",
        "formSchema": "{\"fields\": [{\"name\": \"name\", \"type\": \"text\"}, {\"name\": \"feedback\", \"type\": \"textarea\"}]}",
        "createdBy": "admin",
        "createdAt": "2024-12-01T21:09:22.79958",
        "updatedAt": "2024-12-01T21:09:22.79958",
        "updatedBy": null,
        "formSchemaAsMap": {
            "fields": [
                {
                    "name": "name",
                    "type": "text"
                },
                {
                    "name": "feedback",
                    "type": "textarea"
                }
            ]
        }
    }
]
```

##### Get Particular Forms Endpoint
* **URL:** `/api/forms/{id}`
* **Method:** `GET`

****Response Body:****
```json
{
    "id": "UVjA8T7",
    "formName": "Test Form",
    "formSchema": "{\"fields\": [{\"name\": \"name\", \"type\": \"text\"}, {\"name\": \"feedback\", \"type\": \"textarea\"}]}",
    "createdBy": "admin",
    "createdAt": "2024-12-01T23:02:51.543179",
    "updatedAt": "2024-12-01T23:02:51.543179",
    "updatedBy": null,
    "formSchemaAsMap": {
        "fields": [
            {
                "name": "name",
                "type": "text"
            },
            {
                "name": "feedback",
                "type": "textarea"
            }
        ]
    }
}
```

##### Get Particular Submission Endpoint
* **URL:** `/{formId}/submissions`
* **Method:** `GET`
****Response Body:****
```json
[
    {
        "id": "UVnbhq5",
        "formTemplate": {
            "id": "UVjeDhg",
            "formName": "Contact Form",
            "formSchema": "{\"fields\":[{\"name\":\"email\",\"type\":\"text\",\"validation\":{\"required\":true,\"pattern\":\"^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$\"}},{\"name\":\"message\",\"type\":\"textarea\",\"validation\":{\"required\":true}}]}",
            "createdBy": "user",
            "createdAt": "2024-12-02T00:54:11.026471",
            "updatedAt": null,
            "updatedBy": null,
            "formSchemaAsMap": {
                "fields": [
                    {
                        "name": "email",
                        "type": "text",
                        "validation": {
                            "required": true,
                            "pattern": "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"
                        }
                    },
                    {
                        "name": "message",
                        "type": "textarea",
                        "validation": {
                            "required": true
                        }
                    }
                ]
            }
        },
        "submissionData": {
            "name": "John Doe",
            "feedback": "Great service!"
        },
        "submittedAt": "2024-12-02T17:20:00.867472",
        "updatedAt": null,
        "updatedBy": null,
        "submittedBy": "admin"
    }
]
```

##### Update Form Endpoint
* **URL:** `/api/forms/{id}`
* **Method:** `PUT`

****Request Body:****
```json 
{
    "formName": "Update Registration Form",
    "formSchema": {
        "fields": [
            {
                "name": "firstName",
                "type": "text",
                "label": "First Name",
                "required": true
            },
            {
                "name": "lastName",
                "type": "text",
                "label": "Last Name",
                "required": true
            },
            {
                "name": "email",
                "type": "email",
                "label": "Email Address",
                "required": true
            }
        ]
    }
}
```

****Response Body:****
```json 
{
    "status": "success",
    "message": "Form template updated successfully.",
    "data": {
        "id": "UVp25lt",
        "formName": "Update Registration Form",
        "formSchema": "{\"fields\":[{\"name\":\"firstName\",\"type\":\"text\",\"label\":\"First Name\",\"required\":true},{\"name\":\"lastName\",\"type\":\"text\",\"label\":\"Last Name\",\"required\":true},{\"name\":\"email\",\"type\":\"email\",\"label\":\"Email Address\",\"required\":true}]}",
        "createdBy": "user",
        "createdAt": "2024-12-02T23:04:16.943601",
        "updatedAt": "2024-12-02T23:54:12.4152396",
        "updatedBy": "admin",
        "formSchemaAsMap": {
            "fields": [
                {
                    "name": "firstName",
                    "type": "text",
                    "label": "First Name",
                    "required": true
                },
                {
                    "name": "lastName",
                    "type": "text",
                    "label": "Last Name",
                    "required": true
                },
                {
                    "name": "email",
                    "type": "email",
                    "label": "Email Address",
                    "required": true
                }
            ]
        }
    }
}
```
##### Update Submission Endpoint
* **URL:** `api/forms/{formId}/submissions/{submissionId}`
* **Method:** `PUT`

****Request Body:****
```json
{
    "submissionData": {
        "firstName":"updateJohn",
        "lastName": "Doe",
        "email": "john.doe@example.com"
    }
}
```

****Response Body:****
```json
{
    "status": "success",
    "message": "Form submission updated successfully.",
    "data": {
        "id": "UVoIUSl",
        "formTemplate": {
            "id": "UVoD0FT",
            "formName": "User Registration Form",
            "formSchema": "{\"fields\":[{\"name\":\"firstName\",\"type\":\"text\",\"label\":\"First Name\",\"required\":true},{\"name\":\"lastName\",\"type\":\"text\",\"label\":\"Last Name\",\"required\":true},{\"name\":\"email\",\"type\":\"email\",\"label\":\"Email Address\",\"required\":true}]}",
            "createdBy": "user",
            "createdAt": "2024-12-02T19:46:19.507959",
            "updatedAt": null,
            "updatedBy": null,
            "formSchemaAsMap": {
                "fields": [
                    {
                        "name": "firstName",
                        "type": "text",
                        "label": "First Name",
                        "required": true
                    },
                    {
                        "name": "lastName",
                        "type": "text",
                        "label": "Last Name",
                        "required": true
                    },
                    {
                        "name": "email",
                        "type": "email",
                        "label": "Email Address",
                        "required": true
                    }
                ]
            }
        },
        "submissionData": {
            "firstName": "updateJohn",
            "lastName": "Doe",
            "email": "john.doe@example.com"
        },
        "submittedAt": "2024-12-02T19:57:03.416008",
        "updatedAt": "2024-12-03T00:02:54.8792373",
        "updatedBy": "admin",
        "submittedBy": "admin"
    }
}
```

##### Delete Form Endpoint
* **URL:** `api/forms/{id}`
* **Method:** `DELETE`

****Response Body:****
```json
{
    "status": "success",
    "message": "Form template deleted successfully.",
    "data": null
}
```
##### Delete Submission Endpoint
* **URL:** `api/forms/{formId}/submissions/{submissionId}`
* **Method:** `DELETE`

****Response Body:****
```json
{
    "status": "success",
    "message": "Form submission deleted successfully.",
    "data": null
}
```
## Usage

##### 1. Dynamic Form Creation

- Use the `/api/forms` endpoint to create new forms by providing a JSON schema that defines the structure and validations for the form.
- The schema supports dynamic fields, data types, and validation rules.

##### 2. Submission Management

- Submit form data via the `/api/forms/{formId}/submissions` endpoint, ensuring all required fields and validations defined in the schema are satisfied.
- View all submissions for a specific form using the `/api/forms/{formId}/submissions` endpoint.

##### 3. Form Updates

- Update existing forms dynamically using the `/api/forms/{formId}` endpoint to modify the form schema or configuration.

##### 4. Error Notifications

The application automatically sends email notifications in the following scenarios:
- Form Creation Errors: If the form schema is invalid or missing required fields, an email is sent with the details of the error.
- Submission Validation Errors: When a submission fails due to missing or invalid data, the details are logged, and an email notification is sent.
- System Exceptions: For any unhandled exceptions, the system sends an email with the error details for quick resolution.

##### 5. Schema Validation

- Validates the provided JSON schema during form creation to ensure it meets the required standards.
- Prevents duplicate or conflicting field definitions within the schema.

##### 6. Manual Submission Updates

- Update existing form submissions using the `/api/forms/{formId}/submissions/{submissionId}` endpoint.

##### 7. Deletion Operations

 Remove forms or submissions using the respective DELETE endpoints:
- `/api/forms/{formId}` for forms.
- `/api/forms/{formId}/submissions/{submissionId}` for submissions.

### Swagger Integration
- Swagger UI is integrated to provide API documentation. Access Swagger UI at `http://localhost:8080/swagger-ui/index.html`.

### Test Cases
- Unit test cases are implemented to verify the functionality of service methods, repository operations, and controller endpoints.

### Logging Configuration (Logback)
- Logback is configured to provide live logging and daily log rotation. Logs are stored in `logs/Dynamic_Form_Builder.log` and rotated daily.
## Contributing:
- Fork the repository.
- **Create a feature branch:** `git checkout -b` feature-name
- **Commit your changes:** `git commit -m` 'Add feature'
- **Push to the branch:** `git push origin` feature-name
- Open a pull request.

### Notes:
- Replace `your-username` and `your-repo-name` with your actual GitHub username and repository name.
- Ensure all configurations, such as the `application.properties`, match your actual setup.
- Update the sections to better reflect any additional features or specific instructions for your project.