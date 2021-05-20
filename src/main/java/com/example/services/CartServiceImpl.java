package com.example.services;

import com.example.helpers.IntInput;
import com.example.helpers.PaginationPrinter;
import com.example.models.Customer;
import com.example.models.Product;
import java.util.List;

public class CartServiceImpl implements CartService {
    private final PaymentService paymentService;

    public CartServiceImpl (PaymentService paymentService) {
        this.paymentService = paymentService;
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
                System.out.println (items.remove ((page * 3) - 3) + " was removed from the cart");
                break;
            case 2:
                System.out.println (items.remove ((page * 3) - 2) + " was removed from the cart");
                break;
            case 3:
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
        PaginationPrinter.getFirstOptions(products, page, result);
        result.append("4.Next page ");
        result.append("5.Previous page ");
        result.append("6.Back ");
        return result.toString ();
    }
}

