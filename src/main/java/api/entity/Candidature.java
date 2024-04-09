package api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(mappedBy = "candidature", fetch = FetchType.EAGER)
    private List<Proposal> proposals;


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
}
