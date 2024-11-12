package com.example.cp2396g11gr1.controller;

import com.example.cp2396g11gr1.model.customer.CustomerImpl;
import com.example.cp2396g11gr1.model.customer.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    private Button btnSearch, btnAdd, btnUpdate, btnDelete;

    @FXML
    private TextField email, name, phone, address, searchField;

    @FXML
    private TableColumn<Customers, Integer> idColumn;

    @FXML
    private TableColumn<Customers, String> nameColumn, addressColumn, phoneColumn, emailColumn;

    @FXML
    private TableView<Customers> table;

    private CustomerImpl customerImpl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerImpl = new CustomerImpl();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadCustomers();

        btnSearch.setOnAction(event -> searchCustomers());
        btnAdd.setOnAction(event -> confirmAction("Add", this::addCustomer));
        btnUpdate.setOnAction(event -> confirmAction("Update", this::updateCustomer));
        btnDelete.setOnAction(event -> confirmAction("Delete", this::deleteCustomer));

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> loadSelectedCustomerDetails(newValue));
    }

    private void loadCustomers() {
        List<Customers> customersList = customerImpl.showAllCustomers();
        ObservableList<Customers> customersData = FXCollections.observableArrayList(customersList);
        table.setItems(customersData);
    }

    private void searchCustomers() {
        String keyword = searchField.getText();
        List<Customers> searchResults = customerImpl.search(keyword);
        ObservableList<Customers> searchData = FXCollections.observableArrayList(searchResults);
        table.setItems(searchData);
    }

    private void addCustomer() {
        Customers customer = new Customers();
        customer.setName(name.getText());
        customer.setAddress(address.getText());
        customer.setPhone(phone.getText());
        customer.setEmail(email.getText());
        if (customerImpl.addCustomer(customer)) {
            loadCustomers();
            clearFields();
        } else {
            showAlert("Error", "Could not add customer.");
        }
    }

    private void updateCustomer() {
        Customers selectedCustomer = table.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            selectedCustomer.setName(name.getText());
            selectedCustomer.setAddress(address.getText());
            selectedCustomer.setPhone(phone.getText());
            selectedCustomer.setEmail(email.getText());
            if (customerImpl.updateCustomer(selectedCustomer)) {
                loadCustomers();
                clearFields();
            } else {
                showAlert("Error", "Could not update customer.");
            }
        } else {
            showAlert("Warning", "Please select a customer to update.");
        }
    }

    private void deleteCustomer() {
        Customers selectedCustomer = table.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            if (customerImpl.deleteCustomer(selectedCustomer)) {
                loadCustomers();
                clearFields();
            } else {
                showAlert("Error", "Could not delete customer.");
            }
        } else {
            showAlert("Warning", "Please select a customer to delete.");
        }
    }

    private void confirmAction(String action, Runnable actionMethod) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(action + " Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to " + action.toLowerCase() + " this customer?");
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(yesButton, cancelButton);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                actionMethod.run();
            }
        });
    }

    private void loadSelectedCustomerDetails(Customers customer) {
        if (customer != null) {
            name.setText(customer.getName());
            address.setText(customer.getAddress());
            phone.setText(customer.getPhone());
            email.setText(customer.getEmail());
        }
    }

    private void clearFields() {
        name.clear();
        address.clear();
        phone.clear();
        email.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
