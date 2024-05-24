package es.laura.saborYNoche.service;

import es.laura.saborYNoche.exception.RecursoNoEncontradoException;
import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepositorio;

    @Override
    public Page<Empresa> listarTodasLasEmpresas(Pageable pageable) {
        return empresaRepositorio.findAll(pageable);
    }

    @Override
    public void guardarEmpresa(Empresa empresa) {
        if (empresa.getUser() == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        empresaRepositorio.save(empresa);
    }

    @Override
    public Empresa obtenerEmpresaPorId(Integer id) {
        return empresaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada con ID: " + id));
    }

    @Override
    public void eliminarEmpresa(Integer id) {
        Empresa empresa = empresaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada con id " + id));
        empresaRepositorio.delete(empresa);
    }

    @Override
    public Page<Empresa> listarEmpresasPorUsuario(User user, Pageable pageable) {
        return empresaRepositorio.findByUser(user, pageable);
    }
}
