package gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gs.entity.PasswordResetToken;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {
    @Query(value = "select * from passwordresettoken where theToken = ?;", nativeQuery = true)
    List<PasswordResetToken> findByToken(String theToken);
        
} 