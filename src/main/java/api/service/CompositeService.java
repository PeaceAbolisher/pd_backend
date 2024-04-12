package api.service;

import api.entity.Candidature;
import api.entity.Professor;
import api.entity.Proposal;
import api.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompositeService {
    private final CandidatureService candidatureService;
    private final ProfessorService professorService;
    private final ProposalService proposalService;

    @Autowired
    public CompositeService(CandidatureService candidatureService, ProfessorService professorService, ProposalService proposalService) {
        this.candidatureService = candidatureService;
        this.professorService = professorService;
        this.proposalService = proposalService;
    }


    // TODO: testar queries
    public int assign() {
        List<Candidature> candidatures = candidatureService.getUnusedCandidatures();
        List<Professor> professorList = professorService.getProfessorsOrderByProposalsSize();
        int assigned = 0;
        int professorsIndex = 0;

        for (Candidature candidature : candidatures) {
            candidature.setUsedInAssignment(true);
            List<Proposal> candidatureProposals = candidature.getProposals();
            if (candidatureProposals != null && !candidatureProposals.isEmpty()) {
                for (Proposal cp : candidatureProposals) {
                    // check if the candidature proposal is unassigned
                    if (cp.getStudentNumber() == null) {
                        Student student = candidature.getStudent();
                        if (student.getCourse() == cp.getCourse()) {
                            // assign student and professor to proposal
                            cp.setStudentNumber(student.getNum());

                            Professor professor = professorList.get(professorsIndex);
                            cp.setProfessor(professor);
                            professor.addProposal(cp);

                            // increment and reset index when it reaches the end of the list
                            professorsIndex = (professorsIndex + 1) % professorList.size();

                            saveToDatabase(candidature, cp, professor);

                            assigned++;
                            // move to the next candidature
                            break;
                        }
                    }
                }
            }
        }
        return assigned;
    }


    @Transactional(propagation = Propagation.REQUIRED)
        // save candidature, proposal and professor to database
    void saveToDatabase(Candidature candidature, Proposal cp, Professor professor) {
        proposalService.save(cp);
        professorService.save(professor);
        candidatureService.save(candidature);
    }
}
