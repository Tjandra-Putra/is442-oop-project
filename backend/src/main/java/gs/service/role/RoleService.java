package gs.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gs.entity.Role;
import gs.repository.RoleRepo;

@Service
public class RoleService {
    @Autowired
    private RoleRepo rolerepo; 

    public Role createNewRole(Role role) {
        return rolerepo.save(role); // Save the role and return the role record
    }
}
