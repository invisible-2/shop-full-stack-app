package org.example.backend.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Shop {

    private Integer id;
    private String name;
    private String address;
    private String city;

    public Shop(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }
}
