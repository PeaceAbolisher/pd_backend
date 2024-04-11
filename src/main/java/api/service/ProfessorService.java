package api.service;

import api.entity.Professor;
import api.entity.Proposal;
import api.repository.ProfessorRepository;
import api.util.COURSE;
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

    public Professor createProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor updateProfessor(Long id, Professor professor) {
        Professor p = professorRepository.findById(id).orElse(null);

        if (p != null) {
            p.setName(professor.getName());
            p.setEmail(professor.getEmail());
            return professorRepository.save(p);
        } else {
            return null;
        }
    }

    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }

    public void save(Professor professor) {
        professorRepository.save(professor);
    }

    public List<Professor> getProfessorsOrderByProposalsSize(){
        return professorRepository.findAllOrderByProposalsSizeAsc();
    }
}
