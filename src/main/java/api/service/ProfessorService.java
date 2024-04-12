package api.service;

import api.entity.Professor;
import api.entity.Proposal;
import api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }


    public List<Professor> getAll() {
        return professorRepository.findAll();
    }

    public Professor getProfessorById(Long id) {
        Optional<Professor> professorOptional = professorRepository.findById(id);
        return professorOptional.orElse(null);
    }

    public Professor createProfessor(String name, String email) {
        Professor professor = new Professor(name, email);
        return professorRepository.save(professor);
    }

    public Professor updateProfessor(Long id, String name, String email, List<Proposal> proposalList) {
        Professor p = getProfessorById(id);
        if (p == null) {
            return null;
        }

        p.setName(name);
        p.setEmail(email);
        p.setProposals(proposalList);
        return professorRepository.save(p);
    }

    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }

    public void save(Professor professor) {
        professorRepository.save(professor);
    }

    public List<Professor> getProfessorsOrderByProposalsSize() {
        return professorRepository.findAllOrderByProposalsSizeAsc();
    }
}
