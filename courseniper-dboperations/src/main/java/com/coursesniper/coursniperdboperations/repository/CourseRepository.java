package com.coursesniper.coursniperdboperations.repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coursesniper.coursniperdboperations.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

   
    Optional<Course> findBySectionNameAndStartDate(String sectionName, Date startDate);

    
    List<Course> findByTitleContainingIgnoreCase(String title);

    
    List<Course> findByStartDate(Date startDate);

    
    List<Course> findByAvailableSeats(Integer availableSeats);

    List<Course> findByAvailableSeatsGreaterThan(Integer availableSeats);

   
    List<Course> findByTerm(String term);

   
    List<Course> findByFacultyContainingIgnoreCase(String facultyName);

    
    Optional<Course> findBySectionNameAndTerm(String sectionName, String term);
}
