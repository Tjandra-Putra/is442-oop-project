package gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import gs.model.user.User;

public interface UserRepo extends JpaRepository<User, Integer>{

    @Query(value = "select * from user;", nativeQuery = true)
    List<Object[]> getUser();
    
    @Query(value = "select * from user where user_id = ?;", nativeQuery = true)
    List<Object[]> getUserById(String id);

    @Query(value = "select * from user where email = ?;", nativeQuery = true)
    List<UserDetails> getUserByEmail(String email);
}
