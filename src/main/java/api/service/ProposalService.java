package api.service;

import api.entity.Candidature;
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
    private CandidatureService candidatureService;

    @Autowired
    public ProposalService(ProposalRepository proposalRepository, CandidatureService candidatureService) {
        this.proposalRepository = proposalRepository;
        this.candidatureService = candidatureService;
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

//    public boolean assign() {
//        List<Proposal> proposalsNotAssigned = proposalRepository.findAllByStudentNumberIsNull();
//
//        return false;
//    }

    //TODO: verificar se vem ordenado pela nota dos alunos
    public boolean assign() {
        List<Candidature> candidatures = candidatureService.getUnusedCandidatures();

        for (Candidature candidature : candidatures) {
            candidature.setUsedInAssignment(true);
            List<Proposal> candidatureProposals = candidature.getProposals();
            if (candidatureProposals != null && !candidatureProposals.isEmpty()) {
                for (Proposal cp : candidatureProposals) {
                    // check if the candidature proposal is unassigned
                    if (cp.getStudentNumber() == null) {
                        Student student = candidature.getStudent();
                        if (student != null) {
                            // assign student to proposal
                            cp.setStudentNumber(student.getNum());
                            // TODO: assign a professor
                            proposalRepository.save(cp);
                            // move to the next candidature after assigning
                            break;
                        }
                    }
                }
            }
        }
        return true; // Successfully assigned proposals to students

    }
}
