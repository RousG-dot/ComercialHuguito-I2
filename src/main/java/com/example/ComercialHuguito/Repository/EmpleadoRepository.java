package com.example.ComercialHuguito.Repository;

import com.example.ComercialHuguito.Models.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Esta interfaz sirve para acceder a la base de datos de la entidad Empleados
public interface EmpleadoRepository extends JpaRepository<Empleados, Integer> {

    // Método personalizado que busca un empleado por su correo electrónico
    // Retorna un objeto Empleados si encuentra uno con ese correo
    Empleados findByCorreo(String correo);

    // Método que retorna una lista de empleados que tienen un cargo específico
    List<Empleados> findByCargo(String cargo);

    // Verifica si existe un empleado con un correo específico
    // Retorna true si existe, false si no
    boolean existsByCorreo(String correo);
}
