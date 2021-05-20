package com.example.services;

import com.example.helpers.IntInput;
import com.example.models.ProductCatalog;
import com.example.models.ProductsList;
import com.example.repositories.DataManager;

public class ProductCatalogServiceImpl implements ProductCatalogService {
    private final DataManager dataManager;

    public ProductCatalogServiceImpl(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public ProductsList showCatalog() {
        ProductCatalog catalog = dataManager.fill();
        System.out.println("Choose a category (number or name)");
        System.out.println(catalog);
        int categoryInput = IntInput.readInput();
        return catalog.getCategory().get(categoryInput-1);
    }
}
