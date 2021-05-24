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

    @Override
    public ProductsList showCatalog() {
        ProductCatalog catalog = dataManager.fillCatalogWithCategories();
        System.out.println("Choose a category: ");
        int page = 1;
        printCatalog(catalog, page);
        int categoryInput = IntInput.readInput();
        return catalog.getCategory().get(categoryInput-1);
    }

    private void printCatalog(ProductCatalog catalog, int page) {

    }
}
