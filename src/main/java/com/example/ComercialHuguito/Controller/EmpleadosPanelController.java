package com.example.ComercialHuguito.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empleados")
public class EmpleadosPanelController {

    @GetMapping("/vistaempleados")
    public String mostrarVistaEmpleado() {
        return "empleados/vistaempleados"; // Asegúrate que está en templates/empleados/vistaempleados.html
    }
}
