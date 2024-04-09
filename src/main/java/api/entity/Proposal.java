package api.entity;

import api.util.COURSE;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String studentNumber;     // proposal assigned to student X

    @ManyToOne
    @JoinColumn(name = "candidature_id")
    private Candidature candidature;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
}
