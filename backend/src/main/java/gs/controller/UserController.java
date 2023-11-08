package gs.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gs.common.ApiModel;
import gs.common.NullError;
import gs.common.RequestModel;
import gs.entity.ChangePasswordRequest;
import gs.entity.PasswordResetRequest;
import gs.entity.User;
import gs.event.listener.EmailTemplate;
import gs.inputModel.UserInputModel;
import gs.service.user.UserService;
import jakarta.mail.MessagingException;
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

    private EmailTemplate eventListener;

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

    // FORGET PASSWORD - START
    @PostMapping("/passwordResetRequest")
    public String resetPasswordRequest( @RequestBody PasswordResetRequest passwordResetRequest, final HttpServletRequest httpServletRequest) throws MessagingException, UnsupportedEncodingException {
        
        // Find user by email
        Optional<User> user = userService.getUserByEmail(passwordResetRequest.getEmail()); 

        String passwordResetUrl = "";

        // When user email is found
        if(user.isPresent()){
            // Create a new password reset token
            String passwordResetToken = UUID.randomUUID().toString(); 

            // Save the new password reset token into db
            userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);

            // Generate a link to send to the user via email
            passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(httpServletRequest), passwordResetToken);
        }

        return passwordResetUrl;
    }

    private String passwordResetEmailLink(User user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/register/reset-password?token="+passwordToken;
        eventListener.sendPasswordResetVerificationEmail(url);
        //log.info("Click the link to reset your password :  {}", url);
        return url;
    }

    @PostMapping("/resetPassword")
    public String resetPassword(
        @RequestBody PasswordResetRequest passwordResetRequest, @RequestParam("token") String passwordResetToken
        ) {
            // Validate token
            String tokenValidationResult = userService.validatePasswordResetToken(passwordResetToken);

            if(!tokenValidationResult.equalsIgnoreCase("valid")){
                return "Invalid password reset token.";
            }

            // Find user by password reset token
            User user = userService.findUserByPasswordToken(passwordResetToken);

            if (user != null) {
                // Reset user password
                userService.resetUserPassword(user, passwordResetRequest.getNewPassword());

                return "Password reset has been successfully.";                
            }

            return "Invalid password reset token.";
        }

    public String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath();
    }
    // FORGET PASSWORD - END


    // LOGOUT 
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        // .. perform logout
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/home"; // CHANGE ACCORDINGLY
    }
}