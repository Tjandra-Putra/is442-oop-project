package gs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gs.model.user.User;
import gs.service.user.UserService;

@RestController
@RequestMapping(path = "api/user")

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public List<Object[]> getUser(){
        return userService.getUser();
    }

    @PostMapping("/addUser")
    public String addUser(
        @RequestBody List<User> user
    ) throws Exception{
        userService.addUser(user);
        return null;
    }
}
