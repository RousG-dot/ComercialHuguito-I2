package com.example.ComercialHuguito.Repository;

import com.example.ComercialHuguito.Models.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {
    @Query("SELECT p FROM Productos p ORDER BY p.fechaRegistro DESC")
    List<Productos> findTop4ByOrderByFechaRegistroDesc();

}
