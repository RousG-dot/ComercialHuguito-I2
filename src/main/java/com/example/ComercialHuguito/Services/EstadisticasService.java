package com.example.ComercialHuguito.Services;

import com.example.ComercialHuguito.DAO.ProductoDAO;
import com.example.ComercialHuguito.DAO.ProveedorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

// Marca esta clase como un servicio de Spring, lo que permite que sea detectada y gestionada por el contenedor
@Service
public class EstadisticasService {

    // Inyecta la dependencia de ProductoDAO para acceder a los datos de productos
    @Autowired
    private ProductoDAO productoDAO;

    // Inyecta la dependencia de ProveedorDAO para acceder a los datos de proveedores
    @Autowired
    private ProveedorDAO proveedorDAO;

    // Método público que retorna un mapa con la cantidad de productos registrados por semana
    public Map<String, Long> obtenerProductosPorSemana() {
        // Llama al método del DAO que realiza la consulta a la base de datos
        return productoDAO.contarProductosPorSemana();
    }

    // Método público que retorna un mapa con la cantidad de proveedores registrados por semana
    public Map<String, Long> obtenerProveedoresPorSemana() {
        // Llama al método del DAO que realiza la consulta a la base de datos
        return proveedorDAO.contarProveedoresPorSemana();
    }
}
