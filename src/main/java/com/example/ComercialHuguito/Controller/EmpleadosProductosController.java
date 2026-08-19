package com.example.ComercialHuguito.Controller;

import com.example.ComercialHuguito.Models.Productos;
import com.example.ComercialHuguito.Repository.ProductosRepository;
import com.example.ComercialHuguito.Repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/empleados/vistaproductos")
public class EmpleadosProductosController {

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productosRepository.findAll());
        return "empleados/vistaproductos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("productos", new Productos());
        model.addAttribute("proveedores", proveedorRepository.findAll());
        return "empleados/vistaproductos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable int id, Model model) {
        Productos productos = productosRepository.findById(id).orElseThrow();
        model.addAttribute("productos", productos);
        model.addAttribute("proveedores", proveedorRepository.findAll());
        return "empleados/vistaproductos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Productos productos,
                                  @RequestParam("imagenFile") MultipartFile imagenFile) {
        if (!imagenFile.isEmpty()) {
            try {
                String nombreArchivo = UUID.randomUUID().toString() + "_" + imagenFile.getOriginalFilename();
                Path ruta = Paths.get("uploads/" + nombreArchivo);
                Files.write(ruta, imagenFile.getBytes());
                productos.setImagen(nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productos.setFechaRegistro(new Date());
        productosRepository.save(productos);
        return "redirect:/empleados/vistaproductos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable int id) {
        productosRepository.deleteById(id);
        return "redirect:/empleados/vistaproductos";
    }
}
