package com.coursesniper.coursniperdboperations.service;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursesniper.coursniperdboperations.entity.Student;
import com.coursesniper.coursniperdboperations.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> findStudentById(int studentId) {
        return studentRepository.findById(studentId);
    }

    public Student saveStudent(Student student) {
        student.setPassword(BCrypt.hashpw(student.getPassword(), BCrypt.gensalt()));
        return studentRepository.save(student);
    }

    public Optional<Student> updateStudent(int studentId, Student studentDetails) {
        return studentRepository.findById(studentId)
                .map(existingStudent -> {
                    existingStudent.setFirstName(studentDetails.getFirstName());
                    existingStudent.setLastName(studentDetails.getLastName());
                    existingStudent.setEmail(studentDetails.getEmail());
                    // Keep the existing password unless it's explicitly being changed.
                    if (studentDetails.getPassword() != null && !studentDetails.getPassword().isEmpty()) {
                        existingStudent.setPassword(BCrypt.hashpw(studentDetails.getPassword(), BCrypt.gensalt()));
                    }
                    
                    existingStudent.setTargetCourses(studentDetails.getTargetCourses());
                    return studentRepository.save(existingStudent);
                });
    }

    public boolean deleteStudent(int studentId) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    studentRepository.delete(student);
                    return true;
                }).orElse(false);
    }

    // Method to update a student's password
    public Optional<Student> updateStudentPassword(int studentId, String newPassword) {
        return studentRepository.findById(studentId)
                .map(existingStudent -> {
                    String encodedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                    existingStudent.setPassword(encodedPassword);
                    return studentRepository.save(existingStudent);
                });
    }
}
