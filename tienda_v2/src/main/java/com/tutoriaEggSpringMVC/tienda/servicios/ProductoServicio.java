package com.tutoriaEggSpringMVC.tienda.servicios;

import com.tutoriaEggSpringMVC.tienda.entidades.Fabricante;
import com.tutoriaEggSpringMVC.tienda.entidades.Producto;
import com.tutoriaEggSpringMVC.tienda.excepciones.MiException;
import com.tutoriaEggSpringMVC.tienda.repositorios.ProductoRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductoServicio {

    private final ProductoRepositorio productoRepositorio;

    private final FabricanteServicio fabricanteServicio;

    public ProductoServicio(ProductoRepositorio productoRepositorio, FabricanteServicio fabricanteServicio) {
        this.productoRepositorio = productoRepositorio;
        this.fabricanteServicio = fabricanteServicio;
    }

    @Transactional
    public void crearProducto(String nombre, Double precio, String codigoFabricante) throws MiException {

        validar(nombre, precio, codigoFabricante);

        Fabricante respuestaFabricante = fabricanteServicio.findById(codigoFabricante);

        if (respuestaFabricante != null) {
            Producto producto = new Producto();

            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setFabricante(respuestaFabricante);

            productoRepositorio.save(producto);
        } else {
            throw new MiException("Es necesario un fabricante existente.");
        }
    }

    public void validar(String nombre, Double precio, String codigoFabricante) throws MiException {

        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacio.");
        }
        if (precio == null) {
            throw new MiException("El precio no puede ser nulo.");
        }
        if (codigoFabricante.isEmpty() && fabricanteServicio.findById(codigoFabricante) != null) {
            throw new MiException("El precio no puede ser nulo.");
        }
    }

    @Transactional
    public List<Producto> listarProductos() {
        return productoRepositorio.findAll();
    }

    @Transactional
    public List<Producto> listarProductosPorFabricante(String codigoFabricante) {
        return productoRepositorio.buscarPorFabricante(codigoFabricante);
    }

    @Transactional
    public List<Producto> listarProductosPorRangoPrecio(Double precioMin, Double precioMax) throws MiException {
        if (precioMax < precioMin) {
            throw new MiException("El precio minimo no puede ser superior al precio maximo.");
        }

        return productoRepositorio.buscarPorRangoPrecio(precioMin, precioMax);
    }

    @Transactional
    public void modificarProducto(String nombre, Double precio, String codigoFabricante, String codigo) throws MiException {

        validar(nombre, precio, codigoFabricante);

        Producto respuesta = findById(codigo);
        Fabricante respuestaFabricante = fabricanteServicio.findById(codigoFabricante);

        if (respuestaFabricante != null && respuesta != null) {
            Producto producto = respuesta;

            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setFabricante(respuestaFabricante);

            productoRepositorio.save(producto);
        } else {
            throw new MiException("Es necesario un fabricante y/o un producto existentes.");
        }
    }

    @Transactional
    public Producto findById(String codigo) {

        Optional<Producto> respuesta = productoRepositorio.findById(codigo);

        if (respuesta.isPresent()) {

            return respuesta.get();

        } else {

            return null;

        }
    }
}
