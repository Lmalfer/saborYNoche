package es.laura.saborYNoche.service;

import es.laura.saborYNoche.dto.UserDto;
import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
    User getUsuarioActual();
    void updateUser(User user);
    User save(User user);
    User findByEmail(String email);
    boolean isFavorito(User usuario, Empresa empresa);
    void agregarFavorito(User usuario, Empresa empresa);
    void eliminarFavorito(User usuario, Empresa empresa);

}


