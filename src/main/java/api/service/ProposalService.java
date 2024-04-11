package api.service;

import api.entity.Candidature;
import api.entity.Professor;
import api.entity.Proposal;
import api.entity.Student;
import api.repository.ProposalRepository;
import api.util.COURSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProposalService {
    private final ProposalRepository proposalRepository;
    private final ProfessorService professorService;
    private final CandidatureService candidatureService;

    @Autowired
    public ProposalService(ProposalRepository proposalRepository, CandidatureService candidatureService, ProfessorService professorService) {
        this.proposalRepository = proposalRepository;
        this.candidatureService = candidatureService;
        this.professorService = professorService;
    }

    public List<Proposal> getAll() {
        return proposalRepository.findAll();
    }

    public Proposal getProposalById(Long id) {
        Optional<Proposal> proposalOptional = proposalRepository.findById(id);
        return proposalOptional.orElse(null);
    }

    public Proposal createProposal(Proposal proposal) {
        return proposalRepository.save(proposal);
    }

    public Proposal updateProposal(Long id, Proposal proposal) {
        Proposal p = proposalRepository.findById(id).orElse(null);

        if (p != null) {
            p.setTitle(proposal.getTitle());
            p.setDescription(proposal.getDescription());
            p.setCompanyName(proposal.getCompanyName());
            p.setCourse(proposal.getCourse());
            p.setStudentNumber(proposal.getStudentNumber());
            return proposalRepository.save(p);
        } else {
            return null;
        }
    }

    public void deleteProposal(Long id) {
        proposalRepository.deleteById(id);
    }

    public int assign() {
        int assigned = 0;
        List<Candidature> candidatures = candidatureService.getUnusedCandidatures();
        List<Professor> professors = professorService.getProfessorsOrderByProposalsSize();
        int i = 0;

        // TODO: quando é que dá erro?
        for (Candidature candidature : candidatures) {
            candidature.setUsedInAssignment(true);
            List<Proposal> candidatureProposals = candidature.getProposals();
            if (candidatureProposals != null && !candidatureProposals.isEmpty()) {
                for (Proposal cp : candidatureProposals) {
                    // check if the candidature proposal is unassigned
                    if (cp.getStudentNumber() == null) {
                        Student student = candidature.getStudent();
                        if (student != null) {
                            if (student.getCourse() == cp.getCourse()) {
                                // assign student and professor to proposal
                                cp.setStudentNumber(student.getNum());

                                Professor professor = professors.get(i);
                                cp.setProfessor(professor);
                                professor.addProposal(cp);

                                // increment and reset index when it reaches the end of the list
                                i = (i + 1) % professors.size();

                                saveToDatabase(candidature, cp, professor);

                                assigned++;
                                // move to the next candidature
                                break;
                            }
                        }
                    }
                }
            }
        }
        return assigned;
    }

    // save candidature, proposal and professor to database
    private void saveToDatabase(Candidature candidature, Proposal cp, Professor professor) {
        proposalRepository.save(cp);
        professorService.save(professor);
        candidatureService.save(candidature);
    }
}
