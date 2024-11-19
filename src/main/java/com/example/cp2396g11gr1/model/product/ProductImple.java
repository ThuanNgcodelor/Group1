package com.example.cp2396g11gr1.model.product;

import com.example.cp2396g11gr1.config.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductImple implements ProductDAO{
    @Override
    public List<Products> showStock() {
        String query = "SELECT p.id, p.productName, p.price, p.quantity AS stock_quantity, " +
                "COALESCE(SUM(o.quantity), 0) AS sold_quantity " +
                "FROM products p " +
                "LEFT JOIN orders o ON p.id = o.productID " +
                "GROUP BY p.id, p.productName, p.price, p.quantity";
        List<Products> stockList = new ArrayList<>();

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Products product = new Products();
                product.setId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("productName"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStockQuantity(resultSet.getInt("stock_quantity"));
                product.setStockSold(resultSet.getInt("sold_quantity"));
                product.setStockAmount(resultSet.getDouble("price") * resultSet.getInt("sold_quantity"));

                stockList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockList;
    }




    Connection conn = MyConnection.getConnection();

    @Override
    public int getProductStock(int productID) {
        int stock = 0;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT quantity FROM products WHERE id = ?");
            statement.setInt(1, productID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                stock = resultSet.getInt("quantity");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stock;
    }

    @Override
    public boolean addProducts(Products products) {
        try{
            PreparedStatement statement = conn.prepareStatement("insert into products(productName,price,quantity,image,categoryID,areaID,chipID,supplierID) values(?,?,?,?,?,?,?,?)");
            statement.setString(1, products.getProductName());
            statement.setDouble(2, products.getPrice());
            statement.setInt(3, products.getQuantity());
            statement.setString(4, products.getImage());
            statement.setInt(5, products.getCategoryID());
            statement.setInt(6,products.getAreaID());
            statement.setInt(7, products.getChipID());
            statement.setInt(8, products.getSupplierID());
            int check = statement.executeUpdate();
            return check > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateProducts(Products product) {
        String sql = "UPDATE products SET productName = ?, price = ?, quantity = ?, areaID = ?, categoryID = ?, chipID = ?, supplierID= ?, image = ? WHERE id = ?";
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setInt(4, product.getAreaID());
            statement.setInt(5, product.getCategoryID());
            statement.setInt(6, product.getChipID());
            statement.setInt(7, product.getSupplierID());
            statement.setString(8, product.getImage());
            statement.setInt(9, product.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteProducts(Products product) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM products WHERE id = ?");
            statement.setInt(1, product.getId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public Products getProducts(int id) {
        return null;
    }
    @Override
    public List<Products> AllProducts() {
        List<Products> productList = new ArrayList<>();
        String query = "SELECT p.id, p.productName, p.price, p.quantity, p.image, " +
                "c.categoryID, a.areaID, ch.chipID, sp.supplierID, " +
                "c.categoryName, a.areaName, ch.chipName, sp.supplierName " +
                "FROM products p " +
                "INNER JOIN category c ON p.categoryID = c.categoryID " +
                "INNER JOIN area a ON p.areaID = a.areaID " +
                "INNER JOIN chip ch ON p.chipID = ch.chipID " +
                "INNER JOIN supplier sp ON p.supplierID = sp. supplierID";

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Products product = new Products();
                product.setId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("productName"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setImage(resultSet.getString("image"));
                product.setCategoryName(resultSet.getString("categoryName"));
                product.setAreaName(resultSet.getString("areaName"));
                product.setChipName(resultSet.getString("chipName"));
                product.setSupplierName(resultSet.getString("supplierName"));

                productList.add(product);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving products", e);
        }
        return productList;
    }

    @Override
    public void updateProductStock(int productID, int stock) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE products set quantity = ? WHERE id = ?");
            statement.setInt(1, stock);
            statement.setInt(2, productID);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Products> findProducts(String keyword) {
        List<Products> productList = new ArrayList<>();
        String query = "SELECT p.id, p.productName, p.price, p.quantity, p.image, " +
                "c.categoryName, a.areaName, ch.chipName " +
                "FROM products p " +
                "JOIN category c ON p.categoryID = c.categoryID " +
                "JOIN area a ON p.areaID = a.areaID " +
                "JOIN chip ch ON p.chipID = ch.chipID " +
                "WHERE p.productName LIKE ? " +
                "OR c.categoryName LIKE ? " +
                "OR a.areaName LIKE ? " +
                "OR ch.chipName LIKE ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            String searchKeyword = "%" + keyword + "%";
            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);
            statement.setString(3, searchKeyword);
            statement.setString(4, searchKeyword);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Products product = new Products();
                    product.setId(resultSet.getInt("id"));
                    product.setProductName(resultSet.getString("productName"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setImage(resultSet.getString("image"));
                    product.setCategoryName(resultSet.getString("categoryName"));
                    product.setAreaName(resultSet.getString("areaName"));
                    product.setChipName(resultSet.getString("chipName"));
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}