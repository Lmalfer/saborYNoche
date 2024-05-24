package es.laura.saborYNoche.repository;

import es.laura.saborYNoche.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
