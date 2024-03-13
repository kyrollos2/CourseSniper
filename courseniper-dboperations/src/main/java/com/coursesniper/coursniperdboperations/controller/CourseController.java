package com.coursesniper.coursniperdboperations.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import org.springframework.data.domain.Page;
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.findAllCourses();
        if (courses.isEmpty()) {
            throw new ApiRequestException("No courses available at the moment.");
        }
        return ResponseEntity.ok(courses);
    }
    @GetMapping ("/(field)")
    public ResponseEntity<List<Course>> getAllCoursesWithSorting(@PathVariable String field) {
        List<Course> courses = courseService.findCourseWithSorting(field);
        if (courses.isEmpty()) {
            throw new ApiRequestException("No courses available at the moment.");
        }
        return ResponseEntity.ok(courses);
    }
    @GetMapping("/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<Course>> findCoursesWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Course> coursesWithPagination = courseService.findCoursesWithPagination(offset, pageSize);
        if (coursesWithPagination.isEmpty()) {
            throw new ApiRequestException("No courses available at the moment.");
        }
        return new ResponseEntity<>(coursesWithPagination, HttpStatus.OK); // Pass the page and then the status code
    }
    
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        return courseService.findCourseById(id)
                .orElseThrow(() -> new ApiRequestException("Course with ID: " + id + " not found."));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Course>> getCoursesByTitle(@RequestParam String title) {
        List<Course> courses = courseService.findCoursesByTitleLike(title);
        if (courses.isEmpty()) {
            throw new ApiRequestException("No courses found matching title: " + title);
        }
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/term/{term}")
    public ResponseEntity<List<Course>> getCoursesByTerm(@PathVariable String term) {
        List<Course> courses = courseService.findCoursesByTerm(term);
        if (courses.isEmpty()) {
            throw new ApiRequestException("No courses found for term: " + term);
        }
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/faculty/{facultyName}")
    public ResponseEntity<List<Course>> getCoursesByFacultyName(@PathVariable String facultyName) {
        List<Course> courses = courseService.findCoursesByFacultyName(facultyName);
        if (courses.isEmpty()) {
            throw new ApiRequestException("No courses found taught by faculty: " + facultyName);
        }
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        try {
            Course savedCourse = courseService.saveCourse(course);
            return ResponseEntity.ok(savedCourse);
        } catch (Exception e) {
            throw new ApiRequestException("Failed to add course: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable int id, @RequestBody Course course) {
        return courseService.updateCourse(id, course)
                .orElseThrow(() -> new ApiRequestException("Failed to update course with ID: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id) {
        boolean isDeleted = courseService.deleteCourse(id);
        if (!isDeleted) {
            throw new ApiRequestException("Failed to delete course with ID: " + id);
        }
        return ResponseEntity.ok().build();
    }
}
