package com.tutoriaEggSpringMVC.tienda.servicios;

import com.tutoriaEggSpringMVC.tienda.entidades.Fabricante;
import com.tutoriaEggSpringMVC.tienda.excepciones.MiException;
import com.tutoriaEggSpringMVC.tienda.repositorios.FabricanteRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class FabricanteServicio {

    private final FabricanteRepositorio fabricanteRepositorio;

    public FabricanteServicio(FabricanteRepositorio fabricanteRepositorio) {
        this.fabricanteRepositorio = fabricanteRepositorio;
    }

    @Transactional
    public void crearFabricante(String nombre) throws MiException {

        validar(nombre);

        Fabricante fabricante = new Fabricante();
        fabricante.setNombre(nombre);

        fabricanteRepositorio.save(fabricante);
    }

    private void validar(String nombre) throws MiException {

        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacio.");
        }
    }

    @Transactional
    public List<Fabricante> listarFabricantes() {
        return fabricanteRepositorio.findAll();
    }

    @Transactional
    public void modificarFabricante(String nombre, String codigo) throws MiException {

        validar(nombre);

        Fabricante respuesta = findById(codigo);

        if (respuesta != null) {
            Fabricante fabricante = respuesta;

            fabricante.setNombre(nombre);

            fabricanteRepositorio.save(fabricante);
        } else {
            throw new MiException("Es necesario un fabricante existente.");
        }
    }

    /**
     * Como no podemos llamar al un repositorio desde el controlador, creamos
     * esta función para llamar a la función del repositorio.
     *
     * @param codigo El id de el fabricante.
     * @return
     */
    @Transactional
    public Fabricante findById(String codigo) {

        Optional<Fabricante> respuestaFabricante = fabricanteRepositorio.findById(codigo);

        if (respuestaFabricante.isPresent()) {

            return respuestaFabricante.get();

        } else {

            return null;

        }
    }
}
