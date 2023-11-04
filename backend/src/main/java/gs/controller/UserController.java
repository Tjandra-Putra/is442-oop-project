package gs.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gs.common.ApiModel;
import gs.common.NullError;
import gs.common.RequestModel;
import gs.entity.ChangePasswordRequest;
import gs.entity.User;
import gs.inputModel.UserInputModel;
import gs.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/user")

public class UserController {

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public ApiModel<ArrayList<UserInputModel>> getUser(){
        return ApiModel.ok(userService.getUser());
    }

    @GetMapping("/getUser/{id}")
    public ApiModel<ArrayList<UserInputModel>> getUserById(
        @PathVariable("id") String id
    ){
        return ApiModel.ok(userService.getUserById(id));
    }

    @PostMapping("/addUser")
    public ApiModel addUser(
        @RequestBody RequestModel requestModel
    ) throws Exception{
        ApiModel myApiModel = new ApiModel();
        userService.addUser(response, requestModel, myApiModel);

        return myApiModel;

    }

    // CHANGE PASSWORD - START
    @PatchMapping
    public ResponseEntity<?> changePassword(
        @RequestBody ChangePasswordRequest request, // Holds information of changing the password
        Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build(); // Inform that request was accepted 
    }
    // CHANGE PASSWORD - END
}