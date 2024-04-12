package api.service;

import api.entity.Candidature;
import api.entity.Professor;
import api.entity.Proposal;
import api.entity.Student;
import api.repository.CandidatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatureService {
    private final CandidatureRepository candidatureRepository;

    @Autowired
    public CandidatureService(CandidatureRepository candidatureRepository) {
        this.candidatureRepository = candidatureRepository;
    }

    public List<Candidature> getAll() {
        return candidatureRepository.findAll();
    }

    public Candidature getCandidatureById(Long id) {
        Optional<Candidature> candidatureOptional = candidatureRepository.findById(id);
        return candidatureOptional.orElse(null);
    }

    public Candidature createCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    public Candidature updateCandidature(Long id, Candidature candidature) {
        Candidature c = getCandidatureById(id);

        if (c != null) {
            c.setStudent(candidature.getStudent());
            c.setProposals(candidature.getProposals());
            return candidatureRepository.save(c);
        } else {
            return null;
        }
    }

    public void deleteCandidature(Long id) {
        candidatureRepository.deleteById(id);
    }

    public List<Candidature> getUnusedCandidatures() {
        return candidatureRepository.findAllUnassignedCandidaturesOrderByStudentClassificationDesc();
    }

    public void save(Candidature candidature) {
        candidatureRepository.save(candidature);
    }
}
