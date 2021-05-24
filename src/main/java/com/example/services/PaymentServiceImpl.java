package com.example.services;

import com.example.helpers.CreditCardChecker;
import com.example.helpers.IntInput;
import com.example.models.Customer;
import com.example.models.Product;
import com.example.repositories.DataManager;

import java.util.ArrayList;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private final CreditCardChecker creditCardChecker;
    private final DataManager dataManager;

    public PaymentServiceImpl(CreditCardChecker creditCardChecker, DataManager dataManager) {
        this.creditCardChecker = creditCardChecker;
        this.dataManager = dataManager;
    }

    @Override
    public void makePayment(Customer customer) {
        System.out.println ("Choose an option to pay:");
        System.out.println ("1.Cash  2.Card  3.Back to menu");
        int input = IntInput.readInput ();
        switch (input){
            case 1:
                payByCash(customer);
                break;
            case 2:
                payByCard(customer);
                break;
            default:
        }
    }

    private void payByCard (Customer customer) {
        if(customer.getCardNumber () == null || customer.getCardNumber().equals("")){
            String cardNumber = creditCardChecker.creditCardCheck();
            customer.setCardNumber(cardNumber);
            dataManager.updateCreditCardNumber(customer, cardNumber);
        }
        System.out.println ("Successfully paid. Thank you!");
        customer.getCart ().getItems ().clear ();
    }

    private void payByCash (Customer customer) {
        int totalSum = 0;
        for(var item : customer.getCart ().getItems ()){
            totalSum += item.getPrice ();
        }
        int payment = customer.getBalance () - totalSum;

        if(payment >= 0){
            customer.setBalance(payment);
            dataManager.updateBalance(customer, -payment);
            System.out.println ("Successfully paid. Thank you. Your current balance is: " + customer.getBalance ());
        } else {
            System.out.println ("Not enough cash");
            makeSuggestion(customer, totalSum);
        }
        customer.getCart ().getItems ().clear ();
    }

    private void makeSuggestion (Customer customer, int totalSum) {
        List<Product> toRemove = new ArrayList<> ();
        for(var item : customer.getCart ().getItems ()){
            if(customer.getBalance() + item.getPrice () >= totalSum){
                System.out.println ("Removing " + item + "would yield in sufficient amount of money");
                System.out.println ("Do you accept suggestion: Press 1 for yes or 2 for no");
                int input = IntInput.readInput ();
                if(input == 1){
                    toRemove.add (item);
                    System.out.println (item + " removed");
                } else {
                    System.out.println ("Showing next suggestion...");
                }
            }
        }
        if(customer.getCart ().getItems ().removeAll (toRemove)){
            payByCash (customer);
        }else {
            System.out.println ("No more suggestions");
            System.out.println ("Add some money or use card.");
            
        }
    }
}
