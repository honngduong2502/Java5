package java5.asm.service;

import java5.asm.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService  {
    List<Role> getAllRole();
    Optional<Role> findRoleById(int id);

}
