package es.laura.saborYNoche.controller;

import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.EmpresaResponse;
import es.laura.saborYNoche.model.VotoRequest;
import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.repository.EmpresaRepository;
import es.laura.saborYNoche.service.EmpresaService;
import es.laura.saborYNoche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/votos")
public class VotacionController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<?> votarEmpresa(@RequestBody VotoRequest votoRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try {
            empresaService.añadirPuntuacionEmpresa(votoRequest.getEmpresaId(), email, votoRequest.getNota());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/media-votos")
    public ResponseEntity<List<EmpresaResponse>> obtenerEmpresasConMedias() {
        List<Map<String, Object>> resultados = empresaService.obtenerMediasVotosPorEmpresa();
        List<EmpresaResponse> empresasConMedias = new ArrayList<>();

        for (Map<String, Object> resultado : resultados) {
            Integer empresaId = (Integer) resultado.get("empresa_id");
            BigDecimal mediaVotos = (BigDecimal) resultado.get("media_votos");

            // Crear una instancia de EmpresaResponse y agregarla a la lista
            empresasConMedias.add(new EmpresaResponse(empresaId, null, mediaVotos));
            // ^ En este punto, deberías reemplazar null con el nombre de la empresa si lo tienes disponible
        }

        return ResponseEntity.ok(empresasConMedias);
    }
}
