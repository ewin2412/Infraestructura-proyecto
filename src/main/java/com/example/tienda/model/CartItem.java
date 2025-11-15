package com.example.tienda.model;

public class CartItem {

    private Product product;
    private int cantidad;

    public CartItem(Product product, int cantidad) {
        this.product = product;
        this.cantidad = cantidad;
    }

    public Product getProduct() {
        return product;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return product.getPrecio() * cantidad;
    }
}