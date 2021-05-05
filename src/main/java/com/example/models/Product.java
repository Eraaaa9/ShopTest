package com.example.models;

public class Product {
    private String name;
    private final int price;
     public Product(String name, int price){
         this.name = name;
         this.price = price;
     }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
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
