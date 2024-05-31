package es.laura.saborYNoche.service;

import es.laura.saborYNoche.enums.RoleEnum;
import es.laura.saborYNoche.exception.RecursoNoEncontradoException;
import es.laura.saborYNoche.model.*;
import es.laura.saborYNoche.repository.CategoriaRepository;
import es.laura.saborYNoche.repository.EmpresaRepository;
import es.laura.saborYNoche.repository.TipoEstablecimientoRepository;
import es.laura.saborYNoche.repository.UserRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final CategoriaRepository categoriaRepository;
    private final TipoEstablecimientoRepository tipoEstablecimientoRepository;
    private final UserRepository userRepository;



    @Autowired
    public EmpresaServiceImpl(EmpresaRepository empresaRepository, CategoriaRepository categoriaRepository, TipoEstablecimientoRepository tipoEstablecimientoRepository, UserRepository userRepository) {
        this.empresaRepository = empresaRepository;
        this.categoriaRepository = categoriaRepository;
        this.tipoEstablecimientoRepository = tipoEstablecimientoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Empresa> listarTodasLasEmpresas() {
        return empresaRepository.findAll();
    }

    @Override
    public void guardarEmpresa(Empresa empresa) {
        if (empresa.getUser() == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        empresaRepository.save(empresa);
    }

    @Override
    public Empresa obtenerEmpresaPorId(Integer id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada con ID: " + id));
    }

    @Override
    public void eliminarEmpresa(Integer id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada con id " + id));
        empresaRepository.delete(empresa);
    }

    @Override
    public Page<Empresa> listarEmpresasPorUsuario(User user, Pageable pageable) {
        return empresaRepository.findByUser(user, pageable);
    }

    @Override
    public List<Empresa> buscarEmpresas(String provincia, String poblacion, String busqueda) {
        return empresaRepository.buscarEmpresas(provincia, poblacion, busqueda);
    }

    @Override
    public List<Categoria> listarTodasLasCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public List<TipoEstablecimiento> listarTodosLosTipos() {
        return tipoEstablecimientoRepository.findAll();
    }

    @Override
    public List<Empresa> buscarEmpresasPorCategoriasYTipo(List<Integer> categorias, Integer tipo) {
        return empresaRepository.buscarEmpresasPorCategoriasYTipo(categorias, tipo);
    }

@Override
    public Optional<Empresa> findEmpresaById(Integer id) {
        return empresaRepository.findById(id);
    }

    @Transactional
    public void añadirPuntuacionEmpresa(Integer empresaId, String email, Integer puntuacion) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(empresaId);
        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new RuntimeException("Usuario no encontrado");
            }
            empresa.getUsuariosQueVotaron().add(user);
            empresa.getPuntuaciones().put(user, puntuacion);
            empresaRepository.save(empresa);
        } else {
            throw new RuntimeException("Empresa no encontrada");
        }
    }

}
