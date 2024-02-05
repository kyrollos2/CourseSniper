package com.coursesniper.coursniperdboperations.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coursesniper.coursniperdboperations.entity.TargetCourse;
import com.coursesniper.coursniperdboperations.service.TargetCourseService;

@RestController
@RequestMapping("/api/target-courses")
public class TargetCourseController {

    private final TargetCourseService targetCourseService;

    @Autowired
    public TargetCourseController(TargetCourseService targetCourseService) {
        this.targetCourseService = targetCourseService;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TargetCourse>> getTargetCoursesByStudentId(@PathVariable Integer studentId) {
        List<TargetCourse> targetCourses = targetCourseService.findAllByStudentId(studentId);
        if(targetCourses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(targetCourses);
    }

    @PostMapping
    public ResponseEntity<TargetCourse> addTargetCourse(@RequestBody TargetCourse targetCourse) {
        return targetCourseService.addTargetCourse(targetCourse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTargetCourse(@PathVariable("id") int id) {
        targetCourseService.removeTargetCourse(id);
        return ResponseEntity.ok().build();
    }
}
