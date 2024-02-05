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
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        return studentService.findStudentById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiRequestException("Student with ID: " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(savedStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        return studentService.updateStudent(id, student)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiRequestException("Failed to update student with ID: " + id));
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> resetStudentPassword(@PathVariable int id, @RequestBody String newPassword) {
        studentService.updateStudentPassword(id, newPassword)
                .orElseThrow(() -> new ApiRequestException("Failed to reset password for student with ID: " + id));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        boolean deleted = studentService.deleteStudent(id);
        if (!deleted) {
            throw new ApiRequestException("Failed to delete student with ID: " + id);
        }
        return ResponseEntity.ok().build();
    }
}
