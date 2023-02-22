package org.example.frontend.tables;

import org.example.backend.models.Product;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductTable {
    private final List<Product> products;
    private DefaultTableModel model;

    public ProductTable(List<Product> products) {
        this.products = products;
    }

    public DefaultTableModel generateTableModel() {

        model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Price");
        model.addColumn("Mass");
        model.addColumn("Unit");
        model.addColumn("Expiration Date");

        products.forEach(this::addRow);
        return model;
    }

    public void addRow(Product product) {
        model.addRow(new Object[]{
                product.getName(),
                product.getPrice(),
                product.getMass(),
                product.getMassType(),
                product.getExpirationDate()
        });
    }
}
