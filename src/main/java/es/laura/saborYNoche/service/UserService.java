package es.laura.saborYNoche.service;

import es.laura.saborYNoche.dto.UserDto;
import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    boolean isValidPassword(String password);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
    User getUsuarioActual();
    void updateUser(User user);
    User save(User user);
    User findByEmail(String email);
    void agregarFavorito(User usuario, Empresa empresa);
    void eliminarFavorito(User usuario, Integer empresaId);
    List<User> findAllEmpresarios();

}


