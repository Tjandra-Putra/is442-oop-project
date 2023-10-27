package gs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gs.entity.User;
import gs.repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepo.getUserByEmail(email);
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword()).roles(roles.toArray(new String[0])).build();

        return userDetails;
    }
    
}
