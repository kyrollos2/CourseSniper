package com.coursesniper.coursniperdboperations.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coursesniper.coursniperdboperations.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    
   
        Optional<Course> findById(Integer id);
        Optional<Course> findBySectionNameAndStartDate(String sectionName, Date startDate);
        List<Course> findByTitleContainingIgnoreCase(String title);
        List<Course> findByStartDate(Date startDate);
        List<Course> findByAvailableSeats(Integer availableSeats);
    
       
    }
