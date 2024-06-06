package es.laura.saborYNoche.repository;

import es.laura.saborYNoche.model.Categoria;
import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Page<Empresa> findByUser(User user, Pageable pageable);


    @Query("SELECT e FROM Empresa e " +
            "WHERE (:provincia IS NULL OR LOWER(e.provincia) LIKE LOWER(CONCAT('%', :provincia, '%'))) " +
            "AND (:poblacion IS NULL OR LOWER(e.poblacion) LIKE LOWER(CONCAT('%', :poblacion, '%'))) " +
            "AND (:busqueda IS NULL OR LOWER(e.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) " +
            "OR LOWER(e.direccion) LIKE LOWER(CONCAT('%', :busqueda, '%'))) " )
    List<Empresa> buscarEmpresas(
            @Param("provincia") String provincia,
            @Param("poblacion") String poblacion,
            @Param("busqueda") String busqueda);

    @Query("SELECT e FROM Empresa e JOIN e.categorias c WHERE c.id IN :categorias")
    List<Empresa> buscarEmpresasPorCategorias(@Param("categorias") List<Integer> categorias);

    @Query("SELECT e FROM Empresa e WHERE e.tipoEstablecimiento.id = :tipo")
    List<Empresa> buscarEmpresasPorTipo(@Param("tipo") Integer tipo);

    @Query("SELECT e FROM Empresa e JOIN e.categorias c WHERE c.id IN :categorias AND e.tipoEstablecimiento.id = :tipo")
    List<Empresa> buscarEmpresasPorCategoriasYTipo(@Param("categorias") List<Integer> categorias, @Param("tipo") Integer tipo);

    @Query("SELECT e FROM Empresa e WHERE e.tipoEstablecimiento.id IN (:ids)")
    List<Empresa> findByTipoEstablecimientoIds(@Param("ids") List<Integer> ids);
//    List<Empresa> findEmpresaByCategoria(Categoria categoria);

}

