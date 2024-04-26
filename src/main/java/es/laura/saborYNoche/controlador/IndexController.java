package es.laura.saborYNoche.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping(value= {"/","/index"})
    public String idx() {

        return "index";
    }
}
