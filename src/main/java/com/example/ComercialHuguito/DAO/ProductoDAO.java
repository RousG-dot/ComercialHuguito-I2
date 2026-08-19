package com.example.ComercialHuguito.DAO;

import java.util.Map;

// Interfaz personalizada para operaciones específicas con productos
public interface ProductoDAO {

    // Método que contará la cantidad de productos registrados por semana
    // Retorna un Map donde:
    // - La clave (String) podría ser una representación de la semana (por ejemplo, "2025-W22")
    // - El valor (Long) representa cuántos productos fueron registrados esa semana
    Map<String, Long> contarProductosPorSemana();
}