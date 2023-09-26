package com.example.backend;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class controller {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/user")
    public ArrayList<String> users() {
        ArrayList<String> userList = new ArrayList<>();
        userList.add("User1");
        userList.add("User2");
        return userList;
    }
}