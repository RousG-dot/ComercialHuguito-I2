package com.example.ComercialHuguito.Controller;

import com.example.ComercialHuguito.Models.Productos;
import com.example.ComercialHuguito.Repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class InicioController {
    @Autowired
    private ProductosRepository productosRepository;

    @GetMapping({"", "/"})
    public String home(Model model) {
        List<Productos> productos = productosRepository.findTop4ByOrderByFechaRegistroDesc();
        model.addAttribute("productos", productos);
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/contacto")
    public String contact(){
        return "contacto";
    }
    @GetMapping("/servicios")
    public String servicios(Model model){
        List<Productos> productos = productosRepository.findAll();
        model.addAttribute("productos", productos);
        return "servicios";
    }
    @GetMapping("/nosotros")
    public String nosotros(){
        return "nosotros";
    }
}
