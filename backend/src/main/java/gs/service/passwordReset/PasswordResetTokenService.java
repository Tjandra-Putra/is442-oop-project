package gs.service.passwordReset;

import java.util.Optional;

import gs.entity.PasswordResetToken;
import gs.entity.User;
import gs.repository.PasswordResetTokenRepo;

public interface PasswordResetTokenService {
    void createPasswordResetTokenForUser(User user, String passwordToken);

    String validatePasswordResetToken(String passwordResetToken);

    Optional<User> findUserByPasswordToken(String passwordResetToken);

    PasswordResetToken findPasswordResetToken(String token);
}
