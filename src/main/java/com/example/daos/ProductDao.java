package com.example.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import com.Factories.ConnectionFactorie;
import com.example.models.Product;

public class ProductDao extends ConnectionFactorie{
    private Connection connection;

    public ProductDao() throws SQLException{
        this.connection = connect();
    }

    public boolean Create(Product product) throws SQLException{
        if(product == null)
            return false;
        String sql = "INSERT INTO product(name, price) VALUES (?, ?)";
        var statement = connection.prepareStatement(sql);
        try {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.execute();
            statement.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Product> FindAll() throws SQLException{
        String sql = "select * FROM product";
        var statement = connect().prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        var products = new ArrayList<Product>();
        while(result.next()){
            var product = new Product();
            product.setId(result.getInt("id"));
            product.setName(result.getString("name"));
            product.setPrice(result.getDouble("price"));
            products.add(product);
        }
        result.close();
        return products;        
    }
}
