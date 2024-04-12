package api.controller;

import api.entity.Professor;
import api.service.CompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/professors")
public class ProfessorRestController {
    private final CompositeService compositeService;

    @Autowired
    public ProfessorRestController(CompositeService compositeService) {
        this.compositeService = compositeService;
    }

    @GetMapping
    public List<Professor> getAll() {
        return compositeService.getAllProfessors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getStudentById(@PathVariable Long id) {
        Professor professor = compositeService.getProfessorById(id);

        if (professor != null) {
            return ResponseEntity.ok(professor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Professor> createProfessor(
            @RequestParam String name,
            @RequestParam String email
    ) {
        Professor p = compositeService.createProfessor(name, email);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfessor(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam List<Long> proposalsIds
            ) {
        try {
            Professor p = compositeService.updateProfessor(id, name, email, proposalsIds);
            if (p != null) {
                return ResponseEntity.ok(p);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        compositeService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
}
