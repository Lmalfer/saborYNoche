package es.laura.saborYNoche.service;

import es.laura.saborYNoche.dto.UserDto;
import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.enums.RoleEnum;
import es.laura.saborYNoche.repository.EmpresaRepository;
import es.laura.saborYNoche.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Asignar el rol según la selección del usuario
        if (userDto.getRole() != null) {
            user.setRole(userDto.getRole());
        } else {
            user.setRole(RoleEnum.USER); // Por defecto, si no se especifica ningún rol
        }

        userRepository.save(user);
    }
    public boolean isValidPassword(String password) {
        return password!= null && password.length() >= 6;
    }
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }

    @Override
    public User getUsuarioActual() {
        // Obtiene el nombre de usuario del UserDetails actual
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Busca al usuario en la base de datos por su nombre de usuario
        return userRepository.findByEmail(username);
    }


    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    @Override
//    @Transactional
//    public boolean isFavorito(User usuario, Empresa empresa) {
//        return usuario.getEmpresas().contains(empresa);
//    }

    @Override
    @Transactional
    public void agregarFavorito(User usuario, Empresa empresa) {
        usuario.getEmpresas().add(empresa);
        userRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminarFavorito(User usuario, Integer empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
        if (empresa!= null) {
            usuario.getEmpresas().removeIf(e -> e.getId().equals(empresaId));
            userRepository.save(usuario);
        } else {
            throw new RuntimeException("La empresa con ID " + empresaId + " no se encontró en la base de datos.");
        }
    }


    public List<User> findAllEmpresarios() {
        return userRepository.findByRole(RoleEnum.EMPRESARIO);
    }

}


