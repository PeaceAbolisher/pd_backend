package api.controller;

import api.entity.Professor;
import api.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorRestController {
    private final ProfessorService professorService;

    @Autowired
    public ProfessorRestController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public List<Professor> getAll() {
        return professorService.getAll();
    }


}
