package gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import gs.model.user.User;

public interface UserRepo extends CrudRepository<User, Integer>{

    @Query(value = "select * from user;", nativeQuery = true)
    List<Object[]> getUser();
    
    @Query(value = "select * from user where user_id = ?;", nativeQuery = true)
    List<Object[]> getUserById(String id);

}
