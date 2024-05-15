package com.example.testandroid.Entities;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products;

    public Cart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                p.setQuantity(p.getQuantity() + 1);
                return;
            }
        }
        product.setQuantity(1);
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public double getTotalCost() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice() * product.getQuantity(); // multiply by quantity
        }
        return total;
    }

    public List<Product> getProducts() {
        return products;
    }
}