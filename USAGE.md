# CCRM Usage Guide

## Quick Start

### 1. Setup and Run
```bash
# Clone or download the project
# Ensure Java 11+ is installed

# Compile the project
javac -cp src src/edu/ccrm/Main.java

# Run with assertions enabled
java -ea -cp src edu.ccrm.Main
```

### 2. First Time Setup
On first run, CCRM will automatically create the following directory structure:
```
CCRM/
├── data/          # For CSV import files
├── exports/       # For exported data
└── backup/        # For backup files
```

## Sample Data Files

### Creating Test Data

Create these files in the `data/` directory to test import functionality:

#### `data/students.csv`
```csv
id,regNo,fullName,email,dateOfBirth,enrollmentDate
STU001,2023001,John Doe,john.doe@university.edu,2000-05-15,2023-08-01
STU002,2023002,Jane Smith,jane.smith@university.edu,1999-12-20,2023-08-01
STU003,2023003,Bob Johnson,bob.johnson@university.edu,2001-03-10,2023-08-01
STU004,2023004,Alice Brown,alice.brown@university.edu,2000-09-25,2023-08-01
STU005,2023005,Charlie Wilson,charlie.wilson@university.edu,2001-01-18,2023-08-01
```

#### `data/courses.csv`
```csv
code,title,credits,instructor,semester,department
CS101,Introduction to Programming,4,Dr. Alan Smith,FALL,Computer Science
CS201,Data Structures,4,Dr. Alan Smith,SPRING,Computer Science
MATH201,Calculus II,3,Prof. Mary Johnson,FALL,Mathematics
MATH301,Linear Algebra,3,Prof. Mary Johnson,SPRING,Mathematics
ENG101,English Composition,3,Dr. Sarah Wilson,FALL,English
PHYS101,General Physics,4,Dr. Michael Brown,FALL,Physics
CHEM101,General Chemistry,4,Dr. Lisa Davis,SPRING,Chemistry
HIST101,World History,3,Prof. David Lee,FALL,History
```

## Menu Navigation

### Main Menu Options

1. **Student Management**
   - Add new students
   - List all students (sorted by name)
   - Search students by name, ID, or registration number
   - Update student information
   - Deactivate students
   - View student statistics

2. **Course Management**
   - Add new courses using Builder pattern
   - List all courses (sorted by code)
   - Search by department, instructor, or semester
   - View course statistics and distributions

3. **Enrollment Management**
   - Enroll students in courses (with credit limit validation)
   - Unenroll students from courses
   - View student enrollments
   - View course enrollments

4. **Grade Management**
   - Record grades and marks
   - Generate official transcripts
   - Calculate GPA
   - View grade distributions

5. **Reports**
   - Top students by GPA
   - Students by enrollment year
   - Courses grouped by department
   - Enrollment summaries

6. **Data Import/Export**
   - Import students and courses from CSV
   - Export all data to CSV format
   - Batch operations with error handling

7. **Backup Operations**
   - Create timestamped backups
   - List available backups with sizes
   - Show total backup directory size
   - Restore from previous backups

## Sample Usage Scenarios

### Scenario 1: Setting Up a New Semester

1. **Import Course Data**
   ```
   Main Menu → Data Import/Export → Import Courses from CSV
   Enter filename: courses.csv
   ```

2. **Import Student Data**
   ```
   Main Menu → Data Import/Export → Import Students from CSV
   Enter filename: students.csv
   ```

3. **Enroll Students in Courses**
   ```
   Main Menu → Enrollment Management → Enroll Student in Course
   Student ID: STU001
   Course Code: CS101
   ```

### Scenario 2: Recording Grades and Generating Reports

1. **Record Grades**
   ```
   Main Menu → Grade Management → Record Grade
   Student ID: STU001
   Course Code: CS101
   Marks: 85
   ```

2. **Generate Transcript**
   ```
   Main Menu → Grade Management → Generate Transcript
   Student ID: STU001
   ```

3. **View Top Students**
   ```
   Main Menu → Reports → Top Students by GPA
   Number of students: 5
   ```

### Scenario 3: Data Backup and Export

1. **Export Current Data**
   ```
   Main Menu → Data Import/Export → Export Students to CSV
   Filename: students_backup_2024.csv
   ```

2. **Create System Backup**
   ```
   Main Menu → Backup Operations → Create Backup
   ```

3. **Check Backup Size**
   ```
   Main Menu → Backup Operations → Show Backup Size
   ```

## Command Examples

### Running with Different JVM Options

```bash
# Enable assertions (recommended)
java -ea -cp src edu.ccrm.Main

# Enable system assertions
java -esa -cp src edu.ccrm.Main

# With specific memory settings
java -ea -Xms256m -Xmx512m -cp src edu.ccrm.Main

# Enable detailed garbage collection logging
java -ea -XX:+PrintGC -cp src edu.ccrm.Main
```

### IDE Configuration

#### Eclipse Run Configuration
```
Main class: edu.ccrm.Main
VM arguments: -ea
Working directory: ${workspace_loc:CCRM}
```

