package api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @Column(nullable = false)
//    private long num;
//    @Column(nullable = false)
    private String name;
//    @Column(nullable = false)
//    private String email;
//    @Column(nullable = false)
//    private String course;              // talvez colocar como enum
//    @Column(nullable = false)
//    private double classification;
//    @Column(nullable = false)
//    private boolean hasCandidature;     // TODO: 1 student : 1 candidature

    public StudentEntity() {
//        hasCandidature = false;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

//    public long getNum() {
//        return num;
//    }
//
//    public void setNum(long num) {
//        this.num = num;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getCourse() {
//        return course;
//    }
//
//    public void setCourse(String course) {
//        this.course = course;
//    }
//
//    public double getClassification() {
//        return classification;
//    }
//
//    public void setClassification(double classification) {
//        this.classification = classification;
//    }
//
//    public boolean getHasCandidature() {
//        return hasCandidature;
//    }
//
//    public void setHasCandidature(boolean hasCandidature) {
//        this.hasCandidature = hasCandidature;
//    }
}
