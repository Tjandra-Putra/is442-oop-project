package gs.service;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gs.common.JwtUtil;
import gs.entity.JwtRequest;
import gs.entity.JwtResponse;
import gs.entity.User;
import gs.repository.UserRepo;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();

        // Authenication
        authenicate(userName, userPassword);

        final UserDetails userDetails = loadUserByUsername(userName);
        List<User> userList = userRepo.getUserByUserName(userName);
        User user = userList.get(0);
        String newGeneratedToken = "" ; 
        if (!userList.isEmpty()) {
             user = userList.get(0);
             newGeneratedToken = jwtUtil.generateToken(userDetails);
        } else {
            throw new Exception("User is not found");
        }

        return new JwtResponse(user, newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {        
        // Return token and user details
        List<User> userList = userRepo.getUserByUserName(userName);
        User user = userList.get(0);

        if (user != null) {
            Set<SimpleGrantedAuthority> authorities = getAuthorities(user);
            return new org.springframework.security.core.userdetails.User(
                user.getUsername(), 
                user.getPassword(),
                authorities
            );
        } else {
            throw new UsernameNotFoundException("Username is not valid");
        }
    }
    
    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });

        return authorities;
    }

    private void authenicate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));    
        } catch (DisabledException e) {
            throw new Exception("User is disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials from user");
        }
        
    }
}
