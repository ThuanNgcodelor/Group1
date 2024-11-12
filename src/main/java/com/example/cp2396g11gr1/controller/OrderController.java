package com.example.cp2396g11gr1.controller;

import com.example.cp2396g11gr1.config.MyConnection;
import com.example.cp2396g11gr1.model.bill.Bills;
import com.example.cp2396g11gr1.model.product.ProductDAO;
import com.example.cp2396g11gr1.model.product.ProductImple;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class OrderController implements Initializable {
    Connection conn = MyConnection.getConnection();
    @FXML
    private TableColumn<Order_out, String> name;
    @FXML
    private TableView<Order_out> ordertable;
    @FXML
    private TableColumn<Order_out, Integer> price;
    @FXML
    private TableColumn<Order_out, Integer> quantity;
    @FXML
    private Label totalprice;
    @FXML
    private TextField totalquantity;
    @FXML
    private Button placeOrderButton;
    private ObservableList<Order_out> order_outObservableList;
    @FXML
    private TextField voucher;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listOrder_out();
        placeOrderButton.setDisable(order_outObservableList == null || order_outObservableList.isEmpty());
    }
    public void listOrder_out() {
        List<Order_out> order_outList = showOrder_out(1);
        if (order_outList.isEmpty()) {
            System.out.println("No orders found");
        }
        order_outObservableList = FXCollections.observableArrayList(order_outList);
        name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        ordertable.setItems(order_outObservableList);
        totalPrice();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    private void hanhDeleteProduct(ActionEvent event) {
        Order_out orderOut = ordertable.getSelectionModel().getSelectedItem();
        if (orderOut == null) {
            System.out.println("No order selected for deletion.");
            showAlert(Alert.AlertType.WARNING, "Deletion Error", "Please select an order to delete.");
            return;
        }
        int quantity = orderOut.getQuantity();
        int productID = orderOut.getProductID();
        int orderID = orderOut.getOrder_id();
        ProductDAO productDAO = new ProductImple();
        int stock = productDAO.getProductStock(productID);
        int newStock = stock + quantity;
        productDAO.updateProductStock(productID, newStock);

        boolean success = removeOrder_out(orderID);
        if (success) {
            System.out.println("Order deleted successfully.");
            listOrder_out();
            showAlert(Alert.AlertType.INFORMATION, "Deletion Successful", "The order was deleted successfully!");
        } else {
            System.out.println("Failed to delete order.");
            showAlert(Alert.AlertType.ERROR, "Deletion Error", "Failed to delete the order.");
        }
    }


    private void totalPrice() {
        double total = 0;
        int quantity = 0;

        if (order_outObservableList != null) {
            for (Order_out order : order_outObservableList) {
                if (order != null) {
                    total += order.getPrice();
                    quantity += order.getQuantity();
                }
            }
        }
        double finalTotal = total;
        int finalQuantity = quantity;

        Platform.runLater(() -> {
            totalprice.setText(String.format("%,.0f", finalTotal));
            totalquantity.setText(String.format("%d", finalQuantity));
        });
    }
    public boolean removeOrder_out(int orderID) {
        String sql = "DELETE FROM orders WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, orderID);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting order", e);
        }
    }
    public boolean addBill(Bills bills) {
        try {
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO bill(date, total, status) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setTimestamp(1, new Timestamp(bills.getDate().getTime()));
            statement.setDouble(2, bills.getTotalPrice());
            statement.setInt(3, 0);

            int check = statement.executeUpdate();
            if (check == 0) {
                System.out.println("Failed to insert into bill table");
                conn.rollback();
                return false;
            }
            ResultSet rs = statement.getGeneratedKeys();
            int newBillID = 0;
            if (rs.next()) {
                newBillID = rs.getInt(1);
            } else {
                System.out.println("No bill ID returned. Rolling back.");
                conn.rollback();
                return false;
            }
            PreparedStatement updateStatement = conn.prepareStatement(
                    "UPDATE orders SET billID = ? WHERE order_details = ?"
            );
            updateStatement.setInt(1, newBillID);
            updateStatement.setInt(2, 1);
            int updateCheck = updateStatement.executeUpdate();
            if (updateCheck == 0) {
                System.out.println("Failed to update order_out with bill ID. Rolling back.");
                conn.rollback();
                return false;
            }
            PreparedStatement updateStatusStatement = conn.prepareStatement(
                    "UPDATE orders SET order_details = 0 WHERE status = 0"
            );
            int updateStatusCheck = updateStatusStatement.executeUpdate();
            if (updateStatusCheck == 0) {
                System.out.println("No orders updated to reset order details. Rolling back.");
                conn.rollback();
                return false;
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }
    private void updateOrder_out(int orderID) {
        String sql = "UPDATE orders SET status = ? WHERE order_details = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, 0); // Set status to 0
            statement.setInt(2, 1);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Order status updated successfully for order ID: " + orderID);
            } else {
                System.out.println("No order found with the specified ID: " + orderID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating order status", e);
        }
    }
    @FXML
    public void handlePlaceOrder(ActionEvent event) {
        Order_out orderOut = ordertable.getSelectionModel().getSelectedItem();
        updateOrder_out(orderOut.getOrder_id());
        double total = Double.parseDouble(totalprice.getText().replace(",", ""));
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Bills bill = new Bills();
        bill.setTotalPrice(total);
        bill.setDate(currentTimestamp);

        boolean result = addBill(bill);

        if (result) {
            System.out.println("Order placed successfully");
            listOrder_out();
            showAlert(Alert.AlertType.INFORMATION, "Order Placed", "The order was placed successfully!");
        } else {
            System.out.println("Failed to place the order");
            showAlert(Alert.AlertType.ERROR, "Order Placement Failed", "Failed to place the order.");
        }
    }

    public List<Order_out> showOrder_out(int orders) {
        List<Order_out> order_outList = new ArrayList<>();

        if (conn == null) {
            System.out.println("Database connection is null.");
            return order_outList;
        }

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM orders WHERE order_details = ?");
            statement.setInt(1, orders);

            ResultSet rs = statement.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No data found for order_details = " + orders);
            }

            while (rs.next()) {
                Order_out order = new Order_out();
                order.setOrder_id(rs.getInt("id"));
                order.setProductName(rs.getString("productName"));
                order.setQuantity(rs.getInt("quantity"));
                order.setPrice(rs.getInt("price"));
                order.setProductID(rs.getInt("productID"));

                order_outList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order_outList;
    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
