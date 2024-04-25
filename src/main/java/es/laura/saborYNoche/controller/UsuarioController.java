package es.laura.saborYNoche.controller;

import es.laura.saborYNoche.dto.UsuarioDTO;
import es.laura.saborYNoche.dto.UsuarioLoginDTO;
import es.laura.saborYNoche.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {



    @GetMapping("/area/administrador")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> accesoSoloAdministrador() {
        return new ResponseEntity<>("Eres administrador", HttpStatus.OK);
    }

    @GetMapping("/area/usuario-restringido")
    @PreAuthorize("hasAuthority('USUARIO_RESTRINGIDO')")
    public ResponseEntity<?> accesoSoloUsuarioRestringido() {
        return new ResponseEntity<>("Eres un usuario restringido", HttpStatus.OK);
    }

    @GetMapping("/area/usuario-logueado")
    public ResponseEntity<?> accesoSoloUsuarioLogueas() {
        return new ResponseEntity<>("Eres un usuario logueado, no importa tu rol", HttpStatus.OK);
    }

}
