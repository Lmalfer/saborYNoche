package es.laura.saborYNoche.repository;

import es.laura.saborYNoche.model.TipoEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoEstablecimientoRepository extends JpaRepository<TipoEstablecimiento, Integer> {
}
