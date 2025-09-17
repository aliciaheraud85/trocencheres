package fr.eni.ecole.projet.trocencheres.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello")
    public String helloWorld(){
        return "hello";
    }
}
