package com.example.repositories;

import java.util.ArrayList;

public class ProductCatalog {
    private ArrayList<ProductsList> category;

    public ArrayList<ProductsList> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<ProductsList> category) {
        this.category = category;
    }

    public ProductCatalog(){
        category = new ArrayList<>();
    }

    public void addList(ProductsList list){
        category.add(list);
    }

    public String toString(){
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < category.size(); i++){
            res.append((i + 1));
            res.append(".");
            res.append(category.get(i).getName());
            res.append(" ");
        }
        return String.valueOf(res);
    }
}
