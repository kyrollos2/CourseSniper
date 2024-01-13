package com.coursesniper.coursniperdboperations.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coursesniper.coursniperdboperations.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    
    List<Course> findBySectionName(String sectionName);

    List<Course> findByTitle(String title);

    List<Course> findByStartDate(Date startDate);

   
    List<Course> findByAvailableSeats(Integer availableSeats);
}
