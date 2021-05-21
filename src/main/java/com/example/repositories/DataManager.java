package com.example.repositories;

import com.example.models.Customer;
import com.example.models.Product;
import com.example.models.ProductCatalog;

public interface DataManager {
    void updateBalance(Customer customer, int amount);
    ProductCatalog fill();
    void fillCatalog(ProductCatalog productCatalog);

    void removeFromCart(Product product, Customer customer);
    Customer loginInDatabase(String name, String password);
    void addUserToDatabase(String name, String password, String cardNumber);
}
