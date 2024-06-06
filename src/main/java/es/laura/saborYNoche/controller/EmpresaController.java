package es.laura.saborYNoche.controller;

import es.laura.saborYNoche.exception.RecursoNoEncontradoException;
import es.laura.saborYNoche.model.Categoria;
import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.TipoEstablecimiento;
import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.repository.CategoriaRepository;
import es.laura.saborYNoche.repository.TipoEstablecimientoRepository;
import es.laura.saborYNoche.repository.UserRepository;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
    @GetMapping("/establecimientos/tipo/restaurantes")
    public ModelAndView listarRestaurantes() {
        List<Empresa> empresas = empresaService.listarRestaurantes();
        return new ModelAndView("listadoTipoEstablecimientos", "empresas", empresas);
    }

    @GetMapping("/establecimientos/tipo/bares")
    public ModelAndView listarBares() {
        List<Empresa> empresas = empresaService.listarBares();
        return new ModelAndView("listadoTipoEstablecimientos", "empresas", empresas);
    }

    @GetMapping("/establecimientos/tipo/discotecas")
    public ModelAndView listarDiscotecas() {
        List<Empresa> empresas = empresaService.listarDiscotecas();
        return new ModelAndView("listadoTipoEstablecimientos", "empresas", empresas);
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
        empresa.setFechaPublicacion(LocalDateTime.now()); // Establecer automáticamente la fecha de publicación
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

        // Copiar los datos de la empresa del formulario a la empresa obtenida de la base de datos
        empresaDB.setNombre(empresa.getNombre());
        empresaDB.setDescripcion(empresa.getDescripcion());
        empresaDB.setDireccion(empresa.getDireccion());
        empresaDB.setCategorias(empresa.getCategorias());
        empresaDB.setTipoEstablecimiento(empresa.getTipoEstablecimiento());
        empresaDB.setProvincia(empresa.getProvincia());
        empresaDB.setComunidad(empresa.getComunidad());
        empresaDB.setPoblacion(empresa.getPoblacion());
        empresaDB.setCodigoPostal(empresa.getCodigoPostal());

        // Verificar si se proporciona una nueva imagen
        if (!urlImagen.isEmpty()) {
            try {
                byte[] imagenBytes = urlImagen.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imagenBytes);
                empresaDB.setImagenBase64(base64Image);
            } catch (IOException e) {
                // Manejar error de procesamiento de imagen
                bindingResult.rejectValue("urlImagen", "Error.urlImagen", "Error al procesar la imagen");
                List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nombre"));
                List<TipoEstablecimiento> tiposEstablecimiento = tipoEstablecimientoRepositorio.findAll(Sort.by("nombre"));
                return new ModelAndView("editarEmpresa")
                        .addObject("empresa", empresa)
                        .addObject("categorias", categorias)
                        .addObject("tiposEstablecimiento", tiposEstablecimiento);
            }
        }

        // Guardar la empresa
        empresaService.guardarEmpresa(empresaDB);

        // Redireccionar a la página de administración
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
    @GetMapping("/reporte")
    public ModelAndView generarReporte() {
        List<User> empresarios = userService.findAllEmpresarios();
        List<Map<String, Object>> reporteData = new ArrayList<>();

        for (User user : empresarios) {
            Map<String, Object> reporte = new HashMap<>();

            Map<User, Long> numeroDeLocales = empresaService.obtenerNumeroDeLocalesPorEmpresario(user, Pageable.unpaged());
            List<Empresa> localesUltimoMes = empresaService.obtenerLocalesPublicadosUltimoMes(user, Pageable.unpaged());
            Map<Integer, Double> mediasVotos = empresaService.obtenerMediasVotosPorLocal(user, Pageable.unpaged());

            reporte.put("empresario", user);
            reporte.put("numeroDeLocales", numeroDeLocales);
            reporte.put("localesUltimoMes", localesUltimoMes);
            reporte.put("mediasVotos", mediasVotos);

            reporteData.add(reporte);
        }

        ModelAndView mav = new ModelAndView("reporte");
        mav.addObject("reporteData", reporteData);
        return mav;
    }

}