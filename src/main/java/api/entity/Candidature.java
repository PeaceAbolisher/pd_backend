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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "used_in_assignment")
    private boolean usedInAssignment;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToMany(mappedBy = "candidatures")
    private List<Proposal> proposals;
}
