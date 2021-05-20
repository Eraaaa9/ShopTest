package com.example.repositories;

import com.example.models.Customer;
import com.example.models.Product;
import com.example.models.ProductCatalog;
import com.example.models.ProductsList;

import java.sql.*;

public class DataManagerImpl implements DataManager {
    @Override
    public Connection connect() throws SQLException {
        String dBUrl = "jdbc:postgresql://localhost/ExampleShop";
        String dBUser = "postgres";
        String dBPassword = "admin";
        return DriverManager.getConnection(dBUrl, dBUser, dBPassword);
    }
    @Override
    public void updateBalance(Customer customer, int amount){
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
    @Override
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
    @Override
    public void fillCatalog(ProductCatalog productCatalog){
        String SQL = "SELECT model, price, category from products";
        try(Connection connection = connect ();
            Statement statement = connection.createStatement ();
            ResultSet rs = statement.executeQuery (SQL)){
            while(rs.next ()){
                for(var list : productCatalog.getCategory ()){
                    if(list.getName ().equals (rs.getString ("category"))){
                        list.addProduct (new Product(rs.getString ("model"), rs.getInt ("price")));
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println (e.getMessage ());
        }
    }
}
