package com.example.services;

import com.example.helpers.IntInput;
import com.example.helpers.PaginationPrinter;
import com.example.models.Customer;
import com.example.models.Product;
import com.example.repositories.DataManager;

import java.util.List;

public class CartServiceImpl implements CartService {
    private final PaymentService paymentService;
    private final PaginationPrinter paginationPrinter;
    private final DataManager dataManager;

    public CartServiceImpl(PaymentService paymentService, PaginationPrinter paginationPrinter, DataManager dataManager) {
        this.paymentService = paymentService;
        this.paginationPrinter = paginationPrinter;
        this.dataManager = dataManager;
    }

    public void showCart(Customer customer){
        printOptions();
        int input = IntInput.readInput();
        switch (input) {
            case 1:
                showItemsInTheCart (customer, 1);
                break;
            case 2:
                paymentService.makePayment (customer);
                break;
            default:
                return;
        }
        showCart (customer);
    }

    private void printOptions () {
        System.out.print("1.Show items in cart ");
        System.out.print("2.Proceed to payment ");
        System.out.print("3.Back to menu ");
    }

    public void showItemsInTheCart (Customer customer, int page){
        System.out.println("Current items in the cart.");
        System.out.println ("Choose an item to remove: ");
        List<Product> items = customer.getCart ().getItems ();
        int maxPage = (int) Math.ceil ((double) items.size ()/3);
        System.out.println ("Page " + page + "/" + maxPage);
        System.out.println (listCartProducts (items, page));
        if(items.isEmpty()){
            return;
        }
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

