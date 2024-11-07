package com.example.cp2396g11gr1.controller;

import com.example.cp2396g11gr1.model.bill.BillDAO;
import com.example.cp2396g11gr1.model.bill.BillImple;
import com.example.cp2396g11gr1.model.bill.Bills;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class BillController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBills();
        showDetailsButton.setOnAction(event -> {
            Bills selectedBill = billTable.getSelectionModel().getSelectedItem();
            if (selectedBill != null) {
                showOrderDetails(selectedBill);
            }
        });
    }

    @FXML
    private TableView<Order_out> DetailTable;

    @FXML
    private TableView<Bills> billTable;

    @FXML
    private TableColumn<Order_out, Integer> DetailTableID;

    @FXML
    private TableColumn<Order_out, String> DetailTableName;

    @FXML
    private TableColumn<Order_out, Integer> DetailTablePrice;

    @FXML
    private TableColumn<Order_out, Integer> DetailTableQuantity;

    @FXML
    private TableColumn<Bills, Integer> billTableID;

    @FXML
    private TableColumn<Bills, Date> billTableDate;

    @FXML
    private TableColumn<Bills, Integer> billTableTotal;

    @FXML
    private Button showDetailsButton;

    public void showBills() {
        BillDAO billDAO = new BillImple();
        List<Bills> bills = billDAO.getAllBills();
        ObservableList<Bills> billList = FXCollections.observableArrayList(bills);

        billTableID.setCellValueFactory(new PropertyValueFactory<>("BillID"));
        billTableDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        billTableTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        billTable.setItems(billList);
    }

    private void showOrderDetails(Bills selectedBill) {
        BillDAO billDAO = new BillImple();
        List<Order_out> order_outList = billDAO.showDetailsBill(selectedBill);
        ObservableList<Order_out> orderOutList = FXCollections.observableArrayList(order_outList);

        DetailTableID.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        DetailTableName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        DetailTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        DetailTableQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        DetailTable.setItems(orderOutList);

    }


}
