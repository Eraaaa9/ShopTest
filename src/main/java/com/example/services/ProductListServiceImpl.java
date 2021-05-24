package com.example.services;

import com.example.helpers.IntInput;
import com.example.helpers.PaginationPrinter;
import com.example.models.Customer;
import com.example.models.Product;
import com.example.models.ProductsList;
import com.example.repositories.DataManager;

import java.util.ArrayList;

public class ProductListServiceImpl implements ProductListService {
    private final PaginationPrinter paginationPrinter;
    private final DataManager dataManager;

    public ProductListServiceImpl(PaginationPrinter paginationPrinter, DataManager dataManager) {
        this.paginationPrinter = paginationPrinter;
        this.dataManager = dataManager;
    }

    @Override
    public void showGoods(ProductsList productsList, Customer customer, int page) {
        System.out.println("Choose an item to buy: ");
        System.out.println(listProducts (productsList, page));
        int maxPage = (int) Math.ceil ((double) productsList.getProducts ().size ()/3);
        System.out.println ("Page " + page + "/" + maxPage);
        int input = IntInput.readInput ();
        switch (input){
            case 1:
                addToCart (productsList.getProducts().get (page*3-3), customer);
                break;
            case 2:
                addToCart (productsList.getProducts().get (page*3-2), customer);
                break;
            case 3:
                addToCart (productsList.getProducts().get (page*3-1), customer);
                break;
            case 4:
                if(page == maxPage){
                    showGoods (productsList, customer, page);
                } else {
                    showGoods (productsList, customer, page + 1);
                }
                break;
            case 5:
                if(page == 1){
                    showGoods (productsList, customer, page);
                }
                showGoods (productsList, customer, page - 1 );
                break;
            default:
        }
    }

    public String listProducts(ProductsList productsList, int page){
        StringBuilder result = new StringBuilder();
        ArrayList<Product> products = productsList.getProducts ();
        paginationPrinter.getFirstOptions(products, page, result);
        return result.toString ();
    }

    @Override
    public void addToCart(Product product, Customer customer) {
        customer.getCart().addProduct(product);
        dataManager.addToCart(product, customer);
        System.out.println (product + " added to the cart");
    }
}
