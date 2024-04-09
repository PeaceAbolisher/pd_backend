package api.service;

import api.entity.Student;
import api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Slf4j
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        // return student or null
        return studentOptional.orElse(null);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student student) {
        Student s = studentRepository.findById(id).orElse(null);

        if (s != null) {
            s.setNum(student.getNum());
            s.setName(student.getName());
            s.setEmail(student.getEmail());
            s.setCourse(student.getCourse());
            s.setClassification(student.getClassification());
            return studentRepository.save(s);
        } else {
            return null;
        }
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
