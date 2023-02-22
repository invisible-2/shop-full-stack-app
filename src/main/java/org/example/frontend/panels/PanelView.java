package org.example.frontend.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelView extends JPanel {

    private String name;
    public PanelView(String name, JFrame frame) {

        this.name = name;

        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setForeground(Color.white);
        label.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
        add(label);
        setLayout(new CardLayout());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBackground(new Color(102, 179, 255));


        setSize(150, 150);
        setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                label.setForeground(Color.BLACK);
                setBackground(new Color(179, 218, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                label.setForeground(Color.WHITE);
                setBackground(new Color(102, 179, 255));
            }

        });
    }

}
