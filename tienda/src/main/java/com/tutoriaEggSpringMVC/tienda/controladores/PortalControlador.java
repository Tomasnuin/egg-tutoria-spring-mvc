package com.tutoriaEggSpringMVC.tienda.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class PortalControlador {
    
    @GetMapping("")
    public String inicio(){
        
        return "inicio.html";
    }
}
