package org.example.backend.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    private Integer id;
    private String name;
    private Double price;
    private Double mass;
    private String massType;
    private Date expirationDate;

    public Product(String name, Double price, Double mass, String massType, Date expirationDate) {
        this.name = name;
        this.price = price;
        this.mass = mass;
        this.massType = massType;
        this.expirationDate = expirationDate;
    }
}
