package com.example.cp2396g11gr1.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    // Chuỗi kết nối với tham số encrypt=false để vô hiệu hóa SSL
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Sytems;encrypt=false;";
    private static final String USER = "sa";
    private static final String PASSWORD = "1234";

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Lấy dữ liệu từ ResultSet và thêm vào danh sách sản phẩm
            while (rs.next()) {
                int productID = rs.getInt("id");
                String productName = rs.getString("productName");
                double price = rs.getDouble("price");
                String images = rs.getString("image");
                int stock = rs.getInt("quantity");

                // Thêm sản phẩm vào danh sách
                products.add(new Product(productID, productName, price, images, stock));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }


}
