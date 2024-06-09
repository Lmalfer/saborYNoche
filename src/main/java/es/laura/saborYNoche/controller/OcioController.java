package es.laura.saborYNoche.controller;

import es.laura.saborYNoche.model.Categoria;
import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.TipoEstablecimiento;
import es.laura.saborYNoche.repository.EmpresaRepository;
import es.laura.saborYNoche.repository.TipoEstablecimientoRepository;
import es.laura.saborYNoche.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ocio")
public class OcioController {

    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private TipoEstablecimientoRepository tipoEstablecimientoRepository;
    @Autowired
private EmpresaRepository empresaRepository;
    @GetMapping("/empresas")
    public String listarEmpresas(
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String poblacion,
            @RequestParam(required = false) String busqueda,
            Model model) {
        List<Empresa> empresas = empresaService.buscarEmpresas(provincia, poblacion, busqueda);
//        List<Categoria> categorias = empresaService.listarTodasLasCategorias();
        List<TipoEstablecimiento> tiposEstablecimiento = empresaService.listarTodosLosTipos();
        model.addAttribute("empresas", empresas);
//        model.addAttribute("categorias", categorias);
      model.addAttribute("tiposEstablecimiento", tiposEstablecimiento);
        return "ocio";
    }
    @GetMapping("/filtro")
    public String filtrarPorTipoEstablecimiento(
            @RequestParam Integer tipoEstablecimientoId,
            Model model) {
        List<Empresa> empresas = empresaRepository.findByTipoEstablecimientoId(tipoEstablecimientoId);
        model.addAttribute("empresas", empresas);
        return "ocio :: empresasLista";  // Retorna solo la parte de la lista de empresas usando Thymeleaf fragment expression
    }

//    @GetMapping("/buscarPorCategoriasYTipos")
//    public String buscarPorCategoriasYTipos(
//            @RequestParam(required = false) String categorias,
//            @RequestParam(required = false) Integer tipo,
//            Model model) {
//
//        List<Integer> categoriaIds = categorias != null ? Arrays.stream(categorias.split(","))
//                .map(Integer::parseInt)
//                .collect(Collectors.toList()) : null;
//        List<Empresa> empresas = empresaService.buscarEmpresasPorCategoriasYTipo(categoriaIds, tipo);
//        model.addAttribute("empresas", empresas);
//        return "ocio";
//    }
}
