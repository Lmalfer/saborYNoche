package es.laura.saborYNoche.repository;

import es.laura.saborYNoche.entity.Role;
import es.laura.saborYNoche.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
   Role findRoleByName(RoleEnum name);
}
