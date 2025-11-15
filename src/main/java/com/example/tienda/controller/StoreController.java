package com.example.tienda.controller;

import com.example.tienda.model.Product;
import com.example.tienda.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StoreController {

    private final CartService cartService;

    // Lista de productos HARDCODEADA (prendas básicas)
    private final List<Product> products = Arrays.asList(

            // === CAMISETAS ===
            new Product(1L, "Camiseta básica blanca", "Camiseta unisex 100% algodón",
                    35000, "/img/camiseta-blanca.png", "camisetas"),
            new Product(2L, "Camiseta negra clásica", "Camiseta algodón premium",
                    36000, "/img/camiseta-negra.png", "camisetas"),
            new Product(3L, "Camiseta beige minimalista", "Ideal para outfits básicos",
                    37000, "/img/camiseta-beige.png", "camisetas"),
            new Product(4L, "Camiseta azul navy", "Corte regular fit",
                    35000, "/img/camiseta-azul.png", "camisetas"),

            // === JEANS ===
            new Product(5L, "Jean azul clásico", "Jean tiro alto, corte recto, azul intenso",
                    90000, "/img/jean-azul.png", "jeans"),
            new Product(6L, "Jean negro skinny", "Ajustado, tela stretch",
                    95000, "/img/jean-negro.png", "jeans"),
            new Product(7L, "Jean claro recto", "Tono lavado claro",
                    92000, "/img/jean-claro.png", "jeans"),
            new Product(8L, "Jean vintage", "Estilo retro desgastado",
                    97000, "/img/jean-vintage.jpg", "jeans"),

            // === BUZOS ===
            new Product(9L, "Buzo negro oversize", "Buzo cómodo para uso diario",
                    75000, "/img/buzo-negro.png", "buzos"),
            new Product(10L, "Buzo gris claro", "Material suave, oversize",
                    76000, "/img/buzo-gris.png", "buzos"),
            new Product(11L, "Buzo beige cálido", "Ideal clima frío",
                    78000, "/img/buzo-beige.png", "buzos"),
            new Product(12L, "Buzo azul oscuro", "Estilo clásico",
                    77000, "/img/buzo-azul.png", "buzos"),

            // === SUDADERAS ===
            new Product(13L, "Sudadera gris", "Sudadera liviana para outfits básicos",
                    65000, "/img/sudadera-gris.png", "sudaderas"),
            new Product(14L, "Sudadera negra", "Material suave premium",
                    68000, "/img/sudadera-negra.png", "sudaderas"),
            new Product(15L, "Sudadera azul navy", "Corte regular fit",
                    69000, "/img/sudadera-azul.png", "sudaderas"),
            new Product(16L, "Sudadera beige", "Minimalista y cómoda",
                    70000, "/img/sudadera-beige.png", "sudaderas")
    );

    public StoreController(CartService cartService) {
        this.cartService = cartService;
    }

    // INICIO: solo muestra la portada (sin productos)
    @GetMapping("/")
    public String index(Model model) {
        // NO enviamos lista de productos -> la vista muestra portada.png
        model.addAttribute("cantidadCarrito", cartService.getCantidadTotal());
        model.addAttribute("productos", null);
        model.addAttribute("tituloCategoria", "PRENDAS BÁSICAS");
        return "index";
    }

    // Filtrar por categoría: /categoria/camisetas, /categoria/jeans, etc.
    @GetMapping("/categoria/{nombre}")
    public String productosPorCategoria(@PathVariable String nombre, Model model) {
        List<Product> filtrados = products.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());

        model.addAttribute("productos", filtrados);
        model.addAttribute("cantidadCarrito", cartService.getCantidadTotal());
        model.addAttribute("tituloCategoria", nombre.toUpperCase());
        return "index";
    }

    // Búsqueda: /buscar?q=algo
    @GetMapping("/buscar")
    public String buscar(@RequestParam("q") String q, Model model) {
        String texto = q.toLowerCase();

        List<Product> resultados = products.stream()
                .filter(p ->
                        p.getNombre().toLowerCase().contains(texto) ||
                        p.getDescripcion().toLowerCase().contains(texto) ||
                        p.getCategoria().toLowerCase().contains(texto))
                .collect(Collectors.toList());

        model.addAttribute("productos", resultados);
        model.addAttribute("cantidadCarrito", cartService.getCantidadTotal());
        model.addAttribute("tituloCategoria", "RESULTADOS");
        return "index";
    }

    // Agregar producto al carrito
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productId") Long productId) {
        products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .ifPresent(cartService::addProduct);

        return "redirect:/";
    }

    // Finalizar compra (demo): muestra resumen y limpia el carrito
    @PostMapping("/checkout")
    public String checkout(Model model) {
        // Pasamos los datos actuales del carrito a la vista
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());

        // Aquí podrías guardar la orden en BD, enviar correo, etc.
        // Por ahora solo limpiamos el carrito
        cartService.clear();

        return "checkout";
    }

    // Ver carrito
    @GetMapping("/carrito")
    public String verCarrito(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "carrito";
    }

    // Vaciar carrito
    @PostMapping("/vaciar-carrito")
    public String vaciarCarrito() {
        cartService.clear();
        return "redirect:/carrito";
    }
}