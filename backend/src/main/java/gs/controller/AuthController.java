package gs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gs.auth.JwtUtil;
import gs.common.ApiModel;
import gs.common.RequestModel;
import gs.entity.User;
import gs.model.request.LoginReq;
import gs.model.response.ErrorRes;
import gs.model.response.LoginRes;
import gs.repository.UserRepo;
import gs.service.user.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/rest/auth")
public class AuthController {

    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private UserService userService;

    @Resource
    protected UserRepo userRepo;

    private final AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity login(
        @RequestBody LoginReq loginReq
    ){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));

            String email = authentication.getName();
            User user = new User(email, "");

            String token = jwtUtil.createToken(user);
            String userId = String.valueOf(userRepo.getUserByEmail(email).getUserId());
            LoginRes loginRes = new LoginRes(email, token, userId);

            return ResponseEntity.ok(loginRes);
            
        }

        catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);    
        }

        catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/addUser")
    public ApiModel addUser(
        @RequestBody RequestModel requestModel
    ) throws Exception{
        ApiModel myApiModel = new ApiModel();
        userService.addUser(response, requestModel, myApiModel);

        return myApiModel;

    }
    
}
