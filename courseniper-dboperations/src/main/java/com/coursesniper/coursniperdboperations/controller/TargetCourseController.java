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
import org.springframework.web.bind.annotation.RestController;

import com.coursesniper.coursniperdboperations.entity.TargetCourse;
import com.coursesniper.coursniperdboperations.exception.ApiRequestException;
import com.coursesniper.coursniperdboperations.service.TargetCourseService;

@RestController
@RequestMapping("/targetCourses")
public class TargetCourseController {

    private final TargetCourseService targetCourseService;

    private static final String ERROR_FETCHING_TARGET_COURSES = "Error fetching target courses";
    private static final String ERROR_TARGET_COURSE_NOT_FOUND = "Target course not found";
    private static final String ERROR_SAVING_TARGET_COURSE = "Error saving target course";
    
    @Autowired
    public TargetCourseController(TargetCourseService targetCourseService) {
        this.targetCourseService = targetCourseService;
    }

    @GetMapping
    public ResponseEntity<List<TargetCourse>> getAllTargetCourses() {
        try {
            List<TargetCourse> targetCourses = targetCourseService.findAllTargetCourses();
            return ResponseEntity.ok(targetCourses);
        } catch (Exception e) {
            throw new ApiRequestException(ERROR_FETCHING_TARGET_COURSES, e);
        }
    }
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TargetCourse>> getTargetCoursesByStudentId(@PathVariable Integer studentId) {
        List<TargetCourse> targetCourses = targetCourseService.findTargetCoursesByStudentId(studentId);
        return ResponseEntity.ok(targetCourses);
    }

   /*  @GetMapping("/{id}")
    public ResponseEntity<TargetCourse> getTargetCourseById(@PathVariable("id") int id) {
        return targetCourseService.findTargetCourseById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiRequestException(ERROR_TARGET_COURSE_NOT_FOUND));
    }
*/
    @PostMapping
    public ResponseEntity<TargetCourse> addTargetCourse(@RequestBody TargetCourse targetCourse) {
        try {
            TargetCourse savedTargetCourse = targetCourseService.saveTargetCourse(targetCourse);
            return ResponseEntity.ok(savedTargetCourse);
        } catch (Exception e) {
            throw new ApiRequestException(ERROR_SAVING_TARGET_COURSE, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TargetCourse> updateTargetCourse(@PathVariable("id") int id, @RequestBody TargetCourse targetCourse) {
        return targetCourseService.updateTargetCourse(id, targetCourse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiRequestException(ERROR_TARGET_COURSE_NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTargetCourse(@PathVariable("id") int id) {
        if (!targetCourseService.deleteTargetCourse(id)) {
            throw new ApiRequestException(ERROR_TARGET_COURSE_NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }
}
