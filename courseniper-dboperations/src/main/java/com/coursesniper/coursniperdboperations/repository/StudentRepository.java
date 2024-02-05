package com.coursesniper.coursniperdboperations.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coursesniper.coursniperdboperations.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    
    Optional<Student> findByEmail(String email);

    
    List<Student> findByLastName(String lastName);

    
    List<Student> findByFirstName(String firstName);

   
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);

    
    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
