package es.laura.saborYNoche.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/index")
    public String idx() {
        return "index";
    }
}
