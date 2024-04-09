package api.controller;

import api.entity.Student;
import api.service.StudentService;
import api.util.COURSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


//@CrossOrigin(origins = "http://localhost:8180")
@RestController
@RequestMapping("/students")
public class StudentRestController {
    private final StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);

        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(
            @RequestParam("number") String num,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("course") COURSE course,
            @RequestParam("classification") double classification
    ) {
        Student student = studentService.createStudent(num, name, email, course, classification);

        if (student == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestParam("number") String num,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("course") COURSE course,
            @RequestParam("classification") double classification
    ) {
        Student student = studentService.updateStudent(id, num, name, email, course, classification);

        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
