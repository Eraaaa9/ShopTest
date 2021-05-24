package com.example.services;

import com.example.helpers.IntInput;
import com.example.helpers.PaginationPrinter;
import com.example.models.Customer;
import com.example.models.Product;
import com.example.repositories.DataManager;

import java.util.List;

public class CartServiceImpl implements CartService {
    private final PaginationPrinter paginationPrinter;
    private final DataManager dataManager;
    private final OrderService orderService;

    public CartServiceImpl(PaginationPrinter paginationPrinter, DataManager dataManager, OrderService orderService) {

        this.paginationPrinter = paginationPrinter;
        this.dataManager = dataManager;
        this.orderService = orderService;
    }

    public void showCart(Customer customer){
        System.out.print("1.Show items in cart ");
        System.out.print("2.Proceed to payment ");
        System.out.print("3.Back to menu ");
        int input = IntInput.readInput();
        switch (input) {
            case 1:
                showItemsInTheCart (customer, 1);
                break;
            case 2:
                orderService.createOrder (customer);
                break;
            default:
                return;
        }
    }

    public void showItemsInTheCart (Customer customer, int page){
        List<Product> items = customer.getCart ().getItems ();
        int maxPage = (int) Math.ceil ((double) items.size ()/3);
        if(items.isEmpty()){
            System.out.println("Cart is empty");
            showCart(customer);
        }
        System.out.println("Current items in the cart.");
        System.out.println ("Choose an item to remove: ");
        System.out.println ("Page " + page + "/" + maxPage);
        System.out.println (listCartProducts (items, page));

        int input =IntInput.readInput ();
        switch (input){
            case 1:
                dataManager.removeFromCart(items.get((page*3-3)), customer);
                System.out.println (items.remove ((page * 3) - 3) + " was removed from the cart");
                break;
            case 2:
                if(items.size()<2) break;
                dataManager.removeFromCart(items.get((page*3-2)), customer);
                System.out.println (items.remove ((page * 3) - 2) + " was removed from the cart");
                break;
            case 3:
                if(items.size()<3) break;
                dataManager.removeFromCart(items.get((page*3-1)), customer);
                System.out.println (items.remove ((page * 3) - 1) + " was removed from the cart");
                break;
            case 4:
                if(page == maxPage){
                    showItemsInTheCart (customer, page);
                } else {
                    showItemsInTheCart (customer, page + 1);
                }
                break;
            case 5:
                if(page == 1){
                    showItemsInTheCart (customer, page);
                } else {
                    showItemsInTheCart (customer, page - 1);
                }
                break;
            default:
                showCart (customer);
                break;
        }
    }
    private String listCartProducts(List<Product> products, int page){
        if(products.isEmpty())return "Cart is empty";
        StringBuilder result = new StringBuilder();
        paginationPrinter.getFirstOptions(products, page, result);
        return result.toString ();
    }
}

