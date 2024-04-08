package api.controller;

import api.entity.Proposal;
import api.entity.Student;
import api.service.ProposalService;
import api.util.COURSE;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proposals")
public class ProposalRestController {
    private final ProposalService proposalService;

    @Autowired
    public ProposalRestController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @GetMapping
    public List<Proposal> getAllProposals() {
        return proposalService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proposal> getProposalById(@PathVariable Long id) {
        Proposal proposal = proposalService.getProposalById(id);

        if (proposal != null) {
            return ResponseEntity.ok(proposal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Proposal> createProposal(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("company") String companyName,
            @RequestParam("course") COURSE course
    ) {
        Proposal proposal = proposalService.createProposal(title, description, companyName, course);

        if (proposal == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(proposal);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Proposal> updateStudent(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("company") String companyName,
            @RequestParam("course") COURSE course,
            @RequestParam("studentNumber") String studentNumber
    ) {
        Proposal proposal = proposalService.updateProposal(id, title, description, companyName, course, studentNumber);

        if (proposal != null) {
            return ResponseEntity.ok(proposal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        proposalService.deleteProposal(id);
        return ResponseEntity.noContent().build();
    }
}
