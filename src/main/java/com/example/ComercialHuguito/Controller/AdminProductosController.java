package com.example.ComercialHuguito.Controller;

import com.example.ComercialHuguito.Models.Productos;
import com.example.ComercialHuguito.Models.Proveedor;
import com.example.ComercialHuguito.Repository.ProductosRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;


// Indica que esta clase es un controlador de Spring MVC
@Controller
// Define la URL base para las rutas de este controlador
@RequestMapping("/administrador/panelproductos")
public class AdminProductosController {

    // Inyecta el repositorio de productos para acceder a la base de datos
    @Autowired
    private ProductosRepository productosRepository;

    // Inyecta el repositorio de proveedores para obtener la lista de proveedores
    @Autowired
    private ProveedorRepository proveedorRepository;

    // Maneja peticiones GET a "/administrador/panelproductos"
    @GetMapping
    public String listarMedicamentos(Model model) {
        // Agrega al modelo todos los productos registrados en la base de datos
        model.addAttribute("productos", productosRepository.findAll());

        // Devuelve el nombre de la vista que lista los productos
        return "administrador/panelproductos/lista"; // Vista HTML
    }

    // Muestra el formulario para registrar un nuevo producto
    @GetMapping("/nuevo")
    public String nuevoProductoForm(Model model) {
        // Crea un nuevo objeto Productos vacío
        model.addAttribute("productos", new Productos());

        // Agrega la lista de proveedores al modelo para llenar un combo en el formulario
        model.addAttribute("proveedores", proveedorRepository.findAll());

        // Devuelve el nombre de la vista del formulario
        return "administrador/panelproductos/formulario";
    }

    // Muestra el formulario de edición de un producto existente
    @GetMapping("/editar/{id}")
    public String editarProductos(@PathVariable int id, Model model) {
        // Busca el producto por ID, o lanza una excepción si no se encuentra
        Productos productos = productosRepository.findById(id).orElseThrow();

        // Agrega el producto al modelo (usando nombre 'medicamento' en lugar de 'productos')
        model.addAttribute("productos", productos);

        // Agrega la lista de proveedores para seleccionar en el formulario
        model.addAttribute("proveedores", proveedorRepository.findAll());

        // Devuelve la vista del formulario para editar
        return "administrador/panelproductos/formulario";
    }

    // Guarda el producto nuevo o editado, incluyendo carga de imagen
    @PostMapping("/guardar")
    public String guardarProductos(@ModelAttribute Productos productos,
                                   @RequestParam("imagenFile") MultipartFile imagenFile) {
        if (!imagenFile.isEmpty()) {
            try {
                String originalFilename = imagenFile.getOriginalFilename();
                if (originalFilename != null &&
                        (originalFilename.endsWith(".jpg") ||
                                originalFilename.endsWith(".jpeg") ||
                                originalFilename.endsWith(".png") ||
                                originalFilename.endsWith(".webp"))) {

                    String nombreArchivo = UUID.randomUUID().toString() + "_" + originalFilename;
                    Path ruta = Paths.get("uploads/" + nombreArchivo);
                    Files.write(ruta, imagenFile.getBytes());

                    // Cambiar imagen solo si se sube una nueva
                    productos.setImagen(nombreArchivo);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Si no se subió nueva imagen, conserva la existente
            if (productos.getId() != null) {
                Productos productoExistente = productosRepository.findById(productos.getId()).orElse(null);
                if (productoExistente != null) {
                    productos.setImagen(productoExistente.getImagen());
                }
            }
        }

        productosRepository.save(productos);
        return "redirect:/administrador/panelproductos";
    }


    // Elimina un producto por su ID
    @GetMapping("/eliminar/{id}")
    public String eliminarProductos(@PathVariable int id) {
        // Elimina el producto de la base de datos
        productosRepository.deleteById(id);

        // Redirige al listado de productos
        return "redirect:/administrador/panelproductos";
    }

    @GetMapping("/exportar-pdf")
    public void exportarProductosPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=productos.pdf");

        List<Productos> productos = productosRepository.findAll(); // Asegúrate de tener este repositorio

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Lista de Productos").setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("\n"));

        Table table = new Table(7);
        table.addHeaderCell("ID");
        table.addHeaderCell("Nombre");
        table.addHeaderCell("Descripción");
        table.addHeaderCell("Cantidad");
        table.addHeaderCell("Precio");
        table.addHeaderCell("Fecha Registro");
        table.addHeaderCell("Proveedor");

        for (Productos p : productos) {
            table.addCell(String.valueOf(p.getId()));
            table.addCell(p.getNombre());
            table.addCell(p.getDescripcion());
            table.addCell(String.valueOf(p.getCantidad()));
            table.addCell(String.format("%.2f", p.getPrecio()));
            table.addCell(p.getFechaRegistro() != null ? p.getFechaRegistro().toString() : "");
            table.addCell(p.getProveedor() != null ? p.getProveedor().getNombreEmpresa() : "N/A");
        }

        document.add(table);
        document.close();
    }

    @GetMapping("/exportar-excel")
    public void exportarProductosExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=productos.xlsx");

        List<Productos> productos = productosRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Productos");

        Row header = sheet.createRow(0);
        String[] columnas = {"ID", "Nombre", "Descripción", "Cantidad", "Precio", "Fecha Registro", "Proveedor"};
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        int rowIndex = 1;
        for (Productos p : productos) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getNombre());
            row.createCell(2).setCellValue(p.getDescripcion());
            row.createCell(3).setCellValue(p.getCantidad());
            row.createCell(4).setCellValue(p.getPrecio());
            row.createCell(5).setCellValue(p.getFechaRegistro() != null ? p.getFechaRegistro().toString() : "");
            row.createCell(6).setCellValue(p.getProveedor() != null ? p.getProveedor().getNombreEmpresa() : "N/A");
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
