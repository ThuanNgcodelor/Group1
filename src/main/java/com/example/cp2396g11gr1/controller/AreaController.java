package com.example.cp2396g11gr1.controller;
import com.example.cp2396g11gr1.model.Area.Area;
import com.example.cp2396g11gr1.model.Area.AreaDAO;
import com.example.cp2396g11gr1.model.Area.AreaImple;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.util.Optional;
public class AreaController {
    @FXML
    private TextField areaName;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Area> table_area;
    @FXML
    private TableColumn<Area, Integer> table_AreaID;
    @FXML
    private TableColumn<Area, String> table_areaName;
    private ObservableList<Area> areaList;
    private FilteredList<Area> filteredList;
    @FXML
    private void initialize() {
        showArea();
        table_area.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showInputTouch(newValue);
            }
        });
        searchField.textProperty().addListener((obs, oldText, newText) -> search());
    }
    private void showArea() {
        AreaDAO areaDAO = new AreaImple();
        List<Area> areas = areaDAO.GetAllArea();
        areaList = FXCollections.observableArrayList(areas);
        filteredList = new FilteredList<>(areaList, b -> true);
        table_AreaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_areaName.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_area.setItems(filteredList);
    }
    private void showInputTouch(Area area) {
        areaName.setText(area.getName());
    }
    @FXML
    private void addArea() {
        String areaNameText = areaName.getText();
        if (areaNameText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Area name cannot be empty!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Add");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to add this area?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AreaDAO areaDAO = new AreaImple();
            Area newArea = new Area();
            newArea.setName(areaNameText);
            if (areaDAO.addArea(newArea)) {
                showArea();
                areaName.clear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Area added successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add area!");
            }
        }
    }
    @FXML
    private void updateArea() {
        Area selectedArea = table_area.getSelectionModel().getSelectedItem();
        if (selectedArea == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No area selected!");
            return;
        }
        String areaNameText = areaName.getText();
        if (areaNameText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Area name cannot be empty!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Update");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to update this area?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AreaDAO areaDAO = new AreaImple();
            selectedArea.setName(areaNameText);
            areaDAO.updateArea(selectedArea);
            showArea();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Area updated successfully!");
        }
    }
    @FXML
    private void deleteArea() {
        Area selectedArea = table_area.getSelectionModel().getSelectedItem();
        if (selectedArea == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No area selected!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this area?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AreaDAO areaDAO = new AreaImple();
            if (areaDAO.deleteArea(selectedArea)) {
                showArea();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Area deleted successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete area!");
            }
        }
    }
    private void search() {
        String keyword = searchField.getText().toLowerCase();
        filteredList.setPredicate(area -> {
            if (keyword.isEmpty()) {
                return true;
            }
            return area.getName().toLowerCase().contains(keyword);
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
