package com.example.models;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private final int price;
     public Product(String name, int price){
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