#### IntelliJ IDEA Run Configuration
```
Main class: edu.ccrm.Main
VM options: -ea
Working directory: $PROJECT_DIR$
```

## Input Validation and Error Handling

### Expected Input Formats

- **Dates**: YYYY-MM-DD (e.g., 2023-08-01)
- **Email**: standard email format (validated)
- **Course Codes**: 2-4 letters + 3-4 digits (e.g., CS101, MATH1001)
- **Credits**: 1-6 integer values
- **Marks**: 0-100 decimal values

### Error Scenarios and Handling

1. **Duplicate Enrollment**
   ```
   Error: Student STU001 is already enrolled in course CS101
   ```

2. **Credit Limit Exceeded**
   ```
   Error: Enrolling in CS201 would exceed maximum credits per semester (24)
   ```

3. **File Import Errors**
   ```
   Error in file 'students.csv' at line 3: Invalid date format
   ```

4. **Validation Errors**
   ```
   Error: Invalid course code format. Expected: 2-4 letters followed by 3-4 digits
   ```

## Advanced Features

### Stream API Demonstrations

The application showcases various Stream API operations:

```java
// Filter and sort students
students.stream()
    .filter(Student::isActive)
    .sorted(Comparator.comparing(Student::getFullName))
    .collect(Collectors.toList());

// Group courses by department
courses.stream()
    .collect(Collectors.groupingBy(Course::getDepartment));

// Calculate grade distribution
enrollments.stream()
    .filter(e -> e.getGrade() != null)
    .collect(Collectors.groupingBy(Enrollment::getGrade, Collectors.counting()));
```

### Lambda Expressions Examples

```java
// Custom sorting
students.sort((s1, s2) -> s1.getFullName().compareToIgnoreCase(s2.getFullName()));

// Filtering with predicates
Predicate<Student> activeStudents = Student::isActive;
List<Student> active = students.stream().filter(activeStudents).collect(toList());
```

### Design Patterns in Action

1. **Singleton Pattern**: AppConfig class
2. **Builder Pattern**: Course and Transcript creation
3. **Template Method**: Abstract Person class
4. **Strategy Pattern**: Various Comparator implementations

## Troubleshooting

### Common Issues

1. **"Assertion failed" errors**
   - Solution: Run with `-ea` flag to enable assertions
   - These are intentional validation checks

2. **File not found errors**
   - Ensure CSV files are in the `data/` directory
   - Check file permissions and spelling

3. **Memory issues with large datasets**
   - Increase heap size: `-Xmx1g`
   - The application uses streaming for file operations

4. **Date parsing errors**
   - Use ISO format: YYYY-MM-DD
   - Ensure dates are valid (no future enrollment dates)

### Debug Mode

Enable detailed logging by modifying the logging level or adding debug prints:

```bash
# Run with system property for debugging
java -ea -Ddebug=true -cp src edu.ccrm.Main
```

## Performance Considerations

### Large Dataset Handling

- **Students**: Tested with up to 10,000 student records
- **Courses**: Optimized for university-scale course catalogs
- **Enrollments**: Efficient handling of semester-wise enrollments

### Memory Usage

- Typical memory usage: 50-100 MB for moderate datasets
- File operations use NIO.2 for better performance
- Streaming operations prevent large collections in memory

## Data Formats and Standards

### CSV Format Requirements

- **Encoding**: UTF-8
- **Delimiter**: Comma (,)
- **Quotes**: Double quotes for fields containing commas
- **Headers**: Optional (automatically detected)
- **Line Endings**: Windows (CRLF) or Unix (LF)

### Date and Time Standards

- **Dates**: ISO 8601 format (YYYY-MM-DD)
- **Times**: Automatic timestamp generation for backups
- **Zones**: System default timezone

## Integration Points

### Future Enhancements

The architecture supports easy extension:

1. **Database Integration**: Replace service layer with JPA repositories
2. **Web Interface**: Add REST controllers maintaining service layer
3. **Authentication**: Extend Person hierarchy for user roles
4. **Notifications**: Event-driven architecture ready
5. **Reporting**: PDF/Excel export capabilities

### API-Ready Design

Services are designed with future API exposure in mind:

```java
// RESTful patterns already implemented
StudentService.findById(id)          // GET /students/{id}
StudentService.findAll()             // GET /students
StudentService.save(student)         // POST /students
StudentService.delete(id)            // DELETE /students/{id}
```

## Best Practices Demonstrated

### Code Quality
- Comprehensive exception handling
- Input validation at all entry points
- Immutable value objects where appropriate
- Thread-safe singleton implementation

### Java Features
- Modern Java syntax (text blocks, var, enhanced switch)
- Functional programming with Streams and lambdas
- NIO.2 for efficient file operations
- Proper resource management with try-with-resources

### Object-Oriented Design
- Single Responsibility Principle
- Open/Closed Principle (extensible design)
- Dependency Inversion (service interfaces)
- Composition over inheritance

---

*For complete technical documentation, see [README.md](README.md)*
