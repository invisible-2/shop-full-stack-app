package org.example.frontend.frames;

import org.example.backend.filters.ShopFilter;
import org.example.backend.service.ShopService;
import org.example.frontend.panels.AddShopForm;
import org.example.frontend.tables.ShopTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class ShopsFrame extends JFrame {

    private ShopService shopService = new ShopService();
    private ShopTable shopTable = new ShopTable(shopService.findAll());
    private DefaultTableModel model = shopTable.generateTableModel();
    private JTable table = new JTable(model);

    private final JButton addShoptBtn;
    private final JButton updateShopBtn;
    private final JButton deleteShopBtn;

    private final JButton sortByNameBtn;

    public ShopsFrame() throws SQLException, IOException, ClassNotFoundException {
        super("Shops");

        JScrollPane tableContainer = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        addShoptBtn = new JButton("Add Shop");
        updateShopBtn = new JButton("Update Shop");
        deleteShopBtn = new JButton("Delete Shop");
        sortByNameBtn = new JButton("Sort Asc By Name");

        addShoptBtn.setBackground(new Color(0, 179, 0));
        addShoptBtn.setForeground(Color.WHITE);
        addShoptBtn.setFocusPainted(false);

        updateShopBtn.setBackground(new Color(204, 163, 0));
        updateShopBtn.setForeground(Color.WHITE);
        updateShopBtn.setFocusPainted(false);

        deleteShopBtn.setBackground(new Color(255, 71, 26));
        deleteShopBtn.setForeground(Color.WHITE);
        deleteShopBtn.setFocusPainted(false);

        sortByNameBtn.setFocusPainted(false);
        sortByNameBtn.setToolTipText("Sort By Name");

        setOnAddButtonClicked();
        setOnSortByNameButtonClicked();

        panel.add(addShoptBtn);
        panel.add(updateShopBtn);
        panel.add(deleteShopBtn);
        panel.add(tableContainer);
        panel.add(sortByNameBtn);


        getContentPane().add(panel);
        pack();
        setSize(650, 500);
        setResizable(false);

    }



    private void setOnAddButtonClicked() {
        addShoptBtn.addActionListener(addBtnEvent -> {
            new AddShopForm(table, model, shopTable).setVisible(true);
        });
    }

    private void setOnSortByNameButtonClicked() {
        sortByNameBtn.addActionListener(event -> {
            ShopFilter shopFilter = new ShopFilter(shopService);

            try {
                if (sortByNameBtn.getText().equalsIgnoreCase("Sort Asc By Name")) {
                    model.setRowCount(0);
                    shopFilter.sortByName(true).forEach(shopTable::addRow);
                    sortByNameBtn.setText("Sort Desc By Name");
                } else if (sortByNameBtn.getText().equalsIgnoreCase("Sort Desc By Name")) {
                    model.setRowCount(0);
                    shopFilter.sortByName(false).forEach(shopTable::addRow);
                    sortByNameBtn.setText("Sort Asc By Name");
                }

                table.revalidate();
                table.repaint();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
