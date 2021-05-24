package com.example.services;

import com.example.helpers.CreditCardChecker;
import com.example.helpers.IntInput;
import com.example.models.Customer;
import com.example.repositories.DataManager;

public class AccountHandleServiceImpl implements AccountHandleService {
    private final DataManager dataManager;
    private final CreditCardChecker creditCardChecker;

    public AccountHandleServiceImpl(DataManager dataManager, CreditCardChecker creditCardChecker) {
        this.dataManager = dataManager;
        this.creditCardChecker = creditCardChecker;
    }

    @Override
    public void handleAccountPage (Customer customer) {
        System.out.print("1. Show account ");
        System.out.print("2. Deposit to account ");
        System.out.print("3. Withdraw from account ");
        System.out.print("4. Add credit card ");
        System.out.println("5. Back to main menu ");
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
            case 4:
                String creditCard = creditCardChecker.creditCardCheck();
                customer.setCardNumber(creditCard);
                dataManager.updateCreditCardNumber(customer, creditCard);
            default:
                return;
        }
        handleAccountPage (customer);
    }

    private void withdraw (Customer customer, int withdrawAmount) {
        if(customer.getBalance() < withdrawAmount){
            System.out.println("Insufficient funds.");
        } else {
            customer.setBalance(customer.getBalance() - withdrawAmount);
            dataManager.updateBalance (customer, -withdrawAmount);
            System.out.println ("New balance: " + customer.getBalance());
        }
    }

    private void deposit (Customer customer, int depositAmount) {
        int newValue = customer.getBalance() + depositAmount;
        customer.setBalance(newValue);
        dataManager.updateBalance (customer, depositAmount);
        System.out.println("New balance: " + newValue);
    }
}
