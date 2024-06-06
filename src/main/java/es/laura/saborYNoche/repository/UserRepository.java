package es.laura.saborYNoche.repository;

import es.laura.saborYNoche.enums.RoleEnum;
import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM User u WHERE u.id = :userId AND :empresaId MEMBER OF u.empresas")
//    void removeFavorite(@Param("userId") Long userId, @Param("empresaId") Empresa empresa);
List<User> findByRole(RoleEnum rol);

}