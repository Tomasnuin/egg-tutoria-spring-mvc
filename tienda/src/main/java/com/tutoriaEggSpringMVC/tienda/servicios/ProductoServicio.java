package com.tutoriaEggSpringMVC.tienda.servicios;

import com.tutoriaEggSpringMVC.tienda.entidades.Fabricante;
import com.tutoriaEggSpringMVC.tienda.entidades.Producto;
import com.tutoriaEggSpringMVC.tienda.excepciones.MiException;
import com.tutoriaEggSpringMVC.tienda.repositorios.FabricanteRepositorio;
import com.tutoriaEggSpringMVC.tienda.repositorios.ProductoRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServicio {

    @Autowired
    ProductoRepositorio productoRepositorio;
    @Autowired
    private FabricanteRepositorio fabricanteRepositorio;

    @Transactional
    public void crearProducto(String nombre, Double precio, String idFabricante) throws MiException {

        validar(nombre, precio);

        Optional<Fabricante> respuestaFabricante = fabricanteRepositorio.findById(idFabricante);

        Fabricante fabricante = new Fabricante();

        if (respuestaFabricante.isPresent()) {
            fabricante = respuestaFabricante.get();
        }

        Producto producto = new Producto();

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setFabricante(fabricante);

        productoRepositorio.save(producto);
    }

    public void validar(String nombre, Double precio) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio.");
        }
        if (precio == null) {
            throw new MiException("El precio no puede ser nulo.");
        }
    }

    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList();

        productos = productoRepositorio.findAll();

        return productos;
    }

    public List<Producto> listarProductosPorFabricante(String idFabricante) {
        List<Producto> productos = new ArrayList();

        productos = productoRepositorio.buscarPorFabricante(idFabricante);

        return productos;
    }

    public List<Producto> listarProductosPorPrecioRango(Double precioMin, Double precioMax) {
        List<Producto> productos = new ArrayList();

        productos = productoRepositorio.buscarPorPrecioRango(precioMin, precioMax);

        return productos;
    }

    /**
     * Como no podemos llamar al un repositorio desde el controlador, creamos
     * esta función para llamar a la función del repositorio.
     *
     * @param codigo
     * @return
     */
    public Producto getOne(String codigo) {
        return productoRepositorio.getOne(codigo);
    }

    @Transactional
    public void modificarProducto(String nombre, Double precio, String codigoFabricante, String codigo) throws MiException {

        validar(nombre, precio);

        Optional<Producto> respuesta = productoRepositorio.findById(codigo);
        Optional<Fabricante> respuestaFabricante = fabricanteRepositorio.findById(codigoFabricante);

        Fabricante fabricante = new Fabricante();
        
        if (respuestaFabricante.isPresent()) {
            fabricante = respuestaFabricante.get();
        }
        
        
        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();

            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setFabricante(fabricante);

            productoRepositorio.save(producto);
        }
    }
}
