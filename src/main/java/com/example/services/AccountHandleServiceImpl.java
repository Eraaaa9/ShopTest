package com.example.services;

import com.example.helpers.IntInput;
import com.example.models.Customer;
import com.example.repositories.DataManager;

public class AccountHandleServiceImpl implements AccountHandleService {
    private DataManager dataManager;

    public AccountHandleServiceImpl(DataManager dataManager) {
        this.dataManager = dataManager;
    }

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
    private void printOptions(){
        System.out.print("1. Show account ");
        System.out.print("2. Deposit to account ");
        System.out.print("3. Withdraw from account ");
        System.out.println("4. Back to main menu ");
    }
    private void withdraw (Customer customer, int withdrawAmount) {
        if(customer.getCashAmount () < withdrawAmount){
            System.out.println("Insufficient funds.");
        } else {
            customer.setCashAmount (customer.getCashAmount () - withdrawAmount);
            dataManager.updateBalance (customer, -withdrawAmount);
            System.out.println ("New balance: " + customer.getCashAmount ());
        }
    }

    private void deposit (Customer customer, int depositAmount) {
        int newValue = customer.getCashAmount () + depositAmount;
        customer.setCashAmount (newValue);
        dataManager.updateBalance (customer, depositAmount);
        System.out.println("New balance: " + newValue);
    }


}
