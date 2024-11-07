package com.example.cp2396g11gr1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StaffController implements Initializable {

    @FXML
    private Button Orders;

    @FXML
    private Button bills;

    @FXML
    private Button btnHome;

    @FXML
    private Label fullName;

    @FXML
    private Label fullName1;

    @FXML
    private AnchorPane home;

    @FXML
    private Button logOut;

    @FXML
    private Button stocks;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void LogOut(ActionEvent event) {
        try{
            Stage stage = (Stage) logOut.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cp2396g11gr1/login.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void buttonOrders(ActionEvent event){
        try{
            Main showOrder_out = new Main();
            Stage stage = new Stage();
            showOrder_out.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void Orders(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cp2396g11gr1/bill.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Stock(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cp2396g11gr1/stock.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
