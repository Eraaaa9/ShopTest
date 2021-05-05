package com.example;

import java.util.Scanner;

public class IntInput {
    public static int readInput() {
        String input;
        while(true) {
            Scanner scanner = new Scanner (System.in);
            input = scanner.nextLine ();
            if (!isNumeric (input)) {
                System.out.println ("Please input an Integer.");
                continue;
            }
            else return Integer.parseInt(input);
        }

    }
    public static boolean isNumeric(String str){
        if(str == null){
            return false;
        }
        try {
            int i = Integer.parseInt (str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
