package com.tutoriaEggSpringMVC.tienda.repositorios;

import com.tutoriaEggSpringMVC.tienda.entidades.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FabricanteRepositorio extends JpaRepository<Fabricante, String>{
    
}
