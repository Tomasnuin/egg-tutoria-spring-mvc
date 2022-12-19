package com.tutoriaEggSpringMVC.tienda.servicios;

import com.tutoriaEggSpringMVC.tienda.entidades.Fabricante;
import com.tutoriaEggSpringMVC.tienda.excepciones.MiException;
import com.tutoriaEggSpringMVC.tienda.repositorios.FabricanteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FabricanteServicio {

    @Autowired
    private FabricanteRepositorio fabricanteRepositorio;

    @Transactional
    public void crearFabricante(String nombre) throws MiException {

        validar(nombre);

        Fabricante fabricante = new Fabricante();
        fabricante.setNombre(nombre);

        fabricanteRepositorio.save(fabricante);
    }
        
    private void validar(String nombre) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio.");
        }
    }

    public List<Fabricante> listarFabricantes() {
        List<Fabricante> fabricantes = new ArrayList();

        fabricantes = fabricanteRepositorio.findAll();

        return fabricantes;
    }

    @Transactional
    public void modificarFabricante(String nombre, String id) throws MiException {

        validar(nombre);

        Optional<Fabricante> respuesta = fabricanteRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Fabricante fabricante = respuesta.get();

            fabricante.setNombre(nombre);

            fabricanteRepositorio.save(fabricante);
        }
    }

    /**
     * Como no podemos llamar al un repositorio desde el controlador, creamos
     * esta función para llamar a la función del repositorio.
     *
     * @param codigo El id de el fabricante.
     * @return
     */
    public Fabricante getOne(String codigo) {
        return fabricanteRepositorio.getOne(codigo);
    }
}
