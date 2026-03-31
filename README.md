# Campus-Course-Records-Manager
# Credit Course Registration Management (CCRM) System

A Java-based Credit Course Registration Management system demonstrating various design patterns and best practices.

## Features

- **Student Management**: Register and manage student information
- **Course Management**: Handle course creation and enrollment
- **Transcript Generation**: Generate official transcripts with GPA calculations
- **Design Patterns**: Implements Singleton, Builder, and other patterns

## Design Patterns Demonstrated

- **Singleton Pattern**: `AppConfig` class for application configuration
- **Builder Pattern**: `Transcript` class for complex object creation
- **Service Layer Pattern**: Separation of business logic in service classes

## Project Structure

```
src/
├── main/
│   └── java/
│       └── edu/ccrm/
│           ├── config/
│           │   └── AppConfig.java
│           ├── domain/
│           │   ├── Student.java
│           │   ├── Course.java
│           │   ├── Enrollment.java
│           │   ├── Grade.java
│           │   └── Semester.java
│           ├── service/
│           │   ├── TranscriptService.java
│           │   ├── StudentService.java
│           │   └── CourseService.java
│           └── Main.java
└── test/
    └── java/
        └── edu/ccrm/
            └── service/
                └── TranscriptServiceTest.java
```

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.6 or higher

### Building the Project

```bash
mvn clean compile
```

### Running the Application

```bash
mvn exec:java -Dexec.mainClass="edu.ccrm.Main"
```

### Running Tests

```bash
mvn test
```

## Configuration

The application uses the Singleton pattern for configuration management. Default settings include:

- Data folder: `data/`
- Backup folder: `backup/`
- Export folder: `exports/`
- Maximum credit limit: 24

## Usage Examples

### Generating a Transcript

```java
TranscriptService transcriptService = new TranscriptService();
CourseService courseService = new CourseService();
StudentService studentService = new StudentService();

Student student = studentService.findById("S123");
List<Enrollment> enrollments = studentService.getEnrollments("S123");

Transcript transcript = transcriptService.generateTranscript(student, enrollments, courseService);
System.out.println(transcript);
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
