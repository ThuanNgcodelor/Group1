package com.example.cp2396g11gr1.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.cp2396g11gr1.model.category.Category;
import com.example.cp2396g11gr1.model.category.CategoryDAO;
import com.example.cp2396g11gr1.model.category.CategoryImple;
import java.util.List;
import java.util.Optional;
public class CategoryController {
    @FXML
    private TextField CategoryName;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Category> table_category;
    @FXML
    private TableColumn<Category, Integer> table_CategoryID;
    @FXML
    private TableColumn<Category, String> table_categoryName;
    private ObservableList<Category> categoriesList;
    private FilteredList<Category> filteredList;
    @FXML
    private void initialize() {
        showCategory();
        table_category.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showInputTouch(newValue);
            }
        });
        searchField.textProperty().addListener((obs, oldText, newText) -> search());
    }
    private void showCategory() {
        CategoryDAO categoryDAO = new CategoryImple();
        List<Category> categoryList = categoryDAO.getAllCategory();
        categoriesList = FXCollections.observableArrayList(categoryList);
        filteredList = new FilteredList<>(categoriesList, b -> true);
        table_CategoryID.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_category.setItems(filteredList);
    }

    private void showInputTouch(Category category) {
        CategoryName.setText(category.getName());
    }
    @FXML
    private void addCategory() {
        String categoryNameText = CategoryName.getText();
        if (categoryNameText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Category name cannot be empty!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Add");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to add this category?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            CategoryDAO categoryDAO = new CategoryImple();
            Category newCategory = new Category();
            newCategory.setName(categoryNameText);
            if (categoryDAO.addCategory(newCategory)) {
                showCategory();
                CategoryName.clear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Category added successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add category!");
            }
        }
    }
    @FXML
    private void updateCategory() {
        Category selectedCategory = table_category.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No category selected!");
            return;
        }
        String categoryNameText = CategoryName.getText();
        if (categoryNameText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Category name cannot be empty!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Update");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to update this category?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            CategoryDAO categoryDAO = new CategoryImple();
            selectedCategory.setName(categoryNameText);

            categoryDAO.updateCategory(selectedCategory);
            showCategory();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Category updated successfully!");
        }
    }
    @FXML
    private void deleteCategory() {
        Category selectedCategory = table_category.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No category selected!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this category?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            CategoryDAO categoryDAO = new CategoryImple();
            if (categoryDAO.deleteCategory(selectedCategory)) {
                showCategory();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Category deleted successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete category!");
            }
        }
    }
    private void search() {
        String keyword = searchField.getText().toLowerCase();
        filteredList.setPredicate(category -> {
            if (keyword.isEmpty()) {
                return true;
            }
            return category.getName().toLowerCase().contains(keyword);
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
