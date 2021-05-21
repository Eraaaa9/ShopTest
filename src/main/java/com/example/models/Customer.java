package com.example.models;

import lombok.Data;

@Data
public class Customer{
    private int id;
    private String name;
    private String cardNumber;
    private int balance;
    private String password;
    private Cart cart;
    public Customer() {
        cart = new Cart();
    }

    public Customer (String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

    public String showBalance(){
        return ("Current balance is: " + balance);
    }
    public String toString(){
        String result = "";
        result += name;
        result += " ";
        result += showBalance ();
        if(cardNumber != null){
            result += "\nCard number: ";
            result += cardNumber;
        }
        return result;
    }
}
