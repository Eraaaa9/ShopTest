package com.example.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<Product> items = new ArrayList<> ();

    public List<Product> getItems () {
        return items;
    }

    public void addProduct(Product product){
            items.add(product);
    }
}
