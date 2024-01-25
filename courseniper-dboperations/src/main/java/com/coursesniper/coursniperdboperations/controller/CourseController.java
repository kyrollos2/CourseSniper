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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coursesniper.coursniperdboperations.entity.Course;
import com.coursesniper.coursniperdboperations.exception.ApiRequestException;
import com.coursesniper.coursniperdboperations.service.CourseService;


@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    private static final String ERROR_FETCHING_ALL_COURSES = "Failed to fetch all courses";
    private static final String ERROR_COURSE_NOT_FOUND_BY_ID = "Course not found with ID: ";
    private static final String ERROR_SAVING_COURSE = "Failed to save course";
    private static final String ERROR_COURSE_NOT_FOUND_FOR_UPDATE = "Course not found for update with ID: ";
    private static final String ERROR_FAILED_TO_DELETE_COURSE = "Failed to delete course: Course not found with ID: ";
    
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        try {
            List<Course> courses = courseService.findAllCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            throw new ApiRequestException(ERROR_FETCHING_ALL_COURSES, e);
        }
    }

    @GetMapping("/course-title/search")
    public ResponseEntity<List<Course>> getCoursesByTitle(@RequestParam String title) {
        try {
            List<Course> courses = courseService.findCoursesByTitleLike(title);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            throw new ApiRequestException("Check your Spelling " + title + " Does not Appear in Our Database");
        }
    }

    @GetMapping("/section-name/{sectionName}")
    public Course getCoursebySectioneName(@RequestParam String param) {
        return new Course(); 
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Course> findCourseById(@PathVariable("id") int id) {
        try {
            return courseService.findCourseById(id)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new ApiRequestException(ERROR_COURSE_NOT_FOUND_BY_ID + id));
        } catch (Exception e) {
            throw new ApiRequestException(ERROR_FETCHING_ALL_COURSES + id + ": " + e.getMessage());
        }
    }
   /*  @PostMapping("/upload-json")
    public ResponseEntity<String> uploadJsonData(@RequestBody String jsonData) {
        try {
            // Parse the JSON data and save it to the database
            courseService.saveCoursesFromJson(jsonData);
            return ResponseEntity.ok("JSON data uploaded and saved successfully");
        } catch (Exception e) {
            // Handle exceptions if any occur
            throw new ApiRequestException("Error uploading JSON data", e);
        }
    }
    */
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        try {
            Course savedCourse = courseService.saveCourse(course);
            return ResponseEntity.ok(savedCourse);
        } catch (Exception e) {
            throw new ApiRequestException(ERROR_SAVING_COURSE, e);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") int id, @RequestBody Course course) {
        try {
            return courseService.updateCourse(id, course)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new ApiRequestException(ERROR_COURSE_NOT_FOUND_FOR_UPDATE + id));
        } catch (Exception e) {
            throw new ApiRequestException(ERROR_COURSE_NOT_FOUND_BY_ID + id + ": " + e.getMessage());
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") int id) {
        try {
            if (courseService.deleteCourse(id)) {
                return ResponseEntity.ok().build();
            } else {
                throw new ApiRequestException(ERROR_FAILED_TO_DELETE_COURSE + id);
            }
        } catch (Exception e) {
            throw new ApiRequestException(ERROR_COURSE_NOT_FOUND_BY_ID + id + ": " + e.getMessage());
        }
    }
}
