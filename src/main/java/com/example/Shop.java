package com.example;

import com.example.models.Customer;
import com.example.repositories.DataManager;
import com.example.repositories.DataManagerImpl;
import com.example.services.*;

public class Shop {

    public static void main (String[] args) {
        AuthorizationService authorizationService = new AuthorizationServiceImpl ();
        DataManager dataManager = new DataManagerImpl();
        Customer customer = authorizationService.authorizeUser ();
        ProductCatalogService productCatalogService = new ProductCatalogServiceImpl (dataManager);
        ProductListService productListService = new ProductListServiceImpl ();
        PaymentService paymentService = new PaymentServiceImpl ();
        CartService cartService = new CartServiceImpl (paymentService);
        AccountHandleService accountHandleService = new AccountHandleServiceImpl (dataManager);
        MainMenu mainMenu = new MainMenu (productListService, productCatalogService, authorizationService,
                accountHandleService, cartService);
        while (true) {
            mainMenu.showMenu (customer);
        }
    }
}
