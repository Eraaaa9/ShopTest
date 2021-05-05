package com.example.services;

import com.example.IntInput;
import com.example.models.Customer;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationServiceImpl implements AuthorizationService {

    private final Scanner scanner = new Scanner(System.in);
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
        var name = scanner.nextLine();
        customer.setName(name);
        System.out.println("Enter your password: ");
        var password = scanner.nextLine();
        customer.setPassword(password);
        boolean accepted = false;
        while(!accepted) {
            System.out.println("Add card number (Optional, press Enter to skip)");
            String cardNumber = scanner.nextLine();
            if (cardNumber.equals("")) {
                break;
            } else {
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
        Customer.users.add(customer);
        return customer;
    }

    private Customer loginUser() {
        while(true) {
            System.out.print("Enter your name: ");
            System.out.println("           or press 0 to get back.");
            var name = scanner.nextLine();
            if(name.equals("0")) return authorizeUser();
            System.out.println("Enter your password: ");
            var password = scanner.nextLine();
            for (var customer : Customer.users) {
                if (customer.getName().equals(name) && customer.getPassword().equals(password)) {
                    return customer;
                }
            }
            System.out.println("Incorrect password or name. Try again.");
        }
    }
}
