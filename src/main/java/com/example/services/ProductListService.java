package com.example.services;

import com.example.models.Customer;
import com.example.models.Product;
import com.example.repositories.ProductsList;

public interface ProductListService {
    void showGoods(ProductsList productsList, Customer customer, int page);
    void addToCart(Product product, Customer customer);
}
