package com.example.helpers;

import com.example.models.Product;

import java.util.List;

public class PaginationPrinter {
    public static void getFirstOptions(List<Product> products, int page, StringBuilder result) {
        for(int i = 1; i < 4; i++){
            if(((i + (page-1) * 3)) > products.size ())break;
            result.append (i).append (".");
            result.append (products.get ((i + (page - 1) * 3) - 1)).append (" ");
        }
        result.append("4.Next page ");
        result.append("5.Previous page ");
        result.append ("6.Back");
    }
}

