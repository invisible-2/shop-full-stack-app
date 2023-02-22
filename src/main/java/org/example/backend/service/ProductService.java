package org.example.backend.service;

import org.example.backend.models.Product;
import org.example.backend.util.ConnectionDB;
import org.example.backend.util.Parser;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {

    private final Connection connection;

    public ProductService() throws SQLException, IOException, ClassNotFoundException {
        this.connection = ConnectionDB.connect();
    }

    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM product");
        while (resultSet.next())
            products.add(Parser.parseToProduct(resultSet));
        statement.close();

        return products;
    }

    public Product findById(Integer id) throws SQLException {
        Product product = null;
        PreparedStatement statement = connection.prepareStatement("select * from product where id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next())
            product = Parser.parseToProduct(resultSet);
        statement.close();

        return product;
    }

    public List<Product> findByShopId(Integer shopId) throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from product where shop_id = ?");
        statement.setInt(1, shopId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next())
            products.add(Parser.parseToProduct(resultSet));
        statement.close();

        return products;
    }

    public Optional<Product> findByNameAndPrice(String name, Double price) throws SQLException {
        Product product = null;
        PreparedStatement statement = connection.prepareStatement("select * from product where name=? and price=?");
        statement.setString(1, name);
        statement.setDouble(2, price);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next())
            product = Parser.parseToProduct(resultSet);
        statement.close();

        return Optional.ofNullable(product);
    }

    public void save(Product product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "insert into product(name, price, mass, mass_type, expiration_date) VALUES (?, ?, ?, ?, ?)"
        );
        statement.setString(1, product.getName());
        statement.setDouble(2, product.getPrice());
        statement.setDouble(3, product.getMass());
        statement.setString(4, product.getMassType());

        statement.setDate(5, new Date(product.getExpirationDate().getTime()));

        statement.executeUpdate();
        statement.close();
    }

    public void update(Product updatedProduct) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "update product set name=?, price=?, mass=?, mass_type=?, expiration_date=? where product.id=?");
        statement.setString(1, updatedProduct.getName());
        statement.setDouble(2, updatedProduct.getPrice());
        statement.setDouble(3, updatedProduct.getMass());
        statement.setString(4, updatedProduct.getMassType());
        statement.setDate(5, new Date(updatedProduct.getExpirationDate().getTime()));
        statement.setInt(6, updatedProduct.getId());

        statement.executeUpdate();
        statement.close();
    }

    public void deleteById(Integer id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "delete from product where id=?"
        );
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();

    }

}
