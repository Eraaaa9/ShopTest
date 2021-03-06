package com.example;

import com.example.helpers.IntInput;
import com.example.models.Customer;
import com.example.models.ProductsList;
import com.example.services.*;

public class MainMenu {
    private final ProductCatalogService productCatalogService;
    private final ProductListService productListService;
    private final AccountHandleService accountHandleService;
    private final CartService cartService;

    public MainMenu (ProductListService productListService, ProductCatalogService productCatalogService,
                     AccountHandleService accountHandleService, CartService cartService) {
        this.productListService = productListService;
        this.productCatalogService = productCatalogService;
        this.accountHandleService = accountHandleService;
        this.cartService = cartService;
    }

    public Customer showMenu (Customer customer) {
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
                accountHandleService.handleAccountPage (customer);
                break;
            case 3:
                cartService.showCart (customer);
                break;
            case 4:
                return null;
            default:
                showMenu (customer);
                break;
        }
        return customer;
    }
}
