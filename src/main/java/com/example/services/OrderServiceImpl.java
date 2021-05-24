package com.example.services;

import com.example.models.Customer;
import com.example.repositories.DataManager;

public class OrderServiceImpl implements OrderService {
    private final DataManager dataManager;

    public OrderServiceImpl(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void createOrder(Customer customer){
        dataManager.createOrder(customer);
    }
}
