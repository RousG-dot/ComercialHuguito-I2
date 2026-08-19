package com.example.ComercialHuguito.Controller;

import com.example.ComercialHuguito.Models.Clientes;
import com.example.ComercialHuguito.Repository.ClientesRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/administrador/panelclientes")
public class AdminClientesController {

    @Autowired
    private ClientesRepository clientesRepository;

    // Muestra la lista de todos los clientes
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clientesRepository.findAll());
        return "administrador/panelclientes/lista";
    }

    // Muestra el formulario para crear un nuevo cliente
    @GetMapping("/nuevo")
    public String nuevoClienteForm(Model model) {
        model.addAttribute("cliente", new Clientes());
        return "administrador/panelclientes/formulario";
    }

    // Muestra el formulario para editar un cliente existente
    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable int id, Model model) {
        Clientes cliente = clientesRepository.findById(id).orElseThrow();
        model.addAttribute("cliente", cliente);
        return "administrador/panelclientes/formulario";
    }

    // Guarda un cliente nuevo o editado
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Clientes cliente) {
        // Si es un nuevo cliente (ID == 0), le asignamos rol y fecha
        if (cliente.getId() == 0) {
            cliente.setRole("cliente");
            cliente.setCreatedAt(new Date());

            // Opcional: encriptar contraseña si deseas hacerlo
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        }

        clientesRepository.save(cliente);
        return "redirect:/administrador/panelclientes";
    }

    // Elimina un cliente por ID
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable int id) {
        clientesRepository.deleteById(id);
        return "redirect:/administrador/panelclientes";
    }

    @GetMapping("/clientes/exportar-pdf")
    public void exportarClientesPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=clientes.pdf");

        List<Clientes> clientes = clientesRepository.findAll(); // Asegúrate de tener este repositorio

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Lista de Clientes").setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("\n"));

        Table table = new Table(8);
        table.addHeaderCell("ID");
        table.addHeaderCell("Nombre");
        table.addHeaderCell("Apellido");
        table.addHeaderCell("Correo");
        table.addHeaderCell("Teléfono");
        table.addHeaderCell("Dirección");
        table.addHeaderCell("Rol");
        table.addHeaderCell("Creado el");

        for (Clientes c : clientes) {
            table.addCell(String.valueOf(c.getId()));
            table.addCell(c.getFirstName());
            table.addCell(c.getLastName());
            table.addCell(c.getEmail());
            table.addCell(c.getPhone());
            table.addCell(c.getAddress());
            table.addCell(c.getRole());
            table.addCell(c.getCreatedAt() != null ? c.getCreatedAt().toString() : "");
        }

        document.add(table);
        document.close();
    }

    @GetMapping("/clientes/exportar-excel")
    public void exportarClientesExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=clientes.xlsx");

        List<Clientes> clientes = clientesRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Clientes");

        Row header = sheet.createRow(0);
        String[] columnas = {"ID", "Nombre", "Apellido", "Correo", "Teléfono", "Dirección", "Rol", "Creado el"};
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        int rowIndex = 1;
        for (Clientes c : clientes) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(c.getId());
            row.createCell(1).setCellValue(c.getFirstName());
            row.createCell(2).setCellValue(c.getLastName());
            row.createCell(3).setCellValue(c.getEmail());
            row.createCell(4).setCellValue(c.getPhone());
            row.createCell(5).setCellValue(c.getAddress());
            row.createCell(6).setCellValue(c.getRole());
            row.createCell(7).setCellValue(c.getCreatedAt() != null ? c.getCreatedAt().toString() : "");
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}

