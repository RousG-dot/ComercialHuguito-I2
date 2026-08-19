package com.example.ComercialHuguito.Controller;

import com.example.ComercialHuguito.Models.Proveedor;
import com.example.ComercialHuguito.Repository.ProveedorRepository;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Controller
// Define la ruta base de todas las peticiones manejadas por este controlador
@RequestMapping("/administrador/panelproveedores")
public class AdminProveedorController {

    // Inyecta el repositorio de proveedores para acceder a la base de datos
    @Autowired
    private ProveedorRepository proveedorRepository;

    // Maneja las peticiones GET a "/administrador/panelproveedores"
    @GetMapping
    public String listarProveedores(Model model) {
        // Agrega al modelo todos los proveedores obtenidos de la base de datos
        model.addAttribute("proveedores", proveedorRepository.findAll());

        // Retorna el nombre de la vista que muestra la lista de proveedores
        return "administrador/panelproveedores/lista";
    }

    // Muestra el formulario para registrar un nuevo proveedor
    @GetMapping("/nuevo")
    public String nuevoProveedorForm(Model model) {
        // Agrega un nuevo objeto Proveedor vacío al modelo para llenar el formulario
        model.addAttribute("proveedor", new Proveedor());

        // Retorna el nombre de la vista del formulario
        return "administrador/panelproveedores/formulario";
    }

    // Muestra el formulario para editar un proveedor existente
    @GetMapping("/editar/{id}")
    public String editarProveedor(@PathVariable int id, Model model) {
        // Busca el proveedor por ID. Si no se encuentra, lanza una excepción
        Proveedor proveedor = proveedorRepository.findById(id).orElseThrow();

        // Agrega el proveedor encontrado al modelo
        model.addAttribute("proveedor", proveedor);

        // Retorna el nombre de la vista del formulario, reutilizado para editar
        return "administrador/panelproveedores/formulario";
    }

    // Guarda los datos del proveedor (nuevo o editado)
    @PostMapping("/guardar")
    public String guardarProveedor(@ModelAttribute Proveedor proveedor) {
        // Guarda el proveedor en la base de datos
        proveedorRepository.save(proveedor);

        // Redirige al listado de proveedores después de guardar
        return "redirect:/administrador/panelproveedores";
    }

    // Elimina un proveedor por su ID
    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable int id) {
        // Elimina el proveedor de la base de datos por su ID
        proveedorRepository.deleteById(id);

        // Redirige al listado de proveedores después de eliminar
        return "redirect:/administrador/panelproveedores";
    }

    @GetMapping("/exportar-pdf")
    public void exportarPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=proveedores.pdf");

        List<Proveedor> proveedores = proveedorRepository.findAll();

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Lista de Proveedores").setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("\n"));

        Table table = new Table(7);
        table.addHeaderCell("ID");
        table.addHeaderCell("Empresa");
        table.addHeaderCell("RUC");
        table.addHeaderCell("Dirección");
        table.addHeaderCell("Teléfono");
        table.addHeaderCell("Gerente");
        table.addHeaderCell("DNI Gerente");

        for (Proveedor p : proveedores) {
            table.addCell(String.valueOf(p.getId()));
            table.addCell(p.getNombreEmpresa());
            table.addCell(p.getRuc());
            table.addCell(p.getDireccion());
            table.addCell(p.getTelefono());
            table.addCell(p.getNombreGerente());
            table.addCell(p.getDniGerente());
        }

        document.add(table);
        document.close();
    }

    @GetMapping("/exportar-excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=proveedores.xlsx");

        List<Proveedor> proveedores = proveedorRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Proveedores");

        Row header = sheet.createRow(0);
        String[] columnas = {"ID", "Empresa", "RUC", "Dirección", "Teléfono", "Gerente", "DNI Gerente"};
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        int rowIndex = 1;
        for (Proveedor p : proveedores) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getNombreEmpresa());
            row.createCell(2).setCellValue(p.getRuc());
            row.createCell(3).setCellValue(p.getDireccion());
            row.createCell(4).setCellValue(p.getTelefono());
            row.createCell(5).setCellValue(p.getNombreGerente());
            row.createCell(6).setCellValue(p.getDniGerente());
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }


}

