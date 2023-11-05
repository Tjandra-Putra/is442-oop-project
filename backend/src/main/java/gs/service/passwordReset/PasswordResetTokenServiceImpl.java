package gs.service.passwordReset;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Service;

import gs.entity.PasswordResetToken;
import gs.entity.User;
import gs.repository.PasswordResetTokenRepo;
import jakarta.annotation.Resource;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    @Resource
    protected PasswordResetTokenRepo passwordResetTokenRepo;

    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);

        passwordResetTokenRepo.save(passwordResetToken);
    }

    // Method to valid password reset token
    public String validatePasswordResetToken(String passwordResetToken) {
        PasswordResetToken passwordToken = (PasswordResetToken) passwordResetTokenRepo.findByToken(passwordResetToken);

        if(passwordToken == null){
            return "Invalid password reset token.";
        }

       User user = passwordToken.getUser();
       
       Calendar calendar = Calendar.getInstance();
       if ((passwordToken.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "Link has already expired.";
        }
        
        return "valid";
    }
    
    public Optional<User> findUserByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(((PasswordResetToken) passwordResetTokenRepo.findByToken(passwordResetToken)).getUser());
    }

    public PasswordResetToken findPasswordResetToken(String token){
      return (PasswordResetToken) passwordResetTokenRepo.findByToken(token);
    }
}

