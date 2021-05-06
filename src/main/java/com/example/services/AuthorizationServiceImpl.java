package com.example.services;

import com.example.IntInput;
import com.example.models.Customer;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationServiceImpl implements AuthorizationService {
    private final String dBUrl = "jdbc:postgresql://localhost/ExampleShop";
    private final String dBUser = "postgres";
    private final String dBPassword = "admin";
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
            String rawCardNumber = scanner.nextLine();
            if (rawCardNumber.equals("")) {
                break;
            } else {
                Pattern pattern = Pattern.compile("^[0-9 ]{16,19}");
                Matcher matcher = pattern.matcher(rawCardNumber);
                accepted = matcher.matches();
                if (accepted) {
                    String formattedCardNumber = rawCardNumber.replaceAll ("\\s", "");
                    customer.setCardNumber(formattedCardNumber);
                    addToDatabase(customer, "card number", formattedCardNumber);
                } else {
                    System.out.println("Wrong card number format try again");
                }

            }
        }
        String SQL = "INSERT INTO users(name, password, balance) VALUES (?, ?, 0)";
        try(Connection connection = DriverManager.getConnection (dBUrl, dBUser, dBPassword);
            PreparedStatement pstmt = connection.prepareStatement (SQL, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString (1, name);
            pstmt.setString (2, password);
            int affectedRows = pstmt.executeUpdate ();
            if(affectedRows > 0){
                try(ResultSet rs = pstmt.getGeneratedKeys ()){
                    if(rs.next ()){
                        System.out.println ("New user registered. Welcome");
                        customer.setName (name);
                        customer.setPassword (password);
                        customer.setCashAmount (0);
                    }
                }catch (SQLException e){
                    System.out.println (e.getMessage ());
                }
            }
        }
        catch (SQLException e){
            System.out.println (e.getMessage ());
        }
        return customer;
    }

    private void addToDatabase (Customer customer, String type, String value) {

    }

    private Customer loginUser() {
        Customer customer;
        while(true) {
            System.out.print("Enter your name: ");
            System.out.println("           or press 0 to get back.");
            var name = scanner.nextLine();
            if(name.equals("0")) return authorizeUser();
            System.out.println("Enter your password: ");
            var password = scanner.nextLine();
            String SQL = String.format ("SELECT * FROM users WHERE name = '%s' AND password = '%s'", name, password);
            try(Connection connection = DriverManager.getConnection (dBUrl, dBUser, dBPassword);
                Statement statement = connection.createStatement ();
                ResultSet rs = statement.executeQuery (SQL)){
                if (rs.next ()){
                    customer = new Customer (name, password);
                    customer.setCashAmount (rs.getInt ("balance"));
                    return customer;
                }
            }
            catch (SQLException e){
                System.out.println (e.getMessage ());
            }
            System.out.println("Incorrect password or name. Try again.");
        }
    }
}
