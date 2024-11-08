*Note: The code provided is just an example and may not be suitable for production use.*

Generated using the description: JPA functionality with an integration test. Include all Java code in the same package.

To add the Spring Data JPA functionality with an integration test to an existing Maven project, follow these steps:

### Step 1: Add Dependencies
Add the following dependencies to your `pom.xml` file:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

### Step 2: Create Entity Class
Create a `Person` entity class in the package `org.springframework.samples.petclinic.ai.jpa`:

```java
package org.springframework.samples.petclinic.ai.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;

    public Person() {}

    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
```

### Step 3: Create Repository Interface
Create a `PersonRepository` interface in the same package:

```java
package org.springframework.samples.petclinic.ai.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
```

### Step 4: Create Service Class
Create a `PersonService` class in the same package:

```java
package org.springframework.samples.petclinic.ai.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    // Service methods
}
```

### Step 5: Create Integration Test
Create an integration test class in the same package to test the interaction between layers:

```java
package org.springframework.samples.petclinic.ai.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testIntegration() {
        // Integration test logic
    }
}
```

### Step 6: Application Properties
Add the following configuration to your `application.properties` file with the required database connection details:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

For more information on Spring Data JPA, you can refer to the following tutorials:
1. [Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
2. [Baeldung - Spring Data JPA Tutorial](https://www.baeldung.com/spring-data-jpa-tutorial)
3. [Spring Guides - Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

This setup will provide you with a Spring Java application using Spring Data JPA with an integration test.
