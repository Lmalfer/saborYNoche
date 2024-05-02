package es.laura.saborYNoche.service;

import es.laura.saborYNoche.dto.UserDto;
import es.laura.saborYNoche.entity.Role;
import es.laura.saborYNoche.entity.User;
import es.laura.saborYNoche.repository.UserRepository;
import es.laura.saborYNoche.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

    @Service
    public class UserServiceImpl implements UserService {

        private UserRepository userRepository;
        private RoleRepository roleRepository;
        private PasswordEncoder passwordEncoder;

        public UserServiceImpl(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.passwordEncoder = passwordEncoder;
        }

        // UserServiceImpl.java
        @Override
        public void saveUser(UserDto userDto) {
            User user = new User();
            user.setName(userDto.getFirstName() + " " + userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            // Obtener el rol correspondiente (en este caso, ROLE_USER)
            Role role = roleRepository.findByName("ROLE_USER");
            if (role == null) {
                // Si no se encuentra el rol, puedes crearlo y guardarlo
                role = new Role("ROLE_USER");
                roleRepository.save(role);
            }
            // Asignar el rol al usuario
            user.setRole(role);

            userRepository.save(user);
        }




        @Override
        public User findUserByEmail(String email) {
            return userRepository.findByEmail(email);
        }

        @Override
        public List<UserDto> findAllUsers() {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map((user) -> mapToUserDto(user))
                    .collect(Collectors.toList());
        }

        private UserDto mapToUserDto(User user){
            UserDto userDto = new UserDto();
            String[] str = user.getName().split(" ");
            userDto.setFirstName(str[0]);
            userDto.setLastName(str[1]);
            userDto.setEmail(user.getEmail());
            return userDto;
        }

    }
