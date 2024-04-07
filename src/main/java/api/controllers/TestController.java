package api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    // apenas para teste
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        String hello = "Hello World from backend!";
        return ResponseEntity.ok(hello);
    }

    // apenas para teste
    @GetMapping("")
    public ResponseEntity<String> home() {
        String home = "home page i guess?";
        return ResponseEntity.ok(home);
    }
}
