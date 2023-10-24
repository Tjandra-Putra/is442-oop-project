package gs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gs.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, String> {
    
}
