package gs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import gs.entity.Role;
@Repository
public interface RoleRepo extends CrudRepository<Role, String> {
    
}
