package com.example.ComercialHuguito.Controller;

import com.example.ComercialHuguito.Models.Empleados;
import com.example.ComercialHuguito.Repository.EmpleadoRepository;
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
import java.util.List;

// Indica que esta clase es un controlador de Spring MVC que manejará peticiones HTTP
@Controller
// Establece el prefijo de todas las rutas que manejará este controlador
@RequestMapping("/administrador/panelempleados")
public class AdminEmpleadosController {

    // Inyección del repositorio de empleados para acceder a la base de datos
    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Maneja peticiones GET a "/administrador/panelempleados"
    @GetMapping
    public String listarEmpleados(Model model) {
        // Añade a la vista todos los empleados obtenidos de la base de datos
        model.addAttribute("empleados", empleadoRepository.findAll());
        // Devuelve el nombre de la vista HTML para listar empleados
        return "administrador/panelempleados/lista";
    }

    // Maneja peticiones GET a "/nuevo" para mostrar el formulario de nuevo empleado
    @GetMapping("/nuevo")
    public String nuevoEmpleadoForm(Model model) {
        // Crea un objeto vacío de Empleados y lo pasa a la vista
        model.addAttribute("empleado", new Empleados());
        // Devuelve el nombre de la vista del formulario para crear/editar
        return "administrador/panelempleados/formulario";
    }

    // Maneja peticiones GET a "/editar/{id}" para editar un empleado existente
    @GetMapping("/editar/{id}")
    public String editarEmpleado(@PathVariable int id, Model model) {
        // Busca el empleado por ID o lanza una excepción si no existe
        Empleados empleado = empleadoRepository.findById(id).orElseThrow();
        // Añade el empleado encontrado al modelo para mostrarlo en el formulario
        model.addAttribute("empleado", empleado);
        return "administrador/panelempleados/formulario";
    }

    // Maneja peticiones POST a "/guardar" para guardar un nuevo empleado o actualizar uno existente
    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute Empleados empleado) {
        // Cifra la contraseña usando BCrypt antes de guardar
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        empleado.setContrasena(passwordEncoder.encode(empleado.getContrasena()));

        // Guarda el empleado en la base de datos (nuevo o actualizado)
        empleadoRepository.save(empleado);

        // Redirige al listado de empleados
        return "redirect:/administrador/panelempleados";
    }

    // Maneja peticiones GET a "/eliminar/{id}" para eliminar un empleado
    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable int id) {
        // Elimina el empleado por su ID
        empleadoRepository.deleteById(id);

        // Redirige al listado de empleados
        return "redirect:/administrador/panelempleados";
    }

    @GetMapping("/exportar-pdf")
    public void exportarEmpleadosPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=empleados.pdf");

        List<Empleados> empleados = empleadoRepository.findAll(); // Asegúrate de tener este repositorio

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Lista de Empleados").setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("\n"));

        Table table = new Table(10); // 10 columnas
        table.addHeaderCell("ID");
        table.addHeaderCell("Nombres");
        table.addHeaderCell("Apellidos");
        table.addHeaderCell("DNI");
        table.addHeaderCell("Fecha Nacimiento");
        table.addHeaderCell("Correo");
        table.addHeaderCell("Teléfono");
        table.addHeaderCell("Dirección");
        table.addHeaderCell("Cargo");
        table.addHeaderCell("Sueldo");

        for (Empleados e : empleados) {
            table.addCell(String.valueOf(e.getId()));
            table.addCell(e.getNombres());
            table.addCell(e.getApellidos());
            table.addCell(e.getDni());
            table.addCell(e.getFechaNacimiento() != null ? e.getFechaNacimiento().toString() : "");
            table.addCell(e.getCorreo());
            table.addCell(e.getTelefono());
            table.addCell(e.getDireccion());
            table.addCell(e.getCargo());
            table.addCell(String.format("%.2f", e.getSueldo()));
        }

        document.add(table);
        document.close();
    }

    @GetMapping("/exportar-excel")
    public void exportarEmpleadosExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=empleados.xlsx");

        List<Empleados> empleados = empleadoRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Empleados");

        Row header = sheet.createRow(0);
        String[] columnas = {"ID", "Nombres", "Apellidos", "DNI", "Fecha Nacimiento", "Correo", "Teléfono", "Dirección", "Cargo", "Sueldo"};
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        int rowIndex = 1;
        for (Empleados e : empleados) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(e.getId());
            row.createCell(1).setCellValue(e.getNombres());
            row.createCell(2).setCellValue(e.getApellidos());
            row.createCell(3).setCellValue(e.getDni());
            row.createCell(4).setCellValue(e.getFechaNacimiento() != null ? e.getFechaNacimiento().toString() : "");
            row.createCell(5).setCellValue(e.getCorreo());
            row.createCell(6).setCellValue(e.getTelefono());
            row.createCell(7).setCellValue(e.getDireccion());
            row.createCell(8).setCellValue(e.getCargo());
            row.createCell(9).setCellValue(e.getSueldo());
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }


}
