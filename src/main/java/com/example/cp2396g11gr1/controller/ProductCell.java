package com.example.cp2396g11gr1.controller;


import com.example.cp2396g11gr1.config.MyConnection;
import com.example.cp2396g11gr1.model.product.ProductDAO;
import com.example.cp2396g11gr1.model.product.ProductImple;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

public class ProductCell extends ListCell<Product> {
    Connection conn = MyConnection.getConnection();

    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);
        if (empty || product == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Create ImageView for the product image
            ImageView imageView = new ImageView();
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);

            try {
                String imagePath = product.getImages();
                if (!imagePath.startsWith("http") && !imagePath.startsWith("https")) {
                    imagePath = "file:/" + imagePath;
                }
                Image image = new Image(imagePath);
                imageView.setImage(image);
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
                imageView.setImage(new Image("file:/path/to/default/image.jpg"));
            }

            // Create Labels for product name and price
            Label nameLabel = new Label(product.getProductName());

            // Format price as VND
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedPrice = currencyFormat.format(product.getPrice());
            Label priceLabel = new Label("Price: " + formattedPrice);

            // Button "Thêm món"
            Button addButton = new Button("Add");

            // Handle the button click event
            addButton.setOnAction(event -> {
                int quantity = product.getStock();
                if (quantity == 0) {
                    showAlert(Alert.AlertType.ERROR, "Quantity cannot be zero!");
                    return;
                }

                Order_out ord_out = getOrderOut(product.getProductID());
                if (ord_out != null) {
                    // Product exists, update quantity and price
                    int updatedQuantity = ord_out.getQuantity() + 1;
                    int updatedPrice = ord_out.getPrice() / ord_out.getQuantity() * updatedQuantity;
                    updateOrderOut(product.getProductID(), updatedQuantity, updatedPrice, ord_out.getOrder_id());

                    // Trừ đi số lượng sản phẩm trong stock
                } else {
                    addProductToOrderOut(product);
                }

                ProductDAO productDAO = new ProductImple();
                int newStock = product.getStock() - 1;
                productDAO.updateProductStock(product.getProductID(), newStock);
                product.setStock(newStock);
            });

            // Create VBox to hold the product components
            VBox vbox = new VBox(10, imageView, nameLabel, priceLabel, addButton);
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-border-color: #000000; -fx-border-radius: 10px; -fx-background-color: #e5dede;");
            vbox.setPadding(new javafx.geometry.Insets(10));

            setGraphic(vbox);
        }
    }


    private Order_out getOrderOut(int productID) {
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM orders WHERE productID = ? AND order_details = 1");
            statement.setInt(1, productID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Order_out orderOut = new Order_out();
                orderOut.setOrder_id(rs.getInt("id"));
                orderOut.setPrice(rs.getInt("price"));
                orderOut.setQuantity(rs.getInt("quantity"));
                return orderOut;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addProductToOrderOut(Product product) {
        String query = "INSERT INTO orders (productName, price, quantity, order_details,status, productID) VALUES (?, ?, ?,?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, 1);
            pstmt.setInt(4, 1);
            pstmt.setInt(5,1);
            pstmt.setInt(6, product.getProductID());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product added to order_out successfully.");
            } else {
                System.out.println("Failed to add product to order_out.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateOrderOut(int productID, int quantity, int price, int orderID) {
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE orders SET price = ?, quantity = ? WHERE productID = ? AND id = ?");
            statement.setInt(1, price);
            statement.setInt(2, quantity);
            statement.setInt(3, productID);
            statement.setInt(4, orderID);
            statement.executeUpdate();
            System.out.println("Order updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
