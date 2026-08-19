package com.example.ComercialHuguito.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "productos")
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private int cantidad;
    private double precio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    private String imagen; // guardaremos solo el nombre del archivo
    @ManyToOne
    @JoinColumn(name = "proveedor_id") // FK en la tabla medicamentos
    private Proveedor proveedor;
    // Getters y Setters

    public Integer getId()              {       return id;    }
    public void setId(Integer id)       {       this.id = id;    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
