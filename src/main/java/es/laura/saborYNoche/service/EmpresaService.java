package es.laura.saborYNoche.service;

import es.laura.saborYNoche.model.Categoria;
import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.TipoEstablecimiento;
import es.laura.saborYNoche.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
}
