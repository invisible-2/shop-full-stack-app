package org.example.backend.service;

import org.example.backend.models.Shop;
import org.example.backend.util.ConnectionDB;
import org.example.backend.util.Parser;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopService {
    private final Connection connection;

    public ShopService() throws SQLException, IOException, ClassNotFoundException {
        this.connection = ConnectionDB.connect();
    }

    public List<Shop> findAll() throws SQLException {
        List<Shop> shops = new ArrayList<>();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM shop");
        while (resultSet.next())
            shops.add(Parser.parseToShop(resultSet));
        statement.close();

        return shops;
    }

    public Shop findById(Integer id) throws SQLException {
        Shop shop = null;
        PreparedStatement statement = connection.prepareStatement("select * from shop where id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next())
            shop = Parser.parseToShop(resultSet);
        statement.close();

        return shop;
    }

    public Optional<Shop> findByName(String name) throws SQLException {
        Shop shop = null;
        PreparedStatement statement = connection.prepareStatement("select * from shop where name=?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next())
            shop = Parser.parseToShop(resultSet);
        statement.close();

        return Optional.ofNullable(shop);
    }

    public Optional<Shop> findByAddress(String address) throws SQLException {
        Shop shop = null;
        PreparedStatement statement = connection.prepareStatement("select * from shop where address=?");
        statement.setString(1, address);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next())
            shop = Parser.parseToShop(resultSet);
        statement.close();

        return Optional.ofNullable(shop);
    }


    public Optional<Shop> findByAddressAndCity(String address, String city) throws SQLException {
        Shop shop = null;
        PreparedStatement statement = connection.prepareStatement("select * from shop where address=? and city=?");
        statement.setString(1, address);
        statement.setString(2, city);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next())
            shop = Parser.parseToShop(resultSet);
        statement.close();

        return Optional.ofNullable(shop);
    }

    public void save(Shop shop) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "insert into shop(name, address, city) VALUES (?, ?, ?)");
        statement.setString(1, shop.getName());
        statement.setString(2, shop.getAddress());
        statement.setString(3, shop.getCity());

        statement.executeUpdate();
        statement.close();
    }

}
