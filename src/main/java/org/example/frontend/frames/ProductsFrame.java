package org.example.frontend.frames;

import org.example.backend.filters.ProductFilter;
import org.example.backend.models.Product;
import org.example.backend.service.ProductService;
import org.example.frontend.panels.AddProductForm;
import org.example.frontend.tables.ProductTable;
import org.example.frontend.panels.UpdateProductForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ProductsFrame extends JFrame {

    private ProductService productService = new ProductService();
    private ProductTable productTable = new ProductTable(productService.findAll());
    private DefaultTableModel model = productTable.generateTableModel();
    private JTable table = new JTable(model);
    private final JButton addProductBtn;
    private final JButton updateProductBtn;
    private final JButton deleteProductBtn;
    private final JButton sortBtn;


    public ProductsFrame() throws SQLException, IOException, ClassNotFoundException {
        super("Products");

        JScrollPane tableContainer = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        addProductBtn = new JButton("Add Product");
        updateProductBtn = new JButton("Update Product");
        deleteProductBtn = new JButton("Delete Product");
        sortBtn = new JButton("Sort Asc");

        addProductBtn.setBackground(new Color(0, 179, 0));
        addProductBtn.setForeground(Color.WHITE);
        addProductBtn.setFocusPainted(false);

        updateProductBtn.setBackground(new Color(204, 163, 0));
        updateProductBtn.setForeground(Color.WHITE);
        updateProductBtn.setFocusPainted(false);

        deleteProductBtn.setBackground(new Color(255, 71, 26));
        deleteProductBtn.setForeground(Color.WHITE);
        deleteProductBtn.setFocusPainted(false);

        sortBtn.setFocusPainted(false);
        sortBtn.setToolTipText("Sort By Price");

        setOnAddButtonClicked();
        setOnUpdateButtonClicked();
        setOnDeleteButtonClicked();

        setOnSortButtonClicked();

        panel.add(addProductBtn);
        panel.add(updateProductBtn);
        panel.add(deleteProductBtn);
        panel.add(tableContainer);
        panel.add(sortBtn);

        getContentPane().add(panel);
        pack();
        setSize(600, 500);
        setResizable(false);
    }

    private void setOnAddButtonClicked() {
        addProductBtn.addActionListener(addBtnEvent -> {
            new AddProductForm(table, model, productTable).setVisible(true);
        });
    }
    private void setOnUpdateButtonClicked() {
        updateProductBtn.addActionListener(addBtnEvent -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {

                try {
                    String name = (String) table.getValueAt(selectedRow, 0);
                    Double price = (Double) table.getValueAt(selectedRow, 1);
                    Optional<Product> product = productService.findByNameAndPrice(name, price);

                    new UpdateProductForm(product.get(), table, model, productTable).setVisible(true);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private void setOnDeleteButtonClicked() {

        deleteProductBtn.addActionListener(deleteBtnEvent -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {

                try {
                    String name = (String) table.getValueAt(selectedRow, 0);
                    Double price = (Double) table.getValueAt(selectedRow, 1);
                    Optional<Product> product = productService.findByNameAndPrice(name, price);

                    int res = JOptionPane.showConfirmDialog(this, "You what do delete product [name: " + name + ", price: " + price + "]", "Error", JOptionPane.YES_NO_OPTION);

                    if (res == JOptionPane.YES_OPTION) {
                        productService.deleteById(product.get().getId());
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private void setOnSortButtonClicked() {
        sortBtn.addActionListener(sortBtnEvent -> {
            ProductFilter productFilter = new ProductFilter(productService);

            try {
                if (sortBtn.getText().equals("Sort Asc")) {
                    model.setRowCount(0);
                    productFilter.sortByPrice(true).forEach(productTable::addRow);
                    sortBtn.setText("Sort Desc");

                } else if (sortBtn.getText().equals("Sort Desc")) {
                    model.setRowCount(0);
                    productFilter.sortByPrice(false).forEach(productTable::addRow);
                    sortBtn.setText("Sort Asc");
                }

                table.revalidate();
                table.repaint();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
