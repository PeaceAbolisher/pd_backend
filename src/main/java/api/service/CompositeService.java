package api.service;

import api.entity.Candidature;
import api.entity.Professor;
import api.entity.Proposal;
import api.entity.Student;
import api.util.COURSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompositeService {
    final CandidatureService candidatureService;
    final ProfessorService professorService;
    final ProposalService proposalService;
    final StudentService studentService;

    @Autowired
    public CompositeService(CandidatureService candidatureService, ProfessorService professorService, ProposalService proposalService, StudentService studentService) {
        this.candidatureService = candidatureService;
        this.professorService = professorService;
        this.proposalService = proposalService;
        this.studentService = studentService;
    }


    // TODO: testar queries
    @Transactional
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


    // save candidature, proposal and professor to database
    private void saveToDatabase(Candidature candidature, Proposal cp, Professor professor) {
        proposalService.save(cp);
        professorService.save(professor);
        candidatureService.save(candidature);
    }

    // for each proposal id, fetch it from the database and add to the list
    public List<Proposal> fetchAndAddProposalsToList(List<Long> proposalsIds) {
        List<Proposal> proposalList = new ArrayList<>();
        for (Long propId : proposalsIds) {
            Proposal prop = proposalService.getProposalById(propId);
            if (prop == null) {
                throw new RuntimeException("Invalid proposal id " + propId);
            }
            proposalList.add(prop);
        }
        return proposalList;
    }

    // -------------- PROPOSAL -------------- //
    public List<Proposal> getAllProposals() {
        return proposalService.getAll();
    }

    public Proposal getProposalById(Long id) {
        return proposalService.getProposalById(id);
    }

    public Proposal createProposal(String title, String description, String companyName, COURSE course) {
        return proposalService.createProposal(title, description, companyName, course);
    }

    public Proposal updateProposal(Long id, String title, String description, String companyName, COURSE course, String studentNumber) {
        return proposalService.updateProposal(id, title, description, companyName, course, studentNumber);
    }

    public void deleteProposal(Long id) {
        proposalService.deleteProposal(id);
    }


    // -------------- CANDIDATURE -------------- //
    public List<Candidature> getAllCandidatures() {
        return candidatureService.getAll();
    }

    public Candidature getCandidatureById(Long id) {
        return candidatureService.getCandidatureById(id);
    }

    public Candidature createCandidature(Long studentId, List<Long> proposalsIds) {
        Student student = studentService.getStudentById(studentId);
        List<Proposal> proposalList = new ArrayList<>(proposalsIds.size());

        for (long propId : proposalsIds) {
            Proposal p = proposalService.getProposalById(propId);
            proposalList.add(p);
        }
        return candidatureService.createCandidature(student, proposalList);
    }

    public Candidature updateCandidature(Long id, Boolean usedInAssignment, Long studentId, List<Long> proposalsIds) {
        if (usedInAssignment == null || studentId == null || proposalsIds == null) {
            throw new RuntimeException("Parameters cannot be null.");
        }

        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Invalid student id " + studentId);
        }
        List<Proposal> proposalList = fetchAndAddProposalsToList(proposalsIds);
        return candidatureService.updateCandidature(id, usedInAssignment, student, proposalList);
    }

    public void deleteCandidature(Long id) {
        candidatureService.deleteCandidature(id);
    }

    // -------------- PROFESSOR -------------- //
    public List<Professor> getAllProfessors() {
        return professorService.getAll();
    }

    public Professor getProfessorById(Long id) {
        return professorService.getProfessorById(id);
    }

    public Professor createProfessor(String name, String email) {
        return professorService.createProfessor(name, email);
    }

    public Professor updateProfessor(Long id, String name, String email, List<Long> proposalsIds) {
        if (name == null || email == null || proposalsIds == null) {
            throw new RuntimeException("Parameters cannot be null.");
        }

        List<Proposal> proposalList = fetchAndAddProposalsToList(proposalsIds);
        return professorService.updateProfessor(id, name, email, proposalList);
    }

    public void deleteProfessor(Long id) {
        professorService.deleteProfessor(id);
    }

    // -------------- STUDENT -------------- //
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    public Student getStudentById(Long id) {
        return studentService.getStudentById(id);
    }

    public Student createStudent(String num, String name, String email, COURSE course, double classification) {
        return studentService.createStudent(num, name, email, course, classification);
    }

    public Student updateStudent(Long id, String num, String name, String email, COURSE course, Double classification) {
        return studentService.updateStudent(id, num, name, email, course, classification);
    }

    public void deleteStudent(Long id) {
        studentService.deleteStudent(id);
    }
}
