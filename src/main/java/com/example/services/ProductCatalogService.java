package com.example.services;

import com.example.models.ProductCatalog;
import com.example.models.ProductsList;

public interface ProductCatalogService {
    ProductCatalog fill();
    ProductsList showCatalog();
}
