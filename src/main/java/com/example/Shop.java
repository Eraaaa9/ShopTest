package com.example;

import com.example.models.Customer;
import com.example.services.*;

public class Shop {

    public static void main (String[] args) {
        ProductCatalogService productCatalogService = new ProductCatalogServiceImpl ();
        ProductListService productListService = new ProductListServiceImpl ();
        AuthorizationService authorizationService = new AuthorizationServiceImpl ();
        AccountHandleService accountHandleService = new AccountHandleServiceImpl ();
        PaymentService paymentService = new PaymentServiceImpl ();
        CartService cartService = new CartServiceImpl (paymentService);
        Customer customer = authorizationService.authorizeUser ();
        MainMenu mainMenu = new MainMenu (productListService, productCatalogService, authorizationService, accountHandleService, cartService);
        while (true) {
            mainMenu.showMenu (customer);
        }
    }
}
