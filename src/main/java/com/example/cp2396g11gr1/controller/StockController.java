package com.example.cp2396g11gr1.controller;

import com.example.cp2396g11gr1.model.product.ProductDAO;
import com.example.cp2396g11gr1.model.product.ProductImple;
import com.example.cp2396g11gr1.model.product.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StockController implements Initializable {
    @FXML
    private TableColumn<Products, Long> stockAmount;

    @FXML
    private TableColumn<Products, Integer> stockID;

    @FXML
    private TableColumn<Products, Integer> stockQuantity;

    @FXML
    private TableColumn<Products, Integer> stockSold;

    @FXML
    private TableView<Products> stockTable;

    @FXML
    private TableColumn<Products, String> stockProductName;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStock();
    }
    private void showStock(){
        ProductDAO productDAO = new ProductImple();
        List<Products> stockList = productDAO.showStock();
        ObservableList<Products> observableList = FXCollections.observableList(stockList);
        stockTable.setItems(observableList);
        stockID.setCellValueFactory(new PropertyValueFactory<>("id"));
        stockProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        stockQuantity.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        stockSold.setCellValueFactory(new PropertyValueFactory<>("stockSold"));
        stockAmount.setCellValueFactory(new PropertyValueFactory<>("stockAmount"));
    }
}
