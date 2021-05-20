package com.example.models;

import com.example.models.Product;
import java.util.ArrayList;
public class ProductsList {
    private ArrayList<Product> products;
    private String name;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
