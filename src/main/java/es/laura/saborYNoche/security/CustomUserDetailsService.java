package es.laura.saborYNoche.security;

import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.enums.RoleEnum;
import es.laura.saborYNoche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;


    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);

        if (user != null) {
            Collection<GrantedAuthority> authorities = getAuthorities(user.getRole());
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    authorities);
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection<GrantedAuthority> getAuthorities(RoleEnum roleEnum) {
        if (roleEnum != null) {
            // Me aseguro de acceder al nombre del enum para usarlo como ROLE_X
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + roleEnum.name()));
        } else {
            return Collections.emptyList();
        }
    }
}
