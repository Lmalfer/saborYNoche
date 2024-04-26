package es.laura.saborYNoche;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(form -> form
                        .loginPage("/loggin")
                        .loginProcessingUrl("/logginprocess")
                        .permitAll()
                );

        return http.build();
    }

}
