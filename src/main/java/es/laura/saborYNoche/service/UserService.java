package es.laura.saborYNoche.service;

import es.laura.saborYNoche.dto.EmpresarioDto;
import es.laura.saborYNoche.dto.UserDto;
import es.laura.saborYNoche.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    void saveEmpresario(EmpresarioDto empresarioDto);


    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}