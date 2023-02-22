package org.example.frontend.frames;

import org.example.frontend.panels.PanelView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class PrincipalFrame extends JFrame {

    public PrincipalFrame() throws SQLException, IOException, ClassNotFoundException {
        super("Shop");

        add(new PanelView("Products", new ProductsFrame()));
        add(new PanelView("Shops", new ShopsFrame()));

        setLayout(new GridLayout(2, 3, 15, 15));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.white);
        setSize(600, 450);
        setVisible(true);
    }

    @Override
    public Insets getInsets() {
        return new Insets(60, 50, 50, 50);
    }
}
