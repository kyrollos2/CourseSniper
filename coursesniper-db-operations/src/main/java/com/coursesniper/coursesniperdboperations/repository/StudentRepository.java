package main.java.com.coursesniper.coursesniperdboperations.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coursesniper.coursesniperdboperations.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    
    @Override
    Optional<Student> findById(Integer id);

   
    Optional<Student> findByEmail(String email);

    
    List<Student> findByLastName(String lastName);

   
    List<Student> findByFirstName(String firstName);
}
