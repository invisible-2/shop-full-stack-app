package org.example;

import org.example.frontend.frames.PrincipalFrame;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        new PrincipalFrame();
    }
}