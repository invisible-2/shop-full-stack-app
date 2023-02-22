package org.example.frontend.panels;

import net.sourceforge.jdatepicker.impl.DateComponentFormatter;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
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
import java.text.SimpleDateFormat;

public class AddProductForm extends JFrame {

    private Product product;

    private final JTable table;
    private final DefaultTableModel tableModel;
    private final ProductTable productTable;

    public AddProductForm(JTable table, DefaultTableModel tableModel, ProductTable productTable) {
        super("Add Product Form");
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
        JTextField nameField = new JTextField(20);
        constraints.gridx = 1;
        content.add(nameField, constraints);

        // Price label
        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel priceLabel = new JLabel("Price");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(priceLabel, constraints);

        // Price text field
        JTextField priceField = new JTextField(20);
        constraints.gridx = 1;
        content.add(priceField, constraints);

        // Mass label
        constraints.gridx = 0;
        constraints.gridy = 2;
        JLabel massLabel = new JLabel("Mass");
        massLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(massLabel, constraints);

        // Mass text field
        JTextField massField = new JTextField(20);
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
        content.add(comboBox, constraints);

        // Expiration date label
        constraints.gridx = 0;
        constraints.gridy = 4;
        JLabel expirationLabel = new JLabel("Expiration Date");
        expirationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(expirationLabel, constraints);


        // Expiration date text field
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        constraints.gridx = 1;
        content.add(datePicker, constraints);

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(0, 179, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;

        addButton.addActionListener(event -> {
            String name = nameField.getText();
            String priceString = priceField.getText();
            String massString = massField.getText();
            String massType = (String) comboBox.getSelectedItem();
            String expirationDateString = datePicker.getJFormattedTextField().getText();

            try {
                double price = Double.parseDouble(priceString);
                double mass = Double.parseDouble(massString);

                product = new Product(name, price, mass, massType, new SimpleDateFormat("dd MMM yyyy").parse(expirationDateString));

                ProductService productService = new ProductService();
                if (productService.findByNameAndPrice(product.getName(), product.getPrice()).isEmpty()) {
                    productService.save(product);
                    JOptionPane.showMessageDialog(this, "Adding with success.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                    priceField.setText("");
                    massField.setText("");
                    comboBox.setSelectedIndex(0);
                    datePicker.getJFormattedTextField().setText("");

                } else {
                    JOptionPane.showMessageDialog(this, "This product already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }


            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price or mass format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid expiration date.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        content.add(addButton, constraints);
        pack();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    try {
                        tableModel.setRowCount(0);
                        new ProductService().findAll().forEach(productTable::addRow);

                        table.revalidate();
                        table.repaint();

                    } catch (SQLException | IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                e.getWindow().dispose();
            }
        });

    }

}
