package com.example.ComercialHuguito.Controller;

import com.example.ComercialHuguito.Models.Proveedor;
import com.example.ComercialHuguito.Repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/empleados/vistaproveedores")
public class EmpleadosProveedorController {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorRepository.findAll());
        return "empleados/vistaproveedores/lista"; // Vista para empleados
    }

    @GetMapping("/nuevo")
    public String nuevoProveedorForm(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "empleados/vistaproveedores/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarProveedor(@PathVariable int id, Model model) {
        Proveedor proveedor = proveedorRepository.findById(id).orElseThrow();
        model.addAttribute("proveedor", proveedor);
        return "empleados/vistaproveedores/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@ModelAttribute Proveedor proveedor) {
        proveedorRepository.save(proveedor);
        return "redirect:/empleados/vistaproveedores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable int id) {
        proveedorRepository.deleteById(id);
        return "redirect:/empleados/vistaproveedores";
    }
}
