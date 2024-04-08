package api.service;

import api.entity.Proposal;
import api.entity.Student;
import api.repository.ProposalRepository;
import api.util.COURSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProposalService {
    private final ProposalRepository proposalRepository;

    @Autowired
    public ProposalService(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    public List<Proposal> getAll() {
        return proposalRepository.findAll();
    }

    public Proposal getProposalById(Long id) {
        Optional<Proposal> proposalOptional = proposalRepository.findById(id);
        return proposalOptional.orElse(null);
    }

    public Proposal createProposal(String title, String description, String companyName, COURSE course) {
        Proposal proposal = new Proposal(title, description, companyName, course);
        return proposalRepository.save(proposal);
    }

    public Proposal updateProposal(Long id, String title, String description, String companyName, COURSE course, String studentNumber) {
        Proposal p = proposalRepository.findById(id).orElse(null);

        if (p != null) {
            p.setTitle(title);
            p.setDescription(description);
            p.setCompanyName(companyName);
            p.setCourse(course);
            p.setStudentNumber(studentNumber);
            return proposalRepository.save(p);
        } else {
            return null;
        }
    }

    public void deleteProposal(Long id) {
        proposalRepository.deleteById(id);
    }
}
