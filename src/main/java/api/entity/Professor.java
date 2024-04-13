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

    public void addProposal(Proposal proposal) {
        proposals.add(proposal);
    }

    public void removeProposal(Proposal proposal) {
        proposals.remove(proposal);
    }
}
