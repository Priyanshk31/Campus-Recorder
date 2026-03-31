// File: src/edu/ccrm/cli/MenuHandler.java
package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.io.*;
import edu.ccrm.exceptions.*;
import edu.ccrm.util.ComparatorUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Command Line Interface handler for the CCRM application
 * Demonstrates switch statements, loops, and user interaction
 */
public class MenuHandler {
    
    private final Scanner scanner;
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final TranscriptService transcriptService;
    private final ImportExportService ioService;
    private final BackupService backupService;
    
    public MenuHandler(StudentService studentService, CourseService courseService,
                      EnrollmentService enrollmentService, TranscriptService transcriptService,
                      ImportExportService ioService, BackupService backupService) {
        this.scanner = new Scanner(System.in);
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.transcriptService = transcriptService;
        this.ioService = ioService;
        this.backupService = backupService;
    }
    
    /**
     * Main menu loop with labeled break demonstration
     */
    public void startMenu() {
        System.out.println("Welcome to Campus Course & Records Manager!");
        
        menuLoop: while (true) {
            try {
                displayMainMenu();
                int choice = getIntInput("Enter your choice: ");
                
                switch (choice) {
                    case 1 -> handleStudentManagement();
                    case 2 -> handleCourseManagement();
                    case 3 -> handleEnrollmentManagement();
                    case 4 -> handleGradeManagement();
                    case 5 -> handleReports();
                    case 6 -> handleDataOperations();
                    case 7 -> handleBackupOperations();
                    case 8 -> {
                        System.out.println("Thank you for using CCRM!");
                        break menuLoop; // Labeled break demonstration
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
                
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         CCRM - Main Menu");
        System.out.println("=".repeat(50));
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Grade Management");
        System.out.println("5. Reports");
        System.out.println("6. Data Import/Export");
        System.out.println("7. Backup Operations");
        System.out.println("8. Exit");
        System.out.println("=".repeat(50));
    }
    
    /**
     * Student management submenu
     */
    private void handleStudentManagement() {
        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. List All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Deactivate Student");
            System.out.println("6. Student Statistics");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> listStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deactivateStudent();
                case 6 -> showStudentStatistics();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    /**
     * Course management submenu
     */
    private void handleCourseManagement() {
        while (true) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add Course");
            System.out.println("2. List All Courses");
            System.out.println("3. Search by Department");
            System.out.println("4. Search by Instructor");
            System.out.println("5. Search by Semester");
            System.out.println("6. Course Statistics");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1 -> addCourse();
                case 2 -> listCourses();
                case 3 -> searchCoursesByDepartment();
                case 4 -> searchCoursesByInstructor();
                case 5 -> searchCoursesBySemester();
                case 6 -> showCourseStatistics();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    /**
     * Enrollment management with exception handling
     */
    private void handleEnrollmentManagement() {
        while (true) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Unenroll Student from Course");
            System.out.println("3. View Student Enrollments");
            System.out.println("4. View Course Enrollments");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1 -> enrollStudent();
                case 2 -> unenrollStudent();
                case 3 -> viewStudentEnrollments();
                case 4 -> viewCourseEnrollments();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    /**
     * Grade management operations
     */
    private void handleGradeManagement() {
        while (true) {
            System.out.println("\n--- Grade Management ---");
            System.out.println("1. Record Grade");
            System.out.println("2. Generate Transcript");
            System.out.println("3. Calculate GPA");
            System.out.println("4. Grade Distribution");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1 -> recordGrade();
                case 2 -> generateTranscript();
                case 3 -> calculateGPA();
                case 4 -> showGradeDistribution();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    /**
     * Reports with Stream API demonstrations
     */
    private void handleReports() {
        while (true) {
            System.out.println("\n--- Reports ---");
            System.out.println("1. Top Students by GPA");
            System.out.println("2. Students by Enrollment Year");
            System.out.println("3. Courses by Department");
            System.out.println("4. Enrollment Summary");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1 -> showTopStudents();
                case 2 -> showStudentsByYear();
                case 3 -> showCoursesByDepartment();
                case 4 -> showEnrollmentSummary();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    // Student Management Methods
    private void addStudent() {
        try {
            System.out.println("\n--- Add New Student ---");
            String id = getStringInput("Student ID: ");
            String regNo = getStringInput("Registration Number: ");
            String fullName = getStringInput("Full Name: ");
            String email = getStringInput("Email: ");
            LocalDate dateOfBirth = getDateInput("Date of Birth (YYYY-MM-DD): ");
            
            Student student = studentService.createStudent(id, regNo, fullName, email, dateOfBirth);
            System.out.println("Student added successfully: " + student.getDetails());
            
        } catch (DuplicateEntityException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }
    
    private void listStudents() {
        List<Student> students = studentService.findAll();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        // Demonstrate sorting with lambda expressions
        students.sort(ComparatorUtils.BY_NAME);
        
        System.out.println("\n--- All Students ---");
        System.out.printf("%-10s %-15s %-25s %-30s %-12s%n", 
            "ID", "Reg No", "Name", "Email", "Status");
        System.out.println("-".repeat(90));
        
        // Enhanced for loop demonstration
        for (Student student : students) {
            System.out.printf("%-10s %-15s %-25s %-30s %-12s%n",
                student.getId(),
                student.getRegNo(),
                student.getFullName(),
                student.getEmail(),
                student.getStatus());
        }
    }
    
    private void searchStudent() {
        String searchTerm = getStringInput("Enter student name or ID to search: ");
        
        // Using lambda expressions and streams
        List<Student> results = studentService.search(student -> 
            student.getFullName().toLowerCase().contains(searchTerm.toLowerCase()) ||
            student.getId().equalsIgnoreCase(searchTerm) ||
            student.getRegNo().equalsIgnoreCase(searchTerm));
        
        if (results.isEmpty()) {
            System.out.println("No students found matching: " + searchTerm);
        } else {
            System.out.println("\nSearch Results:");
            results.forEach(student -> System.out.println("- " + student.getDetails()));
        }
    }
    
    // Course Management Methods
    private void addCourse() {
        try {
            System.out.println("\n--- Add New Course ---");
            String code = getStringInput("Course Code (e.g., CS101): ");
            String title = getStringInput("Course Title: ");
            int credits = getIntInput("Credits (1-6): ");
            String instructor = getStringInput("Instructor: ");
            String department = getStringInput("Department: ");
            
            System.out.println("Select Semester:");
            Semester[] semesters = Semester.values();
            for (int i = 0; i < semesters.length; i++) {
                System.out.printf("%d. %s%n", i + 1, semesters[i].getDisplayName());
            }
            
            int semesterChoice = getIntInput("Semester choice: ");
            if (semesterChoice < 1 || semesterChoice > semesters.length) {
                throw new IllegalArgumentException("Invalid semester choice");
            }
            
            Course course = new Course.Builder(code, title, credits)
                .instructor(instructor)
                .department(department)
                .semester(semesters[semesterChoice - 1])
                .build();
            
            courseService.save(course);
            System.out.println("Course added successfully: " + course);
            
        } catch (DuplicateEntityException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error adding course: " + e.getMessage());
        }
    }
    
    private void listCourses() {
        List<Course> courses = courseService.getSortedCoursesByCode();
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        System.out.println("\n--- All Courses ---");
        System.out.printf("%-8s %-30s %-8s %-20s %-10s%n", 
            "Code", "Title", "Credits", "Instructor", "Semester");
        System.out.println("-".repeat(80));
        
        courses.forEach(course -> {
            System.out.printf("%-8s %-30s %-8d %-20s %-10s%n",
                course.getCode(),
                course.getTitle().length() > 30 ? course.getTitle().substring(0, 27) + "..." : course.getTitle(),
                course.getCredits(),
                course.getInstructor().length() > 20 ? course.getInstructor().substring(0, 17) + "..." : course.getInstructor(),
                course.getSemester());
        });
    }
    
    // Enrollment Methods with Exception Handling
    private void enrollStudent() {
        try {
            String studentId = getStringInput("Student ID: ");
            String courseCode = getStringInput("Course Code: ");
            
            enrollmentService.enrollStudent(studentId, courseCode);
            System.out.println("Student enrolled successfully!");
            
        } catch (MaxCreditLimitExceededException e) {
            System.err.println("Enrollment failed: " + e.getMessage());
        } catch (DuplicateEnrollmentException e) {
            System.err.println("Enrollment failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // Data Operations
    private void handleDataOperations() {
        while (true) {
            System.out.println("\n--- Data Import/Export ---");
            System.out.println("1. Import Students from CSV");
            System.out.println("2. Import Courses from CSV");
            System.out.println("3. Export Students to CSV");
            System.out.println("4. Export Courses to CSV");
            System.out.println("5. Export Enrollments to CSV");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1 -> importStudents();
                case 2 -> importCourses();
                case 3 -> exportStudents();
                case 4 -> exportCourses();
                case 5 -> exportEnrollments();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    private void importStudents() {
        try {
            String fileName = getStringInput("Enter CSV filename (e.g., students.csv): ");
            List<Student> students = ioService.importStudents(fileName);
            
            int successCount = 0;
            for (Student student : students) {
                try {
                    studentService.save(student);
                    successCount++;
                } catch (DuplicateEntityException e) {
                    System.out.println("Skipped duplicate: " + student.getId());
                }
            }
            
            System.out.printf("Import completed: %d students imported successfully.%n", successCount);
            
        } catch (DataImportException e) {
            System.err.println("Import failed: " + e.getMessage());
        }
    }
    
    // Utility Methods
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Please enter date in YYYY-MM-DD format.");
            }
        }
    }
    
    // Placeholder methods for remaining functionality
    private void updateStudent() {
        System.out.println("Update student functionality - to be implemented");
    }
    
    private void deactivateStudent() {
        System.out.println("Deactivate student functionality - to be implemented");
    }
    
    private void showStudentStatistics() {
        StudentService.StudentStats stats = studentService.getStudentStatistics();
        System.out.println("Student Statistics:");
        System.out.println(stats);
    }
    
    private void searchCoursesByDepartment() {
        String dept = getStringInput("Enter department: ");
        List<Course> courses = courseService.findByDepartment(dept);
        courses.forEach(System.out::println);
    }
    
    private void searchCoursesByInstructor() {
        String instructor = getStringInput("Enter instructor name: ");
        List<Course> courses = courseService.findByInstructor(instructor);
        courses.forEach(System.out::println);
    }
    
    private void searchCoursesBySemester() {
        System.out.println("Select Semester:");
        Semester[] semesters = Semester.values();
        for (int i = 0; i < semesters.length; i++) {
            System.out.printf("%d. %s%n", i + 1, semesters[i]);
        }
        
        int choice = getIntInput("Semester choice: ");
        if (choice >= 1 && choice <= semesters.length) {
            List<Course> courses = courseService.findBySemester(semesters[choice - 1]);
            courses.forEach(System.out::println);
        }
    }
    
    private void showCourseStatistics() {
        var groupedByDept = courseService.groupByDepartment();
        var countBySemester = courseService.getCourseCountBySemester();
        
        System.out.println("Courses by Department:");
        groupedByDept.forEach((dept, courses) -> 
            System.out.printf("%s: %d courses%n", dept, courses.size()));
        
        System.out.println("\nCourses by Semester:");
        countBySemester.forEach((semester, count) ->
            System.out.printf("%s: %d courses%n", semester, count));
    }
    
    private void unenrollStudent() {
        String studentId = getStringInput("Student ID: ");
        String courseCode = getStringInput("Course Code: ");
        enrollmentService.unenrollStudent(studentId, courseCode);
        System.out.println("Student unenrolled successfully!");
    }
    
    private void viewStudentEnrollments() {
        String studentId = getStringInput("Student ID: ");
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        enrollments.forEach(System.out::println);
    }
    
    private void viewCourseEnrollments() {
        String courseCode = getStringInput("Course Code: ");
        List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseCode);
        enrollments.forEach(System.out::println);
    }
    
    private void recordGrade() {
        String studentId = getStringInput("Student ID: ");
        String courseCode = getStringInput("Course Code: ");
        double marks = Double.parseDouble(getStringInput("Marks (0-100): "));
        
        enrollmentService.recordGrade(studentId, courseCode, marks);
        System.out.println("Grade recorded successfully!");
    }
    
    private void generateTranscript() {
        String studentId = getStringInput("Student ID: ");
        Student student = studentService.findById(studentId);
        
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        TranscriptService.Transcript transcript = transcriptService.generateTranscript(
            student, enrollments, courseService);
        
        System.out.println(transcript);
    }
    
    private void calculateGPA() {
        String studentId = getStringInput("Student ID: ");
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        double gpa = transcriptService.calculateGPA(enrollments, courseService);
        System.out.printf("Student GPA: %.2f%n", gpa);
    }
    
    private void showGradeDistribution() {
        List<Enrollment> allEnrollments = enrollmentService.getAllEnrollments();
        var distribution = transcriptService.getGradeDistribution(allEnrollments);
        
        System.out.println("Grade Distribution:");
        distribution.forEach((grade, count) ->
            System.out.printf("%s: %d students%n", grade, count));
    }
    
    private void showTopStudents() {
        List<Student> students = studentService.findAll();
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        int limit = getIntInput("Number of top students to show: ");
        
        List<Student> topStudents = transcriptService.getTopStudents(
            students, enrollments, courseService, limit);
        
        System.out.println("Top Students:");
        topStudents.forEach(student -> {
            List<Enrollment> studentEnrollments = enrollmentService.getStudentEnrollments(student.getId());
            double gpa = transcriptService.calculateGPA(studentEnrollments, courseService);
            System.out.printf("%s - GPA: %.2f%n", student.getDetails(), gpa);
        });
    }
    
    private void showStudentsByYear() {
        int year = getIntInput("Enter enrollment year: ");
        List<Student> students = studentService.findStudentsByEnrollmentYear(year);
        students.forEach(student -> System.out.println(student.getDetails()));
    }
    
    private void showCoursesByDepartment() {
        var groupedCourses = courseService.groupByDepartment();
        groupedCourses.forEach((dept, courses) -> {
            System.out.printf("\n%s Department (%d courses):%n", dept, courses.size());
            courses.forEach(course -> System.out.println("  - " + course));
        });
    }
    
    private void showEnrollmentSummary() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        System.out.printf("Total enrollments: %d%n", enrollments.size());
        
        long completed = enrollments.stream().filter(Enrollment::isCompleted).count();
        System.out.printf("Completed courses: %d%n", completed);
        System.out.printf("In progress: %d%n", enrollments.size() - completed);
    }
    
    private void importCourses() {
        try {
            String fileName = getStringInput("Enter CSV filename (e.g., courses.csv): ");
            List<Course> courses = ioService.importCourses(fileName);
            
            int successCount = 0;
            for (Course course : courses) {
                try {
                    courseService.save(course);
                    successCount++;
                } catch (DuplicateEntityException e) {
                    System.out.println("Skipped duplicate: " + course.getCode());
                }
            }
            
            System.out.printf("Import completed: %d courses imported successfully.%n", successCount);
            
        } catch (DataImportException e) {
            System.err.println("Import failed: " + e.getMessage());
        }
    }
    
    private void exportStudents() {
        try {
            String fileName = getStringInput("Enter output filename (e.g., students_export.csv): ");
            List<Student> students = studentService.findAll();
            ioService.exportStudents(students, fileName);
        } catch (IOException e) {
            System.err.println("Export failed: " + e.getMessage());
        }
    }
    
    private void exportCourses() {
        try {
            String fileName = getStringInput("Enter output filename (e.g., courses_export.csv): ");
            List<Course> courses = courseService.findAll();
            ioService.exportCourses(courses, fileName);
        } catch (IOException e) {
            System.err.println("Export failed: " + e.getMessage());
        }
    }
    
    private void exportEnrollments() {
        try {
            String fileName = getStringInput("Enter output filename (e.g., enrollments_export.csv): ");
            List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
            ioService.exportEnrollments(enrollments, fileName);
        } catch (IOException e) {
            System.err.println("Export failed: " + e.getMessage());
        }
    }
    
    private void handleBackupOperations() {
        while (true) {
            System.out.println("\n--- Backup Operations ---");
            System.out.println("1. Create Backup");
            System.out.println("2. List Backups");
            System.out.println("3. Show Backup Size");
            System.out.println("4. Restore Backup");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1 -> createBackup();
                case 2 -> listBackups();
                case 3 -> showBackupSize();
                case 4 -> restoreBackup();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    private void createBackup() {
        try {
            String timestamp = backupService.createBackup();
            System.out.println("Backup created with timestamp: " + timestamp);
        } catch (IOException e) {
            System.err.println("Backup failed: " + e.getMessage());
        }
    }
    
    private void listBackups() {
        try {
            backupService.listBackups();
        } catch (IOException e) {
            System.err.println("Error listing backups: " + e.getMessage());
        }
    }
    
    private void showBackupSize() {
        try {
            backupService.showBackupSize();
        } catch (IOException e) {
            System.err.println("Error calculating backup size: " + e.getMessage());
        }
    }
    
    private void restoreBackup() {
        try {
            String timestamp = getStringInput("Enter backup timestamp to restore: ");
            backupService.restoreBackup(timestamp);
        } catch (IOException e) {
            System.err.println("Restore failed: " + e.getMessage());
        }
    }
}
