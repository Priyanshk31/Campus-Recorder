// File: src/edu/ccrm/service/TranscriptService.java
package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for transcript generation and GPA calculation
 * Demonstrates Builder pattern and polymorphism
 */
public class TranscriptService {
    
    /**
     * Transcript class using Builder pattern
     */
    public static class Transcript {
        private final String studentId;
        private final String studentName;
        private final String regNo;
        private final List<TranscriptEntry> entries;
        private final double gpa;
        private final int totalCredits;
        private final String generationDate;
        
        private Transcript(Builder builder) {
            this.studentId = builder.studentId;
            this.studentName = builder.studentName;
            this.regNo = builder.regNo;
            this.entries = new ArrayList<>(builder.entries);
            this.gpa = builder.gpa;
            this.totalCredits = builder.totalCredits;
            this.generationDate = builder.generationDate;
        }
    
    public Transcript generateTranscript(Student student, List<Enrollment> enrollments, 
                                       CourseService courseService) {
        if (student == null || enrollments == null) {
            throw new IllegalArgumentException("Student and enrollments cannot be null");
        }
        
        List<TranscriptEntry> entries = enrollments.stream()
            .filter(e -> e.getStudentId().equals(student.getId()))
            .filter(e -> e.getGrade() != null) // Only completed courses
            .map(enrollment -> {
                Course course = courseService.findById(enrollment.getCourseCode());
                return course != null ? new TranscriptEntry(
                    course.getCode(),
                    course.getTitle(),
                    course.getCredits(),
                    enrollment.getGrade(),
                    course.getSemester()
                ) : null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        
        return new Transcript.Builder()
            .studentInfo(student.getId(), student.getFullName(), student.getRegNo())
            .addEntries(entries)
            .calculateGPA()
            .generationDate(new Date().toString())
            .build();
    }
    
    public double calculateGPA(List<Enrollment> enrollments, CourseService courseService) {
        if (enrollments == null || enrollments.isEmpty()) {
            return 0.0;
        }
        
        double totalPoints = 0.0;
        int totalCredits = 0;
        
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() != null) {
                Course course = courseService.findById(enrollment.getCourseCode());
                if (course != null) {
                    totalPoints += enrollment.getGrade().getGradePoint() * course.getCredits();
                    totalCredits += course.getCredits();
                }
            }
        }
        
        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }
    
    public Map<Grade, Long> getGradeDistribution(List<Enrollment> enrollments) {
        return enrollments.stream()
            .filter(e -> e.getGrade() != null)
            .collect(Collectors.groupingBy(Enrollment::getGrade, Collectors.counting()));
    }
    
    public List<Student> getTopStudents(List<Student> students, List<Enrollment> enrollments, 
                                       CourseService courseService, int limit) {
        return students.stream()
            .map(student -> {
                List<Enrollment> studentEnrollments = enrollments.stream()
                    .filter(e -> e.getStudentId().equals(student.getId()))
                    .collect(Collectors.toList());
                double gpa = calculateGPA(studentEnrollments, courseService);
                return new StudentGPA(student, gpa);
            })
            .sorted(Comparator.comparing(StudentGPA::getGpa).reversed())
            .limit(limit)
            .map(StudentGPA::getStudent)
            .collect(Collectors.toList());
    }
    
    // Inner class for student-GPA pairing
    private static class StudentGPA {
        private final Student student;
        private final double gpa;
        
        StudentGPA(Student student, double gpa) {
            this.student = student;
            this.gpa = gpa;
        }
        
        Student getStudent() { return student; }
        double getGpa() { return gpa; }
    }
}
        
        // Getters
        public String getStudentId() { return studentId; }
        public String getStudentName() { return studentName; }
        public String getRegNo() { return regNo; }
        public List<TranscriptEntry> getEntries() { return new ArrayList<>(entries); }
        public double getGpa() { return gpa; }
        public int getTotalCredits() { return totalCredits; }
        public String getGenerationDate() { return generationDate; }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== OFFICIAL TRANSCRIPT ===\n");
            sb.append(String.format("Student: %s (ID: %s, Reg: %s)\n", studentName, studentId, regNo));
            sb.append(String.format("Generated: %s\n", generationDate));
            sb.append("=" .repeat(50)).append("\n\n");
            
            sb.append(String.format("%-10s %-30s %-8s %-6s %-5s\n", 
                "Course", "Title", "Credits", "Grade", "Points"));
            sb.append("-".repeat(65)).append("\n");
            
            for (TranscriptEntry entry : entries) {
                sb.append(entry.toString()).append("\n");
            }
            
            sb.append("-".repeat(65)).append("\n");
            sb.append(String.format("Total Credits: %d\n", totalCredits));
            sb.append(String.format("GPA: %.2f\n", gpa));
            
            return sb.toString();
        }
        
        /**
         * Builder class for Transcript
         */
        public static class Builder {
            private String studentId;
            private String studentName;
            private String regNo;
            private List<TranscriptEntry> entries = new ArrayList<>();
            private double gpa;
            private int totalCredits;
            private String generationDate;
            
            public Builder studentInfo(String id, String name, String regNo) {
                this.studentId = id;
                this.studentName = name;
                this.regNo = regNo;
                return this;
            }
            
            public Builder addEntry(TranscriptEntry entry) {
                this.entries.add(entry);
                return this;
            }
            
            public Builder addEntries(List<TranscriptEntry> entries) {
                this.entries.addAll(entries);
                return this;
            }
            
            public Builder calculateGPA() {
                if (entries.isEmpty()) {
                    this.gpa = 0.0;
                    this.totalCredits = 0;
                    return this;
                }
                
                double totalPoints = 0.0;
                int credits = 0;
                
                for (TranscriptEntry entry : entries) {
                    if (entry.getGrade() != null) {
                        totalPoints += entry.getGradePoints() * entry.getCredits();
                        credits += entry.getCredits();
                    }
                }
                
                this.gpa = credits > 0 ? totalPoints / credits : 0.0;
                this.totalCredits = credits;
                return this;
            }
            
            public Builder generationDate(String date) {
                this.generationDate = date;
                return this;
            }
            
            public Transcript build() {
                if (generationDate == null) {
                    generationDate = new Date().toString();
                }
                return new Transcript(this);
            }
        }
    }
    
    /**
     * Transcript entry representing a course grade
     */
    public static class TranscriptEntry {
        private final String courseCode;
        private final String courseTitle;
        private final int credits;
        private final Grade grade;
        private final Semester semester;
        
        public TranscriptEntry(String courseCode, String courseTitle, int credits, 
                             Grade grade, Semester semester) {
            this.courseCode = courseCode;
            this.courseTitle = courseTitle;
            this.credits = credits;
            this.grade = grade;
            this.semester = semester;
        }
        
        public String getCourseCode() { return courseCode; }
        public String getCourseTitle() { return courseTitle; }
        public int getCredits() { return credits; }
        public Grade getGrade() { return grade; }
        public Semester getSemester() { return semester; }
        public double getGradePoints() { return grade != null ? grade.getGradePoint() : 0.0; }
        
        @Override
        public String toString() {
            return String.format("%-10s %-30s %-8d %-6s %5.1f", 
                courseCode, 
                courseTitle.length() > 30 ? courseTitle.substring(0, 27) + "..." : courseTitle,
                credits, 
                grade != null ? grade.name() : "N/A",
                getGradePoints());
        }
    }
