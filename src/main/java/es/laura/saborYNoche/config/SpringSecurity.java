package es.laura.saborYNoche.config;

import es.laura.saborYNoche.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Cambiado a CustomUserDetailsService

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/register/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/users/**").hasRole("USER")
                                .requestMatchers("/empresarios/**").hasRole("EMPRESARIO")
                                .requestMatchers("/ocio/**").permitAll()
                )
                .formLogin(
                        form -> form
                                .loginPage("/index")
                                .loginProcessingUrl("/login")
                                .successHandler(new SimpleUrlAuthenticationSuccessHandler() {
                                    @Override
                                    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                                        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                                        if (authorities.contains(new SimpleGrantedAuthority("ROLE_EMPRESARIO"))) {
                                            return "/empresarios";
                                        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                                            return "/users";
                                        } else {
                                            throw new IllegalStateException("No role found for user " + authentication.getName());
                                        }
                                    }
                                })
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/index")
                                .permitAll()
                );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService) // Cambiado a customUserDetailsService
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
