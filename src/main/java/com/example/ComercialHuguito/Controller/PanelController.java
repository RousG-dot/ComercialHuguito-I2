package com.example.ComercialHuguito.Controller;

import com.example.ComercialHuguito.Repository.AppUserRepository;
import com.example.ComercialHuguito.Repository.ClientesRepository;
import com.example.ComercialHuguito.Repository.EmpleadoRepository;
import com.example.ComercialHuguito.Repository.ProductosRepository;
import com.example.ComercialHuguito.Services.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class PanelController {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private ProductosRepository productosRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ClientesRepository clientesRepository;
    @Autowired
    private EstadisticasService estadisticasService;


    @GetMapping("/administrador/paneladministrador")
    public String panelGerente(Model model) {
        // Total productos
        long totalProductos = productosRepository.count();

        // Total empleados
        long totalEmpleados = empleadoRepository.count();

        // Total clientes
        long totalClientes = clientesRepository.count();

        // Productos agregados por semana
        Map<String, Long> productosPorSemana = estadisticasService.obtenerProductosPorSemana();

        // Proveedores agregados por semana
        Map<String, Long> proveedoresPorSemana = estadisticasService.obtenerProveedoresPorSemana();

        // Enviar totales al modelo
        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("totalEmpleados", totalEmpleados);
        model.addAttribute("totalClientes", totalClientes);

        // Enviar gráficos al modelo
        model.addAttribute("productosPorSemana", productosPorSemana);
        model.addAttribute("proveedoresPorSemana", proveedoresPorSemana);

        return "/administrador/paneladministrador"; // Thymeleaf renderizará esta vista
    }


    @GetMapping("/panelcliente")
    public String panelCliente() {
        return "panelcliente"; // panelpaciente.html
    }

}
