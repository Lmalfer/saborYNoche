package es.laura.saborYNoche.service;

import es.laura.saborYNoche.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmpresaService {
    List<Empresa> listarTodasLasEmpresas();
    void guardarEmpresa(Empresa empresa);
    Empresa obtenerEmpresaPorId(Integer id);
    void eliminarEmpresa(Integer id);
    Page<Empresa> listarEmpresasPorUsuario(User user, Pageable pageable);
    List<Empresa> buscarEmpresas(String provincia, String poblacion, String busqueda);
    List<Categoria> listarTodasLasCategorias();
    List<TipoEstablecimiento> listarTodosLosTipos();
    List<Empresa> buscarEmpresasPorCategoriasYTipo(List<Integer> categorias, Integer tipo);
    Optional<Empresa> findEmpresaById(Integer id);
    void añadirPuntuacionEmpresa(Integer empresaId, String email, Integer puntuacion);
//    List<EmpresaResponse> obtenerTodasLasEmpresasConMedias();
//    double calcularMediaVotos(Integer empresaId);
    List<Map<String, Object>> obtenerMediasVotosPorEmpresa();
    Empresa getEmpresaById(Integer id);
}
