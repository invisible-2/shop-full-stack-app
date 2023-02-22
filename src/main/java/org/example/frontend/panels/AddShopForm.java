package org.example.frontend.panels;

import org.example.backend.models.Shop;
import org.example.backend.service.ShopService;
import org.example.frontend.tables.ShopTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

public class AddShopForm extends JFrame {

    private final JTable table;
    private final DefaultTableModel model;
    private final ShopTable shopTable;

    private Shop shop;

    public AddShopForm(JTable table, DefaultTableModel model, ShopTable shopTable) {
        super("Add Shop Form");
        this.table = table;
        this.model = model;
        this.shopTable = shopTable;

        Container content = getContentPane();
        content.setPreferredSize(new Dimension(400, 300));
        content.setLayout(new GridBagLayout());
        content.setBackground(Color.WHITE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Shop name label
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(nameLabel, constraints);

        // Shop name text field
        JTextField nameField = new JTextField(20);
        constraints.gridx = 1;
        content.add(nameField, constraints);


        // Shop Address label
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(addressLabel, constraints);

        // Shop address text field
        JTextField addressField = new JTextField(20);
        constraints.gridx = 1;
        content.add(addressField, constraints);


        // Shop Address label
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel cityLabel = new JLabel("City");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(cityLabel, constraints);

        // Shop city text field
        JTextField cityField = new JTextField(20);
        constraints.gridx = 1;
        content.add(cityField, constraints);

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(0, 179, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;

        addButton.addActionListener(event -> {
            String name = nameField.getText();
            String address = addressField.getText();
            String city = cityField.getText();

            if (!name.equals("") && !address.equals("") && !city.equals("")) {

                try {
                    shop = new Shop(name, address, city);
                    ShopService shopService = new ShopService();

                    if (shopService.findByName(shop.getName()).isPresent()) {

                        if (shopService.findByAddress(shop.getAddress()).isPresent()) {

                            if (shopService.findByAddressAndCity(shop.getAddress(), shop.getCity()).isPresent()) {
                                JOptionPane.showMessageDialog(this,
                                        new StringBuilder().append("In '")
                                                .append(shop.getCity())
                                                .append("' at address '")
                                                .append(shop.getAddress())
                                                .append("' already exist a shop"),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                shopService.save(shop);
                                nameField.setText("");
                                addressField.setText("");
                                cityField.setText("");
                                JOptionPane.showMessageDialog(this, "Adding with success.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            shopService.save(shop);
                            nameField.setText("");
                            addressField.setText("");
                            cityField.setText("");
                            JOptionPane.showMessageDialog(this, "Adding with success.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } else {
                        if (shopService.findByAddressAndCity(shop.getAddress(), shop.getCity()).isPresent()) {
                            JOptionPane.showMessageDialog(this,
                                    new StringBuilder().append("In '")
                                            .append(shop.getCity())
                                            .append("' at address '")
                                            .append(shop.getAddress())
                                            .append("' already exist a shop"),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            shopService.save(shop);
                            nameField.setText("");
                            addressField.setText("");
                            cityField.setText("");
                            JOptionPane.showMessageDialog(this, "Adding with success.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }


                } catch (SQLException | IOException | ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(this, "Database errors!!", "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }

        });

        content.add(addButton, constraints);
        pack();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    model.setRowCount(0);
                    new ShopService().findAll().forEach(shopTable::addRow);

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
