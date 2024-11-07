package com.example.cp2396g11gr1.controller;

import com.example.cp2396g11gr1.model.Chip.Chip;
import com.example.cp2396g11gr1.model.Chip.ChipDAO;
import com.example.cp2396g11gr1.model.Chip.ChipImple;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.util.Optional;
public class ChipController {
    @FXML
    private TextField chipName;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Chip> table_chip;
    @FXML
    private TableColumn<Chip, Integer> table_ChipID;
    @FXML
    private TableColumn<Chip, String> table_chipName;
    private ObservableList<Chip> chipsList;
    private FilteredList<Chip> filteredList;
    @FXML
    private void initialize() {
        showChips();
        table_chip.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showInputTouch(newValue);
            }
        });
        searchField.textProperty().addListener((obs, oldText, newText) -> search());
    }
    private void showChips() {
        ChipDAO chipDAO = new ChipImple();
        List<Chip> chipList = chipDAO.getAllCategory();
        chipsList = FXCollections.observableArrayList(chipList);
        filteredList = new FilteredList<>(chipsList, b -> true);
        table_ChipID.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_chipName.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_chip.setItems(filteredList);
    }
    private void showInputTouch(Chip chip) {
        chipName.setText(chip.getName());
    }
    @FXML
    private void addChip() {
        String chipNameText = chipName.getText();
        if (chipNameText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Chip name cannot be empty!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Add");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to add this chip?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ChipDAO chipDAO = new ChipImple();
            Chip newChip = new Chip();
            newChip.setName(chipNameText);
            if (chipDAO.addCategory(newChip)) {
                showChips();
                chipName.clear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Chip added successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add chip!");
            }
        }
    }
    @FXML
    private void updateChip() {
        Chip selectedChip = table_chip.getSelectionModel().getSelectedItem();
        if (selectedChip == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No chip selected!");
            return;
        }
        String chipNameText = chipName.getText();
        if (chipNameText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Chip name cannot be empty!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Update");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to update this chip?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ChipDAO chipDAO = new ChipImple();
            selectedChip.setName(chipNameText);

            chipDAO.updateCategory(selectedChip);
            showChips();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Chip updated successfully!");
        }
    }
    @FXML
    private void deleteChip() {
        Chip selectedChip = table_chip.getSelectionModel().getSelectedItem();
        if (selectedChip == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No chip selected!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this chip?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ChipDAO chipDAO = new ChipImple();
            if (chipDAO.deleteCategory(selectedChip)) {
                showChips();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Chip deleted successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete chip!");
            }
        }
    }
    private void search() {
        String keyword = searchField.getText().toLowerCase();
        filteredList.setPredicate(chip -> {
            if (keyword.isEmpty()) {
                return true;
            }
            return chip.getName().toLowerCase().contains(keyword);
        });
    }
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
