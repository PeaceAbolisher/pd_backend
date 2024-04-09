package api.service;

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

    public boolean assign() {
        List<Proposal> proposalsNotAssigned = proposalRepository.findAllByStudentNumberIsNull();

        return false;
    }
}
