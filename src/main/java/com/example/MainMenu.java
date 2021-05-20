package com.example;

import com.example.helpers.IntInput;
import com.example.models.Customer;
import com.example.models.ProductsList;
import com.example.services.*;

public class MainMenu {
    private final ProductCatalogService productCatalogService;
    private final ProductListService productListService;
    private final AuthorizationService authorizationService;
    private final AccountHandleService accountHandleService;
    private final CartService cartService;

    public MainMenu (ProductListService productListService, ProductCatalogService productCatalogService,
                     AuthorizationService authorizationService, AccountHandleService accountHandleService,
                     CartService cartService) {
        this.productListService = productListService;
        this.productCatalogService = productCatalogService;
        this.authorizationService = authorizationService;
        this.accountHandleService = accountHandleService;
        this.cartService = cartService;
    }

    public void showMenu (Customer customer) {
        System.out.print ("1.Shop ");
        System.out.print ("2.Account ");
        System.out.print ("3.Cart ");
        System.out.println ("4.Logout ");
        int input = IntInput.readInput ();
        switch (input) {
            case 1:
                ProductsList productsList = productCatalogService.showCatalog ();
                productListService.showGoods (productsList, customer, 1);
                break;
            case 2:
                accountHandleService.handle (customer);
                break;
            case 3:
                cartService.showCart (customer);
                break;
            case 4:
                authorizationService.authorizeUser ();
                break;
            default:
                showMenu (customer);
                break;
        }
    }
}
