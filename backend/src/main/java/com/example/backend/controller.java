package com.example.backend;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class controller {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}

