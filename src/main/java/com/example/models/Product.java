package com.example.models;

import lombok.Data;

@Data
public class Product {
    private int product_id;
    private String name;
    private final int price;
     public Product(int product_id, String name, int price){
         this.product_id = product_id;
         this.name = name;
         this.price = price;
     }

    @Override
    public String toString(){
         String res = "";
         res += name;
         res += " ";
         res += price;
         res += "tg ";
         return res;
    }
}
