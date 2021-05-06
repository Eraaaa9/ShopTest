package com.example.services;

import com.example.IntInput;
import com.example.models.Product;
import com.example.repositories.ProductCatalog;
import com.example.repositories.ProductsList;
import java.sql.*;
import java.util.Scanner;

public class ProductCatalogServiceImpl implements ProductCatalogService {
    private static final Scanner scanner = new Scanner(System.in);
    String url = "jdbc:postgresql://localhost/ExampleShop";
    String user = "postgres";
    String password = "admin";

    public ProductsList showCatalog() {
        ProductCatalog catalog = fill();
        System.out.println("Choose a category (number or name)");
        System.out.println(catalog);
        String categoryInput = scanner.nextLine();
        if(IntInput.isNumeric(categoryInput)){
            return catalog.getCategory().get(Integer.parseInt(categoryInput)-1);
        } else {
            for(var list : catalog.getCategory()){
                if(categoryInput.equalsIgnoreCase(list.getName())){
                    return list;
                }
            }
        }
        return catalog.getCategory().get(0);
    }
    public ProductCatalog fill(){
        ProductCatalog productCatalog = new ProductCatalog ();
        String SQL = "SELECT DISTINCT category FROM products";
        try(Connection connection = connect ();
            Statement statement = connection.createStatement ();
            ResultSet rs = statement.executeQuery (SQL)){
            while(rs.next ()){
                ProductsList productsList = new ProductsList ();
                productsList.setName (rs.getString ("category"));
                productCatalog.addList (productsList);
            }
        }
        catch (SQLException e){
            System.out.println (e.getMessage ());
        }
        fillCatalog (productCatalog);
        return productCatalog;
    }

    public void fillCatalog(ProductCatalog productCatalog){

        String SQL = "SELECT model, price, category from products";
        try(Connection connection = connect ();
            Statement statement = connection.createStatement ();
            ResultSet rs = statement.executeQuery (SQL)){
            while(rs.next ()){
                for(var list : productCatalog.getCategory ()){
                    if(list.getName ().equals (rs.getString ("category"))){
                        list.addProduct (new Product (rs.getString ("model"), rs.getInt ("price")));
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println (e.getMessage ());
        }
    }
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}
