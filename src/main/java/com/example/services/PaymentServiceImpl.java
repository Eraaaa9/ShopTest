package com.example.services;

import com.example.helpers.IntInput;
import com.example.models.Customer;
import com.example.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public void makePayment(Customer customer) {
        System.out.println ("Choose an option to pay:");
        System.out.println ("1.Cash  2.Card   3.Back to menu");
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
        if(customer.getCardNumber () == null){
            System.out.println ("Please enter your card number");
            boolean accepted = false;
            while(!accepted) {
                System.out.println("Add card number");
                Scanner scanner = new Scanner (System.in);
                String cardNumber = scanner.nextLine();
                Pattern pattern = Pattern.compile("^[0-9 ]{16,19}");
                Matcher matcher = pattern.matcher(cardNumber);
                accepted = matcher.matches();
                if (accepted) {
                    customer.setCardNumber(cardNumber);
                } else {
                    System.out.println("Wrong card number format try again");
                }
            }
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
