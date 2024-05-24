package es.laura.saborYNoche.repository;

import es.laura.saborYNoche.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
