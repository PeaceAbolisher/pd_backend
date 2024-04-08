package api.service;

import api.model.StudentEntity;
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

        StudentEntity s = new StudentEntity();
        s.setName("teste student");
        studentEntityRepository.save(s);
    }

    public List<StudentEntity> getAllStudents() {
        return studentEntityRepository.findAll();
    }

    public StudentEntity getStudentById(Long studentId) {
        Optional<StudentEntity> studentOptional = studentEntityRepository.findById(studentId);

        // return student or null
        return studentOptional.orElse(null);
    }
}
