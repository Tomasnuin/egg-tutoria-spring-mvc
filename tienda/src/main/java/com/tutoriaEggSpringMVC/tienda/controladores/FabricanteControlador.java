package com.tutoriaEggSpringMVC.tienda.controladores;

import com.tutoriaEggSpringMVC.tienda.entidades.Fabricante;
import com.tutoriaEggSpringMVC.tienda.excepciones.MiException;
import com.tutoriaEggSpringMVC.tienda.servicios.FabricanteServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/fabricante")
public class FabricanteControlador {

    @Autowired
    private FabricanteServicio fabricanteServicio;

    @GetMapping("/registrar")
    public String registrar() {

        return "fabricante_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {

            fabricanteServicio.crearFabricante(nombre);
            modelo.put("exito", "El Fabricante fue creado correctamente.");
        } catch (MiException e) {

            modelo.put("error", e.getMessage());
            return "fabricante_form.html";
        }

        return "inicio.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Fabricante> fabricantes = fabricanteServicio.listarFabricantes();

        modelo.addAttribute("fabricantes", fabricantes);

        return "fabricante_lista.html";
    }

    @GetMapping("/modificar/{codigo}")
    public String modificar(@PathVariable String codigo, ModelMap modelo) {

        modelo.put("fabricante", fabricanteServicio.getOne(codigo));
        return "fabricante_modificar.html";
    }

    @PostMapping("{codigo}")
    public String modificar(@PathVariable String codigo, String nombre, ModelMap modelo) {
        try {
            fabricanteServicio.modificarFabricante(nombre, codigo);
            //     "redirect:../lista" me llevaba a localhost:8080/lista
            return "redirect:./lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "fabricante_modificar.html";
        }
    }
}
