package org.example.backend.filters;

import org.example.backend.models.Product;
import org.example.backend.service.ProductService;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class ProductFilter {
    private final ProductService productService;

    public ProductFilter(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> sortByPrice(boolean asc) throws SQLException {
        List<Product> products = productService.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .toList();

        return asc ? products : products
                .stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .toList();
    }
}
