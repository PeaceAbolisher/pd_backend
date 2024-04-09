package api.service;

import api.entity.Student;
import api.repository.StudentRepository;
import api.util.COURSE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;

//        // para teste
//        Student s = new Student();
//        s.setName("test student");
//        studentRepository.save(s);
    }

    public List<Student> getAllStudents() {

        List<Student> all = studentRepository.findAll();
        log.info("getAllStudents {}", all);
        return all;
    }

    public Student getStudentById(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        // return student or null
        return studentOptional.orElse(null);
    }

    public Student createStudent(String num, String name, String email, COURSE course, double classification) {
        Student s = new Student(num, name, email, course, classification);
        return studentRepository.save(s);
    }

    public Student updateStudent(Long id, String num, String name, String email, COURSE course, double classification) {
        Student s = studentRepository.findById(id).orElse(null);

        if (s != null) {
            s.setNum(num);
            s.setName(name);
            s.setEmail(email);
            s.setCourse(course);
            s.setClassification(classification);
            return studentRepository.save(s);
        } else {
            return null;
        }
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
