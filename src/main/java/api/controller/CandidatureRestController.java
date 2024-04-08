package api.controller;

import api.service.CandidatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidatures")
public class CandidatureRestController {
    private final CandidatureService candidatureService;

    @Autowired
    public CandidatureRestController(CandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }
}
