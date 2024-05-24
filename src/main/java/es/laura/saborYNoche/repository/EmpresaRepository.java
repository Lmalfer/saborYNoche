package es.laura.saborYNoche.repository;

import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Page<Empresa> findByUser(User user, Pageable pageable);

}
