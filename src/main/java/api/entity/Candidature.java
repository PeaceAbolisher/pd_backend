package api.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(mappedBy = "candidature", fetch = FetchType.EAGER)
    private List<Proposal> proposals;


    public Candidature() {
    }

    public Candidature(Student student, List<Proposal> proposals) {
        this.student = student;
        this.proposals = proposals;
    }

    public boolean addProposal(Proposal proposal) {
        return proposals.add(proposal);
    }

    public boolean removeProposal(Proposal proposal) {
        return proposals.remove(proposal);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }
}
