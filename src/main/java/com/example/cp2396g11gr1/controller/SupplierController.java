package com.example.cp2396g11gr1.controller;

import com.example.cp2396g11gr1.model.supplier.Supplier;
import com.example.cp2396g11gr1.model.supplier.SupplierImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class SupplierController {

    @FXML
    private TableColumn<Supplier, Integer> id;

    @FXML
    private TableColumn<Supplier, String> name;

    @FXML
    private TableColumn<Supplier, String> address;

    @FXML
    private TableColumn<Supplier, String> email;

    @FXML
    private TableColumn<Supplier, Integer> phone;

    @FXML
    private TextField nametxt;

    @FXML
    private TextField addresstxt;

    @FXML
    private TextField emailtxt;

    @FXML
    private TextField phonetxt;

    @FXML
    private TextField searchTxt;

    @FXML
    private TableView<Supplier> table;

    private final SupplierImpl supplierImpl = new SupplierImpl();
    private ObservableList<Supplier> supplierList;

    public void initialize() {
        // Set up the table columns
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        address.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        email.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));
        phone.setCellValueFactory(new PropertyValueFactory<>("supplierPhone"));

        loadSuppliers();

        // Add listener for row selection in the table
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
            }
        });
    }

    private void loadSuppliers() {
        supplierList = FXCollections.observableArrayList(supplierImpl.GetAllSupplier());
        table.setItems(supplierList);
    }

    @FXML
    private void addSupplier() {
        String name = nametxt.getText();
        String address = addresstxt.getText();
        String email = emailtxt.getText();
        int phone;
        try {
            phone = Integer.parseInt(phonetxt.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Phone number must be an integer.");
            return;
        }

        Supplier newSupplier = new Supplier(0, name, address, phone, email);
        if (supplierImpl.addSupplier(newSupplier)) {
            loadSuppliers();
            clearFields();
            showAlert("Success", "Supplier added successfully.");
        } else {
            showAlert("Error", "Failed to add supplier.");
        }
    }

    @FXML
    private void updateSupplier() {
        Supplier selectedSupplier = table.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            showAlert("No Selection", "Please select a supplier to update.");
            return;
        }

        selectedSupplier.setSupplierName(nametxt.getText());
        selectedSupplier.setSupplierAddress(addresstxt.getText());
        selectedSupplier.setSupplierEmail(emailtxt.getText());

        try {
            selectedSupplier.setSupplierPhone(Integer.parseInt(phonetxt.getText()));
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Phone number must be an integer.");
            return;
        }

        supplierImpl.updateSupplier(selectedSupplier);
        loadSuppliers();
        clearFields();
        showAlert("Success", "Supplier updated successfully.");
    }

    @FXML
    private void deleteSupplier() {
        Supplier selectedSupplier = table.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            showAlert("No Selection", "Please select a supplier to delete.");
            return;
        }

        if (supplierImpl.deleteSupplier(selectedSupplier)) {
            loadSuppliers();
            clearFields();
            showAlert("Success", "Supplier deleted successfully.");
        } else {
            showAlert("Error", "Failed to delete supplier.");
        }
    }

    @FXML
    private void searchSupplier() {
        String keyword = searchTxt.getText();
        supplierList = FXCollections.observableArrayList(supplierImpl.searchSupplier(keyword));
        table.setItems(supplierList);
    }

    private void populateFields(Supplier supplier) {
        nametxt.setText(supplier.getSupplierName());
        addresstxt.setText(supplier.getSupplierAddress());
        emailtxt.setText(supplier.getSupplierEmail());
        phonetxt.setText(String.valueOf(supplier.getSupplierPhone()));
    }

    private void clearFields() {
        nametxt.clear();
        addresstxt.clear();
        emailtxt.clear();
        phonetxt.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
