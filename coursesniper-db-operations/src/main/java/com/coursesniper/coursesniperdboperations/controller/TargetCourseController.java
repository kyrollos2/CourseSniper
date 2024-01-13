package main.java.com.coursesniper.coursesniperdboperations.controller;

import java.util.List;

import com.coursesniper.coursesniperdboperations.entity.TargetCourse;

@RestController
@RequestMapping("/targetCourses")
public class TargetCourseController {

    private final TargetCourseService targetCourseService;

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
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching target courses", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TargetCourse> getTargetCourseById(@PathVariable("id") int id) {
        try {
            return targetCourseService.findTargetCourseById(id)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Target course not found"));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching target course", e);
        }
    }

    @PostMapping
    public ResponseEntity<TargetCourse> addTargetCourse(@RequestBody TargetCourse targetCourse) {
        try {
            TargetCourse savedTargetCourse = targetCourseService.saveTargetCourse(targetCourse);
            return ResponseEntity.ok(savedTargetCourse);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error saving target course", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TargetCourse> updateTargetCourse(@PathVariable("id") int id, @RequestBody TargetCourse targetCourse) {
        try {
            return targetCourseService.updateTargetCourse(id, targetCourse)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Target course not found"));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error updating target course", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTargetCourse(@PathVariable("id") int id) {
        try {
            if (targetCourseService.deleteTargetCourse(id)) {
                return ResponseEntity.ok().build();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Target course not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting target course", e);
        }
    }
}