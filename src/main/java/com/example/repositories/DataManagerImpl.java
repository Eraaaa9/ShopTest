package com.example.repositories;

import com.example.models.Customer;
import com.example.models.Product;
import com.example.models.ProductCatalog;
import com.example.models.ProductsList;

import java.sql.*;

public class DataManagerImpl implements DataManager {

    private Connection connect() throws SQLException {
        String dBUrl = "jdbc:postgresql://localhost/postgres";
        String dBUser = "postgres";
        String dBPassword = "admin";
        return DriverManager.getConnection(dBUrl, dBUser, dBPassword);
    }

    @Override
    public void updateBalance(Customer customer, int amount){
        String SQL = "UPDATE customer SET balance = balance+? where name = ?";
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
    public ProductCatalog fillCatalogWithCategories(){
        ProductCatalog productCatalog = new ProductCatalog ();
        String SQL = "SELECT DISTINCT category FROM product";
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
        fillCatalogWithProducts (productCatalog);
        return productCatalog;
    }

    @Override
    public void fillCatalogWithProducts(ProductCatalog productCatalog){
        String SQL = "SELECT * from product";
        try(Connection connection = connect ();
            Statement statement = connection.createStatement ();
            ResultSet rs = statement.executeQuery (SQL)){
            while(rs.next ()){
                for(var list : productCatalog.getCategory ()){
                    if(list.getName ().equals (rs.getString ("category"))){
                        list.addProduct
                                (new Product(rs.getInt("code"),
                                        rs.getString ("name"),
                                        rs.getInt ("buyprice")));
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
        String SQL = "delete from cart_product where productcode = ? and cartid = ?";
        try(Connection connection = connect ();
            PreparedStatement statement = connection.prepareStatement(SQL)){
                statement.setInt(1, product.getProduct_id());
                statement.setInt(2, customer.getUser_id());
        } catch (SQLException e){
                System.out.println(e.getMessage());
        }
    }

    @Override
    public void addToCart(Product product, Customer customer) {
        String SQL = "insert into cart_product (productcode, cartid) values (?, ?)";
        try(Connection connection = connect ();
            PreparedStatement statement = connection.prepareStatement(SQL)){
            statement.setInt(1, product.getProduct_id());
            statement.setInt(2, customer.getUser_id());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Customer loginInDatabase(String name, String password) {
        String SQL = String.format("SELECT * FROM customer WHERE name = '%s' AND password = '%s'", name, password);
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            if (rs.next()) {
                Customer customer = new Customer(name, password);
                customer.setBalance(rs.getInt("balance"));
                customer.setCardNumber(rs.getString("cardNumber"));
                customer.setUser_id(rs.getInt("id"));
                return customer;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    @Override
    public int addUserToDatabase(String name, String password){
        int user_id = 0;
        String SQL = "INSERT INTO customer(name, password, balance) VALUES (?, ?, 0) returning *";
        try(Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement (SQL)){
            pstmt.setString (1, name);
            pstmt.setString (2, password);
            ResultSet rs = pstmt.executeQuery();
            user_id = rs.getInt("user_id");
        }
        catch (SQLException e){
            System.out.println (e.getMessage ());
        }
        return user_id;
    }

    @Override
    public void updateCreditCardNumber(Customer customer, String creditCard) {
        String SQL = "UPDATE customer SET \"cardNumber\" = ? where name = ?";
        try (Connection connection = connect ();
             PreparedStatement statement = connection.prepareStatement (SQL)) {
            statement.setString (1, creditCard);
            statement.setString (2, customer.getName ());
            statement.execute ();
        } catch (SQLException e) {
            System.out.println (e.getMessage ());
        }
    }

    @Override
    public void createOrder(Customer customer) {

    }
}
