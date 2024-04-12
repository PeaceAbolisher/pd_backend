package api.entity;

import api.util.COURSE;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String companyName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private COURSE course;
    @Column(nullable = true)
    private String studentNumber;     // proposal assigned to student X

    @ManyToMany(mappedBy = "proposals")
    private List<Candidature> candidatures;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    public Proposal(String title, String description, String companyName, COURSE course) {
        this.title = title;
        this.description = description;
        this.companyName = companyName;
        this.course = course;
    }
}
