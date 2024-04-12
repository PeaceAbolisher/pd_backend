package api.controller;

import api.entity.Candidature;
import api.service.CandidatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/candidatures")
public class CandidatureRestController {
    private final CandidatureService candidatureService;

    @Autowired
    public CandidatureRestController(CandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    @GetMapping
    public List<Candidature> getAll() {
        return candidatureService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidature> getCandidatureById(@PathVariable Long id) {
        Candidature candidature = candidatureService.getCandidatureById(id);

        if (candidature != null) {
            return ResponseEntity.ok(candidature);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Candidature> createCandidature(
            @RequestParam Long studentId,
            @RequestParam Long[] proposalsIds
    ) {
        Candidature c = candidatureService.createCandidature(studentId, proposalsIds);

        if (c == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCandidature(
            @PathVariable Long id,
            @RequestParam Boolean usedInAssignment,
            @RequestParam Long studentId,
            @RequestParam Long[] proposalsIds
    ) {
        try {
            Candidature c = candidatureService.updateCandidature(id, usedInAssignment, studentId, proposalsIds);
            if (c != null)
                return ResponseEntity.ok(c);
            else
                return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidature(@PathVariable Long id) {
        candidatureService.deleteCandidature(id);
        return ResponseEntity.noContent().build();
    }
}
