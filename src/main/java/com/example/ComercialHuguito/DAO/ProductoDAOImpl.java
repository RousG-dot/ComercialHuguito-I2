package com.example.ComercialHuguito.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
// Clase que implementa la interfaz ProductoDAO
public class ProductoDAOImpl implements ProductoDAO {

    // Inyecta el EntityManager, que se usa para ejecutar consultas JPQL o SQL nativas
    @PersistenceContext
    private EntityManager entityManager;

    // Implementación del método que cuenta la cantidad de productos registrados por semana
    @Override
    public Map<String, Long> contarProductosPorSemana() {

        // Consulta JPQL que agrupa productos por semana de registro usando la función YEARWEEK
        // 'p.fechaRegistro' se refiere a la fecha de registro del producto
        String jpql = "SELECT FUNCTION('YEARWEEK', p.fechaRegistro), COUNT(p) FROM Productos p GROUP BY FUNCTION('YEARWEEK', p.fechaRegistro)";

        // Ejecuta la consulta y obtiene los resultados como una lista de arreglos de objetos
        // Cada fila contiene dos elementos: el número de semana (YEARWEEK) y la cantidad
        List<Object[]> resultados = entityManager.createQuery(jpql).getResultList();

        // Se crea un mapa ordenado (LinkedHashMap) para guardar los resultados formateados
        Map<String, Long> datos = new LinkedHashMap<>();

        // Itera sobre cada fila del resultado de la consulta
        for (Object[] fila : resultados) {
            // Convierte el número de semana a String
            String semana = String.valueOf(fila[0]);

            // Extrae la cantidad de productos (cast a Long)
            Long cantidad = (Long) fila[1];

            // Inserta el dato en el mapa, usando como clave "Semana {numero}" y el valor como cantidad
            datos.put("Semana " + semana, cantidad);
        }

        // Retorna el mapa con los resultados agrupados por semana
        return datos;
    }
}
