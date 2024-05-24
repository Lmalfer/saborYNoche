package es.laura.saborYNoche.controller;

import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
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
}
