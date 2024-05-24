package es.laura.saborYNoche.controller;

import es.laura.saborYNoche.exception.RecursoNoEncontradoException;
import es.laura.saborYNoche.model.Categoria;
import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.TipoEstablecimiento;
import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.repository.CategoriaRepository;
import es.laura.saborYNoche.repository.TipoEstablecimientoRepository;
import es.laura.saborYNoche.service.EmpresaService;
import es.laura.saborYNoche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/adminEmpresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private CategoriaRepository categoriaRepositorio;

    @Autowired
    private TipoEstablecimientoRepository tipoEstablecimientoRepositorio;

    @Autowired
    private UserService userService;

    // Listar empresas por usuario
    @GetMapping("")
    public ModelAndView listarEmpresas(@PageableDefault(sort = "nombre", size = 5) Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        Page<Empresa> empresas = empresaService.listarEmpresasPorUsuario(user, pageable);
        return new ModelAndView("adminEmpresa")
                .addObject("empresas", empresas);
    }

    // Mostrar formulario de nueva empresa
    @GetMapping("/nuevaEmpresa")
    public ModelAndView mostrarFormularioDeNuevaEmpresa() {
        List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nombre"));
        List<TipoEstablecimiento> tiposEstablecimiento = tipoEstablecimientoRepositorio.findAll(Sort.by("nombre"));
        return new ModelAndView("nuevaEmpresa")
                .addObject("empresa", new Empresa())
                .addObject("categorias", categorias)
                .addObject("tiposEstablecimiento", tiposEstablecimiento);
    }

    // Registrar nueva empresa
    @PostMapping("/nuevaEmpresa")
    public ModelAndView registrarEmpresa(@Validated Empresa empresa, BindingResult bindingResult) {
        MultipartFile urlImagen = empresa.getUrlImagen();
        if (bindingResult.hasErrors() || urlImagen.isEmpty()) {
            if (urlImagen.isEmpty()) {
                bindingResult.rejectValue("urlImagen", "NotEmpty.urlImagen", "Debe seleccionar una imagen");
            }
            List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nombre"));
            List<TipoEstablecimiento> tiposEstablecimiento = tipoEstablecimientoRepositorio.findAll(Sort.by("nombre"));
            return new ModelAndView("nuevaEmpresa")
                    .addObject("empresa", empresa)
                    .addObject("categorias", categorias)
                    .addObject("tiposEstablecimiento", tiposEstablecimiento);
        }
        try {
            byte[] imagenBytes = urlImagen.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imagenBytes);
            empresa.setImagenBase64(base64Image);
        } catch (IOException e) {
            bindingResult.rejectValue("urlImagen", "Error.urlImagen", "Error al procesar la imagen");
            List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nombre"));
            List<TipoEstablecimiento> tiposEstablecimiento = tipoEstablecimientoRepositorio.findAll(Sort.by("nombre"));
            return new ModelAndView("nuevaEmpresa")
                    .addObject("empresa", empresa)
                    .addObject("categorias", categorias)
                    .addObject("tiposEstablecimiento", tiposEstablecimiento);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        empresa.setUser(user);
        empresaService.guardarEmpresa(empresa);
        return new ModelAndView("redirect:/adminEmpresa");
    }

    // Mostrar formulario de editar empresa
    @GetMapping("/{id}/editarEmpresa")
    public ModelAndView mostrarFormularioDeEditarEmpresa(@PathVariable Integer id) {
        Empresa empresa = empresaService.obtenerEmpresaPorId(id);
        if (empresa == null) {
            throw new RecursoNoEncontradoException("Empresa no encontrada con id " + id);
        }
        List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nombre"));
        List<TipoEstablecimiento> tiposEstablecimiento = tipoEstablecimientoRepositorio.findAll(Sort.by("nombre"));
        return new ModelAndView("editarEmpresa")
                .addObject("empresa", empresa)
                .addObject("categorias", categorias)
                .addObject("tiposEstablecimiento", tiposEstablecimiento);
    }

    // Actualizar empresa
    @PostMapping("/{id}/editarEmpresa")
    public ModelAndView actualizarEmpresa(@PathVariable Integer id, @Validated Empresa empresa, BindingResult bindingResult) {
        MultipartFile urlImagen = empresa.getUrlImagen();
        if (bindingResult.hasErrors()) {
            List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nombre"));
            List<TipoEstablecimiento> tiposEstablecimiento = tipoEstablecimientoRepositorio.findAll(Sort.by("nombre"));
            return new ModelAndView("editarEmpresa")
                    .addObject("empresa", empresa)
                    .addObject("categorias", categorias)
                    .addObject("tiposEstablecimiento", tiposEstablecimiento);
        }
        Empresa empresaDB = empresaService.obtenerEmpresaPorId(id);
        if (empresaDB == null) {
            throw new RecursoNoEncontradoException("Empresa no encontrada con id " + id);
        }
        empresaDB.setNombre(empresa.getNombre());
        empresaDB.setDescripcion(empresa.getDescripcion());
        empresaDB.setDireccion(empresa.getDireccion());
        empresaDB.setCategorias(empresa.getCategorias());
        empresaDB.setTipoEstablecimiento(empresa.getTipoEstablecimiento());
        empresaDB.setProvincia(empresa.getProvincia());
        empresaDB.setComunidad(empresa.getComunidad());
        empresaDB.setPoblacion(empresa.getPoblacion());
        empresaDB.setCodigoPostal(empresa.getCodigoPostal());

        if (!urlImagen.isEmpty()) {
            try {
                byte[] imagenBytes = urlImagen.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imagenBytes);
                empresaDB.setImagenBase64(base64Image);
            } catch (IOException e) {
                bindingResult.rejectValue("urlImagen", "Error.urlImagen", "Error al procesar la imagen");
                List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nombre"));
                List<TipoEstablecimiento> tiposEstablecimiento = tipoEstablecimientoRepositorio.findAll(Sort.by("nombre"));
                return new ModelAndView("editarEmpresa")
                        .addObject("empresa", empresa)
                        .addObject("categorias", categorias)
                        .addObject("tiposEstablecimiento", tiposEstablecimiento);
            }
        }

        empresaService.guardarEmpresa(empresaDB);
        return new ModelAndView("redirect:/adminEmpresa");
    }

    // Eliminar empresa
    @PostMapping("/{id}/eliminarEmpresa")
    public String eliminarEmpresa(@PathVariable Integer id) {
        Empresa empresa = empresaService.obtenerEmpresaPorId(id);
        if (empresa == null) {
            throw new RecursoNoEncontradoException("Empresa no encontrada con id " + id);
        }
        empresaService.eliminarEmpresa(id);
        return "redirect:/adminEmpresa";
    }
}
