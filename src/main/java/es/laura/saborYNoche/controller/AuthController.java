package es.laura.saborYNoche.controller;

import es.laura.saborYNoche.dto.EmpresarioDto;
import es.laura.saborYNoche.dto.UserDto;
import es.laura.saborYNoche.entity.User;
import es.laura.saborYNoche.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login(Model model){
        // Obtener el nombre del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);

        // Verificar el rol del usuario autenticado utilizando Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER"); // Si no hay roles, se asume ROLE_USER

        // Pasar el rol a la vista
        model.addAttribute("role", role);

        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        // Verificar si hay errores de validación en el formulario
        if(result.hasErrors()){
            // Si hay errores, devolver el formulario de registro con los errores
            model.addAttribute("user", userDto);
            return "register"; // No es necesario incluir una barra inicial en el nombre de la vista
        }

        // Verificar si ya existe un usuario con el mismo correo electrónico
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
            // Si ya existe un usuario con el mismo correo electrónico, devolver el formulario de registro con el error
            model.addAttribute("user", userDto);
            return "register";
        }

        // Verificar si el usuario es empresario y guardar de acuerdo a eso
        if (userDto.isEsEmpresario()) {
            EmpresarioDto empresarioDto = new EmpresarioDto();
            empresarioDto.setFirstName(userDto.getFirstName());
            empresarioDto.setLastName(userDto.getLastName());
            empresarioDto.setEmail(userDto.getEmail());
            empresarioDto.setPassword(userDto.getPassword());
            userService.saveEmpresario(empresarioDto);
        } else {
            userService.saveUser(userDto);
        }

        // Redirigir a una URL permitida sin autenticación
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
