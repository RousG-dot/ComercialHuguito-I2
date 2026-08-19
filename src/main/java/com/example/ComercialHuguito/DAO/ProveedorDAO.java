package com.example.ComercialHuguito.DAO;

import java.util.Map;

// Interfaz personalizada para definir operaciones específicas relacionadas con los proveedores
public interface ProveedorDAO {

    // Método que contará la cantidad de proveedores registrados por semana.
    // Retorna un Map donde:
    // - La clave (String) representa la semana (por ejemplo, "2025-W23")
    // - El valor (Long) representa la cantidad de proveedores registrados en esa semana.
    Map<String, Long> contarProveedoresPorSemana();
}
