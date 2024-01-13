package main.java.com.coursesniper.coursesniperdboperations.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
       
        return studentRepository.save(student);
    }

    public Optional<Student> updateStudent(int studentId, Student studentDetails) {
        return studentRepository.findById(studentId)
            .map(existingStudent -> {
                
                existingStudent.setFirstName(studentDetails.getFirstName());
                existingStudent.setLastName(studentDetails.getLastName());
                existingStudent.setEmail(studentDetails.getEmail());
                existingStudent.setPassword(studentDetails.getPassword());
                existingStudent.setDesiredCourse(studentDetails.getDesiredCourse());
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
}
