package com.example.ComercialHuguito.Models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "empleados") // Esta anotación indica que esta clase se mapeará a la tabla "empleados" en la base de datos
public class Empleados {

    @Id // Marca este campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El valor se generará automáticamente por la base de datos
    private int id; // Identificador único del empleado

    private String nombres; // Nombres del empleado
    private String apellidos; // Apellidos del empleado
    private String dni; // Documento Nacional de Identidad del empleado
    private LocalDate fechaNacimiento; // Fecha de nacimiento del empleado
    private String correo; // Correo electrónico del empleado
    private String contrasena; // Contraseña para acceder al sistema (debe almacenarse de forma segura)
    private String telefono; // Número de teléfono del empleado
    private String direccion; // Dirección física del empleado
    private String cargo; // Cargo o puesto que ocupa el empleado
    private double sueldo; // Sueldo que percibe el empleado
    private LocalDate fechaContratacion; // Fecha en la que fue contratado el empleado

    // Métodos getters y setters permiten acceder y modificar los valores de estos campos
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
}
