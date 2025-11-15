package com.example.tienda.model;

public class Product {

    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String imagenUrl;

    // 👉 NUEVO CAMPO PARA PODER FILTRAR
    private String categoria;

    // 👉 ACTUALIZA TU CONSTRUCTOR PARA ACEPTAR CATEGORÍA
    public Product(Long id, String nombre, String descripcion, double precio, String imagenUrl, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.categoria = categoria;
    }

    // GETTERS

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    // 👉 GETTER DE CATEGORÍA (NECESARIO PARA FILTRAR)
    public String getCategoria() {
        return categoria;
    }
}