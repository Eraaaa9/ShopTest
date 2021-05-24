package com.example.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Cart {

    private int customer_id;
    private final List<Product> items = new ArrayList<> ();

    public void addProduct(Product product){
            items.add(product);
    }
}
