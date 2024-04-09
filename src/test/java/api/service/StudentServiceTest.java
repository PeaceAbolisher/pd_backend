package api.service;

import api.entity.Student;
import api.util.COURSE;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void insertStudent() {
        String rand = UUID.randomUUID().toString();
        Student created = studentService.createStudent(rand, rand, rand, COURSE.LEC, 1.2);

        Student found = studentService.getAllStudents().stream().filter(student1 -> Objects.equals(student1.getId(), created.getId())).findFirst().get();

        assertEquals(created.getId(), found.getId());
    }
}