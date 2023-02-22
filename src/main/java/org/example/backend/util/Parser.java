package org.example.backend.util;

import org.example.backend.models.Product;
import org.example.backend.models.Shop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Parser {

    public static Shop parseToShop(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String city = resultSet.getString("city");

        return new Shop(id, name, address, city);
    }

    public static Product parseToProduct(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Double price = resultSet.getDouble("price");
        Double mass = resultSet.getDouble("mass");
        String massType = resultSet.getString("mass_type");
        Date expirationDate = resultSet.getDate("expiration_date");

        return new Product(id, name, price, mass, massType, expirationDate);
    }

}
