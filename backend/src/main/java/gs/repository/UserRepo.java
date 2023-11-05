package gs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gs.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

    @Query(value = "select * from user;", nativeQuery = true)
    List<User> getUser();
    
    @Query(value = "select * from user where user_id = ?;", nativeQuery = true)
    List<User> getUserById(String id);

    @Query(value = "select * from user where email = ?;", nativeQuery = true)
    Optional<User> getUserByEmail(String email);

}
