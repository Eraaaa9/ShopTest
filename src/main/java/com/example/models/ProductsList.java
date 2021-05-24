package com.example.models;

import lombok.Data;

import java.util.ArrayList;
@Data
public class ProductsList {
    private ArrayList<Product> products;
    private String name;

    public ProductsList(String name) {
        this();
        this.name = name;
    }
    public ProductsList(){
        products = new ArrayList<>();
        name = "";
    }
    public void addProduct(Product product){
        products.add(product);
    }
}
