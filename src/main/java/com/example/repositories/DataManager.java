package com.example.repositories;

import com.example.models.Customer;
import com.example.models.Product;
import com.example.models.ProductCatalog;

public interface DataManager {
    void updateBalance(Customer customer, int amount);
    ProductCatalog fillCatalogWithCategories();
    void fillCatalogWithProducts(ProductCatalog productCatalog);
    void removeFromCart(Product product, Customer customer);
    void addToCart(Product product, Customer customer);
    Customer loginInDatabase(String name, String password);
    int addUserToDatabase(String name, String password);
    void updateCreditCardNumber(Customer customer, String creditCard);

    void createOrder(Customer customer);
}
