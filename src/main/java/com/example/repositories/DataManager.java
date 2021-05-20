package com.example.repositories;

import com.example.models.Customer;
import com.example.models.ProductCatalog;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataManager {
    Connection connect() throws SQLException;
    void updateBalance(Customer customer, int amount);
    ProductCatalog fill();
    void fillCatalog(ProductCatalog productCatalog);
}
