package com.coursesniper.coursniperdboperations.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coursesniper.coursniperdboperations.entity.Student;
import com.coursesniper.coursniperdboperations.exception.ApiRequestException;
import com.coursesniper.coursniperdboperations.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    private static final String ERROR_FETCHING_STUDENTS = "Error fetching students";
    private static final String STUDENT_NOT_FOUND = "Student not found";
    private static final String ERROR_SAVING_STUDENT = "Error saving student";

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            List<Student> students = studentService.findAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            throw new ApiRequestException(ERROR_FETCHING_STUDENTS, e);
        }
    }

    @GetMapping("/student-id/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") int studentId) {
        return studentService.findStudentById(studentId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiRequestException(STUDENT_NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.saveStudent(student);
            return ResponseEntity.ok(savedStudent);
        } catch (Exception e) {
            throw new ApiRequestException(ERROR_SAVING_STUDENT, e);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") int studentId, @RequestBody Student student) {
        return studentService.updateStudent(studentId, student)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiRequestException(STUDENT_NOT_FOUND));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") int studentId) {
        if (!studentService.deleteStudent(studentId)) {
            throw new ApiRequestException(STUDENT_NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }
}
