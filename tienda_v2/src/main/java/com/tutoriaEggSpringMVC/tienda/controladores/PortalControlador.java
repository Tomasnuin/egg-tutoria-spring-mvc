package com.tutoriaEggSpringMVC.tienda.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @GetMapping("/")
    public String inicio(){
        
        return "inicio.html";
    }
}
