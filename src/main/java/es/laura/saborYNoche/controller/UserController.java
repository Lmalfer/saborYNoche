package es.laura.saborYNoche.controller;

import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.repository.EmpresaRepository;
import es.laura.saborYNoche.repository.UserRepository;
import es.laura.saborYNoche.service.EmpresaService;
import es.laura.saborYNoche.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/datosPersonales")
    public String datosPersonales(Model model, HttpServletRequest request) {
        // Obtiene el usuario actual autenticado
        User usuario = userService.getUsuarioActual();
        model.addAttribute("usuario", usuario);
        // Guarda la URL de referencia en el modelo
        String referer = request.getHeader("Referer");
        model.addAttribute("referer", referer != null ? referer : "/defaultPage");
        return "datosPersonales";
    }

    @GetMapping("/perfil")
    public String perfil(Model model) {
        User usuario = userService.getUsuarioActual();

        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @PostMapping("/datosPersonales")
    public String actualizarDatosPersonales(@ModelAttribute("usuario") User usuario,
                                            @RequestParam("currentPassword") String currentPassword,
                                            @RequestParam("newPassword") String newPassword,
                                            @RequestParam("referer") String referer,
                                            Model model) {
        try {
            User usuarioActual = userService.getUsuarioActual();
            usuarioActual.setName(usuario.getName());

            // Cambiar la contraseña si se proporciona una nueva
            if (currentPassword != null && !currentPassword.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
                if (passwordEncoder.matches(currentPassword, usuarioActual.getPassword())) {
                    usuarioActual.setPassword(passwordEncoder.encode(newPassword));
                } else {
                    model.addAttribute("errorMessage", "La contraseña actual es incorrecta.");
                    model.addAttribute("referer", referer);
                    return "datosPersonales";
                }
            }

            userService.updateUser(usuarioActual);
            model.addAttribute("usuario", usuarioActual);
            model.addAttribute("successMessage", "Los datos personales se han actualizado correctamente.");
            // Redirige a la URL de referencia guardada en el modelo
            return "redirect:" + referer;
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Ha ocurrido un error al actualizar los datos personales.");
            model.addAttribute("referer", referer);
            return "datosPersonales"; // Redirige a la página de datos personales con un mensaje de error
        }
    }


    @PostMapping("/favoritos/agregar/{empresaId}")
    public ResponseEntity<String> agregarFavorito(@PathVariable Integer empresaId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        User currentUser = userService.findByEmail(userDetails.getUsername());
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        Optional<Empresa> empresaOpt = empresaRepository.findById(empresaId);
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            userService.agregarFavorito(currentUser, empresa);
            return ResponseEntity.ok("Se ha añadido a favoritos");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa no encontrada");
        }
    }

    @PostMapping("/favoritos/eliminar/{empresaId}")
    public ResponseEntity<String> eliminarFavorito(@PathVariable Integer empresaId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        User currentUser = userService.findByEmail(userDetails.getUsername());
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        Optional<Empresa> empresaOpt = empresaRepository.findById(empresaId);
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            userService.eliminarFavorito(currentUser, empresa);
            return ResponseEntity.ok("Se ha eliminado de favoritos");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa no encontrada");
        }
    }

    // Método para listar favoritos
    @GetMapping("/favoritos")
    public String mostrarFavoritos(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser == null) {
            // Manejar el caso de un usuario no autenticado según sea necesario
            return "redirect:/login";
        }

        User user = userService.findByEmail(currentUser.getUsername());
        List<Empresa> favoritos = user.getEmpresas();
        model.addAttribute("favoritos", favoritos);

        return "favoritos";
    }

}
