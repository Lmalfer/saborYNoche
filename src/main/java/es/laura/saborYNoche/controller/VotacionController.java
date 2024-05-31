package es.laura.saborYNoche.controller;

import es.laura.saborYNoche.model.VotoRequest;
import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.service.EmpresaService;
import es.laura.saborYNoche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/votos")
public class VotacionController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UserService userService;

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
}
