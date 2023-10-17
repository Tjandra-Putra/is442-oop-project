package gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import gs.entity.user.User;

public interface UserRepo extends JpaRepository<User, Long>{

    @Query(value = "select * from user;", nativeQuery = true)
    List<Object[]> getUser();
    
    @Query(value = "select * from user where user_id = ?;", nativeQuery = true)
    List<Object[]> getUserById(String id);

}
