package com.tutoriaEggSpringMVC.tienda.controladores;

import com.tutoriaEggSpringMVC.tienda.entidades.Fabricante;
import com.tutoriaEggSpringMVC.tienda.entidades.Producto;
import com.tutoriaEggSpringMVC.tienda.excepciones.MiException;
import com.tutoriaEggSpringMVC.tienda.servicios.FabricanteServicio;
import com.tutoriaEggSpringMVC.tienda.servicios.ProductoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producto")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;
    @Autowired
    private FabricanteServicio fabricanteServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Fabricante> fabricantes = fabricanteServicio.listarFabricantes();

        modelo.addAttribute("fabricantes", fabricantes);

        return "producto_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam(required = false) Double precio,
            @RequestParam String codigoFabricante, ModelMap modelo) {

        try {

            productoServicio.crearProducto(nombre, precio, codigoFabricante);
            modelo.put("exito", "El Producto fue creado correctamente.");
        } catch (MiException e) {
            List<Fabricante> fabricantes = fabricanteServicio.listarFabricantes();

            modelo.addAttribute("fabricantes", fabricantes);

            modelo.addAttribute("nombre", nombre);
            modelo.addAttribute("precio", precio);

            modelo.put("error", e.getMessage());
            return "producto_form.html";
        }

        return "inicio.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Producto> productos = productoServicio.listarProductos();

        modelo.addAttribute("productos", productos);

        return "producto_lista.html";
    }

    /**
     * Uso esta función para seleccionar el rango del precio de la lista.
     *
     * @return
     */
    @GetMapping("/lista/precio/seleccionar")
    public String listaPrecioSeleccionar() {

        return "producto_lista_precio_min_max_seleccionar.html";
    }

    @PostMapping("/lista/precio")
    public String listaPrecioRango(@RequestParam Double precioMin, @RequestParam Double precioMax,
            ModelMap modelo) {
        try {
            List<Producto> productos = productoServicio.listarProductosPorRangoPrecio(precioMin, precioMax);

            modelo.addAttribute("productos", productos);
            return "producto_lista.html";

        } catch (Exception e) {

            modelo.put("error", e.getMessage());
            return "producto_lista_precio_min_max_seleccionar.html";
        }

    }

    @GetMapping("/modificar/{codigo}")
    public String modificar(@PathVariable String codigo, ModelMap modelo) {
        List<Fabricante> fabricantes = fabricanteServicio.listarFabricantes();
        modelo.addAttribute("fabricantes", fabricantes);

        modelo.put("producto", productoServicio.findById(codigo));
        return "producto_modificar.html";
    }

    @PostMapping("{codigo}")
    public String modificar(@PathVariable String codigo, String nombre, Double precio, String codigoFabricante, ModelMap modelo,
            RedirectAttributes redirect) {
        try {

            productoServicio.modificarProducto(nombre, precio, codigoFabricante, codigo);
            redirect.addFlashAttribute("exito", "El producto ha sido modificado con exito.");
            return "redirect:./lista";

        } catch (MiException ex) {
            List<Fabricante> fabricantes = fabricanteServicio.listarFabricantes();
            modelo.addAttribute("fabricantes", fabricantes);

            modelo.put("producto", productoServicio.findById(codigo));
            modelo.put("error", ex.getMessage());
            return "producto_modificar.html";
        }
    }
}
