package com.tutoriaEggSpringMVC.tienda.repositorios;

import com.tutoriaEggSpringMVC.tienda.entidades.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, String> {

    // JQL
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioMin and :precioMax")
    public List<Producto> buscarPorRangoPrecio(@Param("precioMin") Double precioMin, @Param("precioMax") Double precioMax);

    @Query("SELECT p FROM Producto p WHERE p.fabricante.codigo = :codigoFabricante")
    public List<Producto> buscarPorFabricante(@Param("codigoFabricante") String codigoFabricante);

}
