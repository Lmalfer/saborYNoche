package es.laura.saborYNoche.repository;

import es.laura.saborYNoche.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    Optional<Promocion> findById(Long id);
    List<Promocion> findAll();
}
