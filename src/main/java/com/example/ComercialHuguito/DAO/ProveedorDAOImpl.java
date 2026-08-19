package com.example.ComercialHuguito.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Indica que esta clase es un componente de tipo repositorio, gestionado por Spring
@Repository
public class ProveedorDAOImpl implements ProveedorDAO {

    // Inyección del EntityManager, que permite interactuar directamente con la base de datos usando JPQL
    @PersistenceContext
    private EntityManager entityManager;

    // Implementación del método que cuenta la cantidad de proveedores registrados por semana
    @Override
    public Map<String, Long> contarProveedoresPorSemana() {

        // Consulta JPQL que agrupa los proveedores por la semana del año en que fueron registrados
        // YEARWEEK es una función de MySQL que devuelve el año y la semana en formato YYYYWW
        String jpql = "SELECT FUNCTION('YEARWEEK', p.fechaRegistro), COUNT(p) FROM Proveedor p GROUP BY FUNCTION('YEARWEEK', p.fechaRegistro)";

        // Ejecuta la consulta JPQL y devuelve los resultados como una lista de arreglos de objetos
        // Cada arreglo contiene: [número de semana, cantidad de proveedores en esa semana]
        List<Object[]> resultados = entityManager.createQuery(jpql).getResultList();

        // Se crea un mapa ordenado para almacenar los resultados con formato legible
        Map<String, Long> datos = new LinkedHashMap<>();

        // Itera sobre cada resultado (una fila)
        for (Object[] fila : resultados) {
            // Convierte el valor de la semana a String
            String semana = String.valueOf(fila[0]);

            // Convierte el valor de la cantidad a Long
            Long cantidad = (Long) fila[1];

            // Agrega el resultado al mapa con clave "Semana {número}" y valor la cantidad
            datos.put("Semana " + semana, cantidad);
        }

        // Retorna el mapa con los datos contados por semana
        return datos;
    }
}
