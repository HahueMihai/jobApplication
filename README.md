# Patient scheduled application
Open Api Swagger can be found here: http://localhost:8080/v3/api-docs/Medications

## Design Patterns used:

- **Model View Controller (MVC)**
- **Builder Pattern** through the `@Builder` annotation from Lombok
- **Data Transfer Object (DTO)**
    - by using the `MedicationDto` class
    - by using Lombok annotations to automatically generate `toString`, `equalsAndHashCode`, `getters`, and `setters`
- **Exception Handling** returning `SecurityException` when the entity ID is not found
- The `@Service`, `@Repository`, and `@RestController` annotations in Spring Boot configure these classes to be managed as singletons by the Spring container

## Field Limitations

- **Email**: Field is limited to a specific format and length to ensure valid email addresses.
- **Name**: Field is limited to a specific length.
- **Dosage**: Field is limited to a specific length.
- **Frequency**: Field is limited to a specific length.

## Generating Client API using Swagger Codegen
### Steps to Generate API Client Library
**Generate Client Library**

Use the following command to generate the client library in Java using Swagger Codegen CLI:

```shell
java --add-opens=java.base/java.util=ALL-UNNAMED -jar swagger-codegen-cli-3.0.20.jar generate \
  -i http://localhost:8080/v3/api-docs/Medications \
  --api-package com.application.patient.api \
  --model-package com.application.patient.model \
  --group-id com.application.patient \
  --artifact-id patient-api-client \
  --artifact-version 0.0.1-SNAPSHOT \
  -l java \
  -o target/java-client \
  -DhideGenerationTimestamp=true