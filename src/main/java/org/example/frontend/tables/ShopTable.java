package org.example.frontend.tables;

import org.example.backend.models.Shop;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ShopTable {

    private final List<Shop> shops;
    private DefaultTableModel model;

    public ShopTable(List<Shop> shops) {
        this.shops = shops;
    }

    public DefaultTableModel generateTableModel() {

        model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Address");
        model.addColumn("City");

        shops.forEach(this::addRow);
        return model;
    }

    public void addRow(Shop shop) {
        model.addRow(new Object[]{
                shop.getName(),
                shop.getAddress(),
                shop.getCity()
        });
    }
}
