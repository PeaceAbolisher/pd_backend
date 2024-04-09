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
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "professor")
    private List<Proposal> proposals;


    public Professor(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public boolean addProposal(Proposal proposal) {
        return proposals.add(proposal);
    }

    public boolean removeProposal(Proposal proposal) {
        return proposals.remove(proposal);
    }
}
