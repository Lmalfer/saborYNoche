package es.laura.saborYNoche.security;

import es.laura.saborYNoche.entity.Role;
import es.laura.saborYNoche.entity.User;
import es.laura.saborYNoche.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (user.getRole() != null) {
                authorities.addAll(mapRolesToAuthorities(user.getRole()));
            }

            // Verificar si el usuario es un empresario y agregar el rol correspondiente
            if (user.isEsEmpresario()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_EMPRESARIO"));
            }

            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    authorities);
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
        return Collections.singletonList(authority);
    }
}
