package com.example.tienda.service;

import com.example.tienda.model.CartItem;
import com.example.tienda.model.Product;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CartService {

    // Mapa: idProducto -> CartItem
    private Map<Long, CartItem> items = new LinkedHashMap<>();

    public void addProduct(Product product) {
        CartItem item = items.get(product.getId());
        if (item == null) {
            items.put(product.getId(), new CartItem(product, 1));
        } else {
            item.setCantidad(item.getCantidad() + 1);
        }
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public double getTotal() {
        return items.values().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public void clear() {
        items.clear();
    }

    public int getCantidadTotal() {
        return items.values().stream()
                .mapToInt(CartItem::getCantidad)
                .sum();
    }
}