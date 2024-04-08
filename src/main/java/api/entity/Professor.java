package api.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Professor {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "professor")
    private List<Proposal> proposals;

    public Professor() {
    }

    public Professor(String name, String email, List<Proposal> proposals) {
        this.name = name;
        this.email = email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }
}
