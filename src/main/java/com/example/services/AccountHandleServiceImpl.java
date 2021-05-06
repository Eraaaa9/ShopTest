package com.example.services;

import com.example.IntInput;
import com.example.models.Customer;

import java.sql.*;

public class AccountHandleServiceImpl implements AccountHandleService {
    private final String dBUrl = "jdbc:postgresql://localhost/ExampleShop";
    private final String dBUser = "postgres";
    private final String dBPassword = "admin";

    @Override
    public void handle (Customer customer) {
        printOptions ();
        int input = IntInput.readInput ();
        switch (input){
            case 1:
                System.out.println (customer);
                break;
            case 2:
                System.out.println ("Enter amount to deposit: ");
                int depositAmount = IntInput.readInput ();
                deposit (customer, depositAmount);
                break;
            case 3:
                System.out.println ("Enter amount to withdraw: ");
                int withdrawAmount = IntInput.readInput ();
                withdraw (customer, withdrawAmount);
                break;
            default:
                return;
        }
        handle (customer);
    }

    private void withdraw (Customer customer, int withdrawAmount) {
        if(customer.getCashAmount () < withdrawAmount){
            System.out.println("Insufficient funds.");
        } else {
            customer.setCashAmount (customer.getCashAmount () - withdrawAmount);
            System.out.println ("New balance: " + customer.getCashAmount ());
            updateBalance (customer, -withdrawAmount);
        }
    }

    private void deposit (Customer customer, int depositAmount) {
        int newValue = customer.getCashAmount () + depositAmount;
        customer.setCashAmount (newValue);
        updateBalance (customer, depositAmount);
        System.out.println("New balance: " + newValue);
    }

    private void printOptions(){
        System.out.print("1. Show account ");
        System.out.print("2. Deposit to account ");
        System.out.print("3. Withdraw from account ");
        System.out.println("4. Back to main menu ");
    }
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(dBUrl, dBUser, dBPassword);
    }
    private void updateBalance(Customer customer, int amount){
        String SQL = "UPDATE users SET balance = balance+? where name = ?";
        try (Connection connection = connect ();
             PreparedStatement statement = connection.prepareStatement (SQL)) {
            statement.setInt (1, amount);
            statement.setString (2, customer.getName ());
            statement.execute ();
        } catch (SQLException e) {
            System.out.println (e.getMessage ());
        }
    }

}
