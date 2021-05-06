package com.example.models;

public class Customer{
    private String name;
    private String cardNumber;
    private int cashAmount;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(int cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String showBalance(){
        return ("Current balance is: " + cashAmount);
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
