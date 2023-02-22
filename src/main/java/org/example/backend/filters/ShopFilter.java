package org.example.backend.filters;

import org.example.backend.models.Shop;
import org.example.backend.service.ShopService;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class ShopFilter {
    private final ShopService shopService;

    public ShopFilter(ShopService shopService) {
        this.shopService = shopService;
    }

    public List<Shop> sortByName(boolean asc) throws SQLException {

        List<Shop> shops = shopService.findAll()
                .stream()
                .sorted(Comparator.comparing(Shop::getName))
                .toList();

        return asc ? shops : shops
                .stream()
                .sorted(Comparator.comparing(Shop::getName).reversed())
                .toList();
    }
}
