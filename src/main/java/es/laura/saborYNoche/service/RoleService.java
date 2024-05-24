package es.laura.saborYNoche.service;

import es.laura.saborYNoche.model.Role;
import es.laura.saborYNoche.enums.RoleEnum;


public interface RoleService {
    Role findRoleByName(RoleEnum name);

}
