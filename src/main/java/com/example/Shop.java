package com.example;

import com.example.models.Customer;
import com.example.services.*;

public class Shop {

    public static void main (String[] args) {
        AuthorizationService authorizationService = new AuthorizationServiceImpl ();
        Customer customer = authorizationService.authorizeUser ();
        ProductCatalogService productCatalogService = new ProductCatalogServiceImpl ();
        ProductListService productListService = new ProductListServiceImpl ();
        PaymentService paymentService = new PaymentServiceImpl ();
        CartService cartService = new CartServiceImpl (paymentService);
        AccountHandleService accountHandleService = new AccountHandleServiceImpl ();
        MainMenu mainMenu = new MainMenu (productListService, productCatalogService, authorizationService, accountHandleService, cartService);
        while (true) {
            mainMenu.showMenu (customer);
        }
    }
}
