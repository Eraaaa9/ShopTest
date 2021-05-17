package com.example.services;

import com.example.IntInput;
import com.example.models.Customer;
import com.example.models.Product;
import com.example.models.ProductsList;
import java.util.ArrayList;

public class ProductListServiceImpl implements ProductListService {

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
                return;
        }

    }

    public String listProducts(ProductsList productsList, int page){
        StringBuilder result = new StringBuilder();
        ArrayList<Product> products = productsList.getProducts ();
        if(products.size () <= 3){
            for(var item : products){
                result.append (products.indexOf (item) + 1);
                result.append (".");
                result.append (item).append (" ");
            }
        } else {
            for(int i = 1; i < 4; i++){
                if(((i + (page-1) * 3)) > products.size ()){
                    break;
                }
                result.append (i).append (".");
                result.append (products.get ((i + (page - 1) * 3) - 1)).append (" ");
            }
        }
            result.append("4.Next page ");
            result.append("5.Previous page ");
            result.append ("6.Back");
        return result.toString ();
    }
    @Override
    public void addToCart(Product product, Customer customer) {
        customer.getCart().addProduct(product);
        System.out.println (product + " added to the cart");
    }
}
