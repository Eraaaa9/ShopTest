package com.example;

import com.example.helpers.CreditCardChecker;
import com.example.helpers.PaginationPrinter;
import com.example.models.Customer;
import com.example.repositories.DataManager;
import com.example.repositories.DataManagerImpl;
import com.example.services.*;

public class Shop {

    public static void main (String[] args) {
        CreditCardChecker creditCardChecker = new CreditCardChecker();
        DataManager dataManager = new DataManagerImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl (creditCardChecker, dataManager);
        PaginationPrinter paginationPrinter = new PaginationPrinter();
        Customer customer = authorizationService.authorizeUser ();
        ProductCatalogService productCatalogService = new ProductCatalogServiceImpl (dataManager);
        ProductListService productListService = new ProductListServiceImpl (paginationPrinter);
        PaymentService paymentService = new PaymentServiceImpl ();
        CartService cartService = new CartServiceImpl (paymentService, paginationPrinter, dataManager);
        AccountHandleService accountHandleService = new AccountHandleServiceImpl (dataManager);
        MainMenu mainMenu = new MainMenu (productListService, productCatalogService, authorizationService,
                accountHandleService, cartService);
        while (true) {
            mainMenu.showMenu(customer);
        }
    }
}
