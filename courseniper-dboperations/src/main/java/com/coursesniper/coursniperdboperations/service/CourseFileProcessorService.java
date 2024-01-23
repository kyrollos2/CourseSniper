package com.coursesniper.coursniperdboperations.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursesniper.coursniperdboperations.entity.Course;
import com.coursesniper.coursniperdboperations.exception.ApiRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CourseFileProcessorService {

    private final CourseService courseService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CourseFileProcessorService(CourseService courseService, ObjectMapper objectMapper) {
        this.courseService = courseService;
        this.objectMapper = objectMapper;
    }

    public void processJsonData(String jsonDataFilePath) {
        try {
            List<Course> courses = convertJsonToCourses(jsonDataFilePath);
            courseService.saveAllCourses(courses); // Save all courses at once
        } catch (JsonProcessingException e) {
            throw new ApiRequestException("Error parsing JSON data", e);
        } catch (IOException e) {
            throw new ApiRequestException("Error reading JSON data", e);
        }
    }

    private List<Course> convertJsonToCourses(String jsonDataFilePath) throws IOException {
        File jsonDataFile = new File(jsonDataFilePath);
        Map<String, Map<String, Object>> coursesMap = objectMapper.readValue(jsonDataFile,
                new TypeReference<Map<String, Map<String, Object>>>() {});

        // Convert coursesMap into a list of Course entities
        List<Course> courses = new ArrayList<>();

        for (Map.Entry<String, Map<String, Object>> entry : coursesMap.entrySet()) {
            Map<String, Object> courseData = entry.getValue();
            Course course = new Course();
            populateCourseData(course, courseData);
            courses.add(course);
        }

        return courses;
    }

    private void populateCourseData(Course course, Map<String, Object> courseData) {
        course.setSectionName((String) courseData.get("section_name"));
        course.setTitle((String) courseData.get("title"));
        // Parse the date as a String and set it as a Date in the Course object
        String startDateString = (String) courseData.get("start_date");
        Date startDate = new Date(startDateString); 
        course.setStartDate(startDate);
    
        // Get the 'faculty' value as a String
        String faculty = (String) courseData.get("faculty");
    
        // Check if the 'faculty' value exceeds the database column's length
        int maxFacultyLength = 255; // Adjust this value based on your database column's actual length
        if (faculty != null && faculty.length() > maxFacultyLength) {
            // Truncate the 'faculty' value to fit within the column's length
            faculty = faculty.substring(0, maxFacultyLength);
        }
    
        course.setFaculty(faculty);
    
        // Parse available seats as an Integer
        int availableSeats = Integer.parseInt((String) courseData.get("available_seats"));
        course.setAvailableSeats(availableSeats);
    }
}

