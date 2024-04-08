package api.service;

import api.entity.Student;
import api.repository.StudentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentEntityRepository studentEntityRepository;

    @Autowired
    public StudentService(StudentEntityRepository studentEntityRepository) {
        this.studentEntityRepository = studentEntityRepository;

        Student s = new Student();
        s.setName("teste student");
        studentEntityRepository.save(s);
    }

    public List<Student> getAllStudents() {
        return studentEntityRepository.findAll();
    }

    public Student getStudentById(Long studentId) {
        Optional<Student> studentOptional = studentEntityRepository.findById(studentId);

        // return student or null
        return studentOptional.orElse(null);
    }
}
