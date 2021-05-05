package com.example.services;

import com.example.repositories.ProductCatalog;
import com.example.repositories.ProductsList;

public interface ProductCatalogService {
    ProductCatalog fill();
    ProductsList showCatalog();
}
