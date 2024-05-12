package es.laura.saborYNoche.service;

import es.laura.saborYNoche.entity.Role;
import es.laura.saborYNoche.enums.RoleEnum;
import es.laura.saborYNoche.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(RoleEnum name) {
        return roleRepository.findRoleByName(name);
    }
}
