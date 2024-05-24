package es.laura.saborYNoche.service;

import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmpresaService {

    Page<Empresa> listarTodasLasEmpresas(Pageable pageable);
    void guardarEmpresa(Empresa empresa);
    Empresa obtenerEmpresaPorId(Integer id);
    void eliminarEmpresa(Integer id);
    Page<Empresa> listarEmpresasPorUsuario(User user, Pageable pageable);

}
