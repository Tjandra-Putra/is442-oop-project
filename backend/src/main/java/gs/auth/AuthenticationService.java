package gs.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gs.config.JwtService;
import gs.model.user.User;
import gs.repository.UserRepo;

@Service
public class AuthenticationService {

    private UserRepo userRepo;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;
    
    public AuthenticationResponse register(RegisterRequest request){
        var user = User.builder()
            .email(request.getEmail())
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();

        userRepo.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        var user = userRepo.getUserByEmail(request.getEmail())
            // throw exception
            .orElseThrow();

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }
}
