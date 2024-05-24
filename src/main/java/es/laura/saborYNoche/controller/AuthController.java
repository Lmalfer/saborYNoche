package es.laura.saborYNoche.controller;

import es.laura.saborYNoche.dto.UserDto;
import es.laura.saborYNoche.model.Role;
import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.enums.RoleEnum;
import es.laura.saborYNoche.service.RoleService;
import es.laura.saborYNoche.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

@Controller
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;

    public AuthController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority("ROLE_EMPRESARIO"))) {
                return "redirect:/empresarios";
            } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                return "redirect:/users";
            }
        }
        return "login";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user") UserDto userDto,
                               BindingResult result) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }

    @GetMapping("/empresarios")
    public String empresarios(Model model) {
        // Aquí debes proporcionar el nombre del rol empresario como un RoleEnum
        Role roleEmpresario = roleService.findRoleByName(RoleEnum.EMPRESARIO);

        // Agregar el rol encontrado al modelo para que pueda ser usado en la vista
        model.addAttribute("empresarios", roleEmpresario);

        return "empresarios";
    }

}
