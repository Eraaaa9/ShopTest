package com.example.services;

import com.example.helpers.CreditCardChecker;
import com.example.helpers.IntInput;
import com.example.models.Customer;
import com.example.repositories.DataManager;

import java.sql.*;
import java.util.Scanner;

public class AuthorizationServiceImpl implements AuthorizationService {
    private final Scanner scanner = new Scanner(System.in);
    private final CreditCardChecker creditCardChecker;
    private final DataManager dataManager;

    public AuthorizationServiceImpl(CreditCardChecker creditCardChecker, DataManager dataManager) {
        this.creditCardChecker = creditCardChecker;
        this.dataManager = dataManager;
    }

    public Customer authorizeUser() {
        System.out.println("Enter 1 for log in or 2 for Sign up or 0 to Exit");
        Customer customer;
        while (true) {
            var input = IntInput.readInput();
            if (input == 1) {
                customer = loginUser();
                break;
            } else if (input == 2) {
                customer = signUpUser();
                break;
            } else if (input == 0) {
                System.exit(1);
            } else {
                System.out.println("Enter 1 for Log in or 2 for Sign up");
            }
        }
        return customer;
    }

    private Customer signUpUser() {
        Customer customer = new Customer();
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        customer.setName(name);
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        customer.setPassword(password);
        String cardNumber = creditCardChecker.creditCardCheck(customer);
        customer.setCardNumber(cardNumber);
        dataManager.addUserToDatabase(name, password, cardNumber);
        return customer;
    }

    private Customer loginUser() {
        Customer customer = null;
        while(true) {
            System.out.print("Enter your name: ");
            System.out.println("           or press 0 to get back.");
            String name = scanner.nextLine();
            if(name.equals("0")) return authorizeUser();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
            customer = dataManager.loginInDatabase(name, password);
            if(customer != null){
                return customer;
            }
            System.out.println("Incorrect password or name. Try again.");
        }
    }
}
