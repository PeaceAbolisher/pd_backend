package api.service;

import api.entity.Candidature;
import api.entity.Proposal;
import api.entity.Student;
import api.repository.CandidatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CandidatureService {
    private final CandidatureRepository candidatureRepository;
    private final StudentService studentService;
    private final ProposalService proposalService;

    @Autowired
    public CandidatureService(CandidatureRepository candidatureRepository, StudentService studentService, @Lazy ProposalService proposalService) {
        this.candidatureRepository = candidatureRepository;
        this.studentService = studentService;
        this.proposalService = proposalService;
    }

    public List<Candidature> getAll() {
        return candidatureRepository.findAll();
    }

    public Candidature getCandidatureById(Long id) {
        Optional<Candidature> candidatureOptional = candidatureRepository.findById(id);
        return candidatureOptional.orElse(null);
    }

    public Candidature createCandidature(Long studentId, Long[] proposalsIds) {
        Student student = studentService.getStudentById(studentId);
        List<Proposal> proposalList = new ArrayList<>(proposalsIds.length);

        for (long propId : proposalsIds) {
            Proposal p = proposalService.getProposalById(propId);
            proposalList.add(p);
        }

        if (student == null || proposalList.isEmpty()) {
            return null;
        } else {
            Candidature candidature = new Candidature(student, proposalList);
            return candidatureRepository.save(candidature);
        }
    }

    public Candidature updateCandidature(Long id, Boolean usedInAssignment, Long studentId, Long[] proposalsIds) {
        Candidature c = getCandidatureById(id);
        if (c == null) {
            return null;
        }

        if (usedInAssignment == null || studentId == null || proposalsIds == null) {
            throw new RuntimeException("Parameters cannot be null.");
        }

        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Invalid student id " + studentId);
        }

        List<Proposal> proposalList = new ArrayList<>();
        for (Long propId : proposalsIds) {
            Proposal p = proposalService.getProposalById(propId);
            if (p == null) {
                throw new RuntimeException("Invalid proposal id " + propId);
            }
            proposalList.add(p);
        }

        c.setUsedInAssignment(usedInAssignment);
        c.setStudent(student);
        c.setProposals(proposalList);
        return candidatureRepository.save(c);
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
