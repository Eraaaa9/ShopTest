package com.example.helpers;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreditCardChecker {
    public String creditCardCheck() {
        boolean accepted = false;
        while (!accepted) {
            System.out.println("Add card number (Optional, press Enter to skip)");
            Scanner scanner = new Scanner(System.in);
            String rawCardNumber = scanner.nextLine();
            if (rawCardNumber.equals("")) {
                return "";
            } else {
                Pattern pattern = Pattern.compile("^[0-9 ]{16,19}");
                Matcher matcher = pattern.matcher(rawCardNumber);
                accepted = matcher.matches();
                if (accepted) {
                    return rawCardNumber.replaceAll("\\s", "");
                } else {
                    System.out.println("Wrong card number format try again");
                }
            }
        }
        return "";
    }
}
