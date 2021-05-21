package com.example.repositories;

import com.example.models.Customer;
import com.example.models.Product;
import com.example.models.ProductCatalog;
import com.example.models.ProductsList;

import java.sql.*;

public class DataManagerImpl implements DataManager {

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

    @Override
    public void removeFromCart(Product product, Customer customer) {
        String SQL = "delete from carts where productId = ? and userId = ?";
        try(Connection connection = connect ();
            PreparedStatement statement = connection.prepareStatement(SQL)){
                statement.setInt(1, product.getId());
                statement.setInt(2, customer.getId());
        } catch (SQLException e){
                System.out.println(e.getMessage());
        }
    }
    public Customer loginInDatabase(String name, String password) {
        String SQL = String.format("SELECT * FROM users WHERE name = '%s' AND password = '%s'", name, password);
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            if (rs.next()) {
                Customer customer = new Customer(name, password);
                customer.setBalance(rs.getInt("balance"));
                customer.setCardNumber(rs.getString("cardNumber"));
                return customer;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public void addUserToDatabase(String name, String password, String cardNumber){
        String SQL = "INSERT INTO users(name, password, balance) VALUES (?, ?, 0)";
        try(Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement (SQL, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString (1, name);
            pstmt.setString (2, password);
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println (e.getMessage ());
        }
    }
}
