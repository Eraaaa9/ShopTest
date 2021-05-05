package com.example.services;

import com.example.IntInput;
import com.example.MainMenu;
import com.example.models.Customer;
import java.util.Scanner;

public class AccountHandleServiceImpl implements AccountHandleService {
    @Override
    public void handle (Customer customer) {
        Scanner scanner = new Scanner (System.in);
        printOptions ();
        int input = IntInput.readInput ();
        switch (input){
            case 1:
                System.out.println (customer);
                break;
            case 2:
                System.out.println ("Enter amount to deposit: ");
                String deposit = scanner.nextLine ();
                customer.deposit (Integer.parseInt (deposit));
                break;
            case 3:
                System.out.println ("Enter amount to withdraw: ");
                String withdraw = scanner.nextLine ();
                customer.withdraw (Integer.parseInt (withdraw));
                break;
            default:
                return;
        }
        handle (customer);
    }
    public void printOptions(){
        System.out.print("1. Show account ");
        System.out.print("2. Deposit to account ");
        System.out.print("3. Withdraw from account ");
        System.out.println("4. Back to main menu ");
    }
}
