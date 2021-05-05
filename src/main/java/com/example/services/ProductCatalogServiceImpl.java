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

    public ProductCatalog fille() {

        ProductCatalog productCatalog = new ProductCatalog();
        ProductsList smartphones = new ProductsList("Smartphones");
        smartphones.addProduct(new Product("Apple iPhone 11", 369990));
        smartphones.addProduct(new Product("Samsung Galaxy A32", 109990));
        smartphones.addProduct(new Product("Oppo Reno 5 Lite", 161890));
        smartphones.addProduct(new Product("Xiaomi Redmi Note 10", 99990));
        smartphones.addProduct(new Product("Huawei P30", 325000));
        smartphones.addProduct(new Product("Apple IPhone 12 Pro", 654000));
        smartphones.addProduct(new Product("Apple iPhone 7S", 265000));
        smartphones.addProduct(new Product("One Plus 9R", 157000));
        smartphones.addProduct(new Product("Samsung Galaxy M12", 89900));
        smartphones.addProduct(new Product("Oppo A52", 87000));
        smartphones.addProduct(new Product("Poco X3 Pro", 125700));
        ProductsList laptops = new ProductsList("Laptops");
        laptops.addProduct(new Product("Lenovo Legion", 751420));
        laptops.addProduct(new Product("Asus Zenbook", 452130));
        laptops.addProduct(new Product("Acer Nitro", 654200));
        laptops.addProduct(new Product("HP Pavilion", 154100));
        laptops.addProduct(new Product("Apple MacBook", 150850));
        laptops.addProduct(new Product("Asus TUF", 320000));
        laptops.addProduct(new Product("HP Omen", 248600));
        laptops.addProduct(new Product("Lenovo IdeaPad", 98700));
        laptops.addProduct(new Product("Neo 15U", 142500));
        laptops.addProduct(new Product("Asus ROG", 339100));
        ProductsList gadgets = new ProductsList("Gadgets");
        gadgets.addProduct(new Product("IP camera", 10000));
        gadgets.addProduct(new Product("Smart Watch", 5400));
        gadgets.addProduct(new Product("IP camera 2", 12000));
        gadgets.addProduct(new Product("Smart scales", 7900));
        gadgets.addProduct(new Product("Amazfit", 15500));
        gadgets.addProduct(new Product("Air humidifier", 22500));
        gadgets.addProduct(new Product("Huawei Honor Band", 19990));
        gadgets.addProduct(new Product("Facial cleanser", 5990));
        gadgets.addProduct(new Product("Bluetooth speaker", 14500));
        gadgets.addProduct(new Product("Selfie stick", 8990));
        gadgets.addProduct(new Product("Automatic umbrella", 9990));
        ProductsList accessories = new ProductsList("Accessories");
        accessories.addProduct(new Product("PowerBank Xiaomi", 8990));
        accessories.addProduct(new Product("Honor FlyPads Pro", 37990));
        accessories.addProduct(new Product("Huawei FreeBuds 3", 37990));
        accessories.addProduct(new Product("Mi True Wireless Earphones", 12990));
        accessories.addProduct(new Product("Edifier W200BT", 6500));
        accessories.addProduct(new Product("Galaxy Buds", 53500));
        accessories.addProduct(new Product("Wireless Charger", 7500));
        accessories.addProduct(new Product("QCY T8", 13990));
        accessories.addProduct(new Product("Apple AirPods", 72990));
        accessories.addProduct(new Product("Xiaomi MiBand 5 charger", 1990));
        ProductsList spareParts = new ProductsList("Spare parts");
        spareParts.addProduct(new Product("Display Xiaomi Redmi 4x", 5990));
        spareParts.addProduct(new Product("Display Huawei Honor 9", 19900));
        spareParts.addProduct(new Product("Display Apple IPhone 11", 39900));
        spareParts.addProduct(new Product("Speaker", 1990));
        spareParts.addProduct(new Product("Battery Samsung Galaxy S10", 25490));
        spareParts.addProduct(new Product("Camera module Sony IMX682", 54900));
        spareParts.addProduct(new Product("Camera module Sony IMX219", 17900));
        spareParts.addProduct(new Product("Keyboard for Acer Aspire", 3900));
        spareParts.addProduct(new Product("DC Jack", 1990));
        productCatalog.addList(smartphones);
        productCatalog.addList(laptops);
        productCatalog.addList(gadgets);
        productCatalog.addList(accessories);
        productCatalog.addList(spareParts);
        return productCatalog;
    }
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
        String url = "jdbc:postgresql://localhost/ExampleShop";
        String user = "postgres";
        String password = "admin";

        String SQL = "SELECT model, price, category from products";
        try(Connection connection = DriverManager.getConnection (url, user, password);
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
