package org.example.frontend.panels;

import org.example.backend.models.Product;
import org.example.backend.service.ProductService;
import org.example.frontend.tables.ProductTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateProductForm extends JFrame {

    private final Product product;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final ProductTable productTable;

    public UpdateProductForm(Product product, JTable table, DefaultTableModel tableModel, ProductTable productTable) {
        super("Update Product Form");
        this.product = product;
        this.table = table;
        this.tableModel = tableModel;
        this.productTable = productTable;

        Container content = getContentPane();
        content.setPreferredSize(new Dimension(400, 300));
        content.setLayout(new GridBagLayout());
        content.setBackground(Color.WHITE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Product name label
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(nameLabel, constraints);


        // Product name text field
        JTextField nameField = new JTextField(product.getName(),20);
        constraints.gridx = 1;
        content.add(nameField, constraints);

        // Price label
        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel priceLabel = new JLabel("Price");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(priceLabel, constraints);

        // Price text field
        JTextField priceField = new JTextField(product.getPrice().toString(),20);
        constraints.gridx = 1;
        content.add(priceField, constraints);

        // Mass label
        constraints.gridx = 0;
        constraints.gridy = 2;
        JLabel massLabel = new JLabel("Mass");
        massLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(massLabel, constraints);

        // Mass text field
        JTextField massField = new JTextField(product.getMass().toString(), 20);
        constraints.gridx = 1;
        content.add(massField, constraints);

        //Mass Type/ Units
        constraints.gridx = 0;
        constraints.gridy = 3;
        JLabel label = new JLabel("Unit");
        massLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(label, constraints);

        JComboBox<String> comboBox = new JComboBox<>(new String[]{"KG", "g", "mg", "L", "ml", "buc."});
        constraints.gridx = 1;
        comboBox.setSelectedItem(product.getMassType());
        content.add(comboBox, constraints);


        JButton updateButton = new JButton("Update");
        updateButton.setBackground(new Color(204, 163, 0));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;

        updateButton.addActionListener(event -> {
            String name = nameField.getText();
            String priceString = priceField.getText();
            String massString = massField.getText();
            String massType = (String) comboBox.getSelectedItem();

            try {
                double price = Double.parseDouble(priceString);
                double mass = Double.parseDouble(massString);

                product.setName(name);
                product.setPrice(price);
                product.setMass(mass);
                product.setMassType(massType);

                ProductService productService = new ProductService();

                int res = JOptionPane.showConfirmDialog(this, "You want to save update ? ", "", JOptionPane.YES_NO_OPTION);

                if (res == JOptionPane.YES_OPTION){
                    if (productService.findByNameAndPrice(name, price).isEmpty()) {
                        productService.update(product);
                        repaintTable();
                        dispose();
                    }  else JOptionPane.showMessageDialog(this, "This product already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price or mass format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid expiration date.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        content.add(updateButton, constraints);
        pack();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                repaintTable();
                e.getWindow().dispose();
            }
        });

    }

    private void repaintTable() {
        try {
            tableModel.setRowCount(0);
            new ProductService().findAll().forEach(productTable::addRow);

            table.revalidate();
            table.repaint();

        } catch (SQLException | IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
