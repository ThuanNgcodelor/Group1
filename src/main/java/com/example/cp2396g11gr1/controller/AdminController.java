package com.example.cp2396g11gr1.controller;
//@David Nguyen
import com.example.cp2396g11gr1.model.Area.Area;
import com.example.cp2396g11gr1.model.Area.AreaDAO;
import com.example.cp2396g11gr1.model.Area.AreaImple;
import com.example.cp2396g11gr1.model.Chip.Chip;
import com.example.cp2396g11gr1.model.Chip.ChipDAO;
import com.example.cp2396g11gr1.model.Chip.ChipImple;
import com.example.cp2396g11gr1.model.account.AccountDAO;
import com.example.cp2396g11gr1.model.account.AccountImple;
import com.example.cp2396g11gr1.model.account.Accounts;
import com.example.cp2396g11gr1.model.category.Category;
import com.example.cp2396g11gr1.model.category.CategoryDAO;
import com.example.cp2396g11gr1.model.category.CategoryImple;
import com.example.cp2396g11gr1.model.product.ProductDAO;
import com.example.cp2396g11gr1.model.product.ProductImple;
import com.example.cp2396g11gr1.model.product.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
public class AdminController {
    @FXML
    private Button btnHome;
    @FXML
    private Button btnStaff;
    @FXML
    private ImageView images_add;
    @FXML
    private Button btnProduct;
    @FXML
    private AnchorPane home;
    @FXML
    private AnchorPane product;
    @FXML
    private AnchorPane staff;
    @FXML
    private TextField productSearch;
    @FXML
    private TableView<Products> productTable;
    @FXML
    private TableColumn<Products, String> productTableArea;
    @FXML
    private TableColumn<Products, String> productTableCategory;
    @FXML
    private TableColumn<Products, String> productTableChip;
    @FXML
    private TableColumn<Products, Integer> productTableID;
    @FXML
    private TableColumn<Products, String> productTableName;
    @FXML
    private TableColumn<Products, Double> productTablePrice;
    @FXML
    private TableColumn<Products, Integer> productTableStock;
    @FXML
    private Label fullName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtProductName;
    @FXML
    private TextField txtStock;
    @FXML
    private ComboBox<Category> comBoxCategory;
    @FXML
    private ComboBox<Area> comBoxArea;
    @FXML
    private ComboBox<Chip> comBoxChip;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtFullname;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtAddress;
    @FXML
    private ComboBox<Integer> comBoxRole;
    @FXML
    private TableView<Accounts> staffTable;
    @FXML
    private TableColumn<Accounts, String> staffTableAddress;
    @FXML
    private TableColumn<Accounts, String> staffTableEmail;
    @FXML
    private TableColumn<Accounts, String> staffTableFullName;
    @FXML
    private TableColumn<Accounts, String> staffTablePassword;
    @FXML
    private TableColumn<Accounts, Integer> staffTablePhone;
    @FXML
    private TableColumn<Accounts, Integer> staffTableID;
    @FXML
    private TableColumn<Accounts, Integer> staffTableRole;
    @FXML
    private TextField staffSearch;
    @FXML
    private Button logOut;
    @FXML
    private void showStaff() {
        AccountDAO accountDAO = new AccountImple();
        List<Accounts> accounts = accountDAO.AllStaff();
        ObservableList<Accounts> accountsObservableList = FXCollections.observableList(accounts);
        staffTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        staffTableFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        staffTableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        staffTablePassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        staffTablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        staffTableAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        staffTableRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        staffTable.setItems(accountsObservableList);
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
    private void addStaff(ActionEvent event) {
        try {
            String fullname = txtFullname.getText();
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            String phone = txtPhone.getText();
            String address = txtAddress.getText();
            Integer role = comBoxRole.getValue();
            if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty() || role == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all required fields!");
                return;
            }
            Accounts newAccount = new Accounts();
            newAccount.setFullName(fullname);
            newAccount.setEmail(email);
            newAccount.setPassword(password);
            newAccount.setPhone(phone);
            newAccount.setAddress(address);
            newAccount.setRole(role);
            AccountDAO accountDAO = new AccountImple();
            boolean added = accountDAO.addAccount(newAccount);
            if (added) {
                showStaff();
                clearFields();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void updateStaff(ActionEvent event) {
        try {
            Accounts selectedAccount = staffTable.getSelectionModel().getSelectedItem();
            if (selectedAccount == null) {
                showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a staff member to update.");
                return;
            }
            selectedAccount.setFullName(txtFullname.getText());
            selectedAccount.setEmail(txtEmail.getText());
            selectedAccount.setPassword(txtPassword.getText());
            selectedAccount.setPhone(txtPhone.getText());
            selectedAccount.setAddress(txtAddress.getText());
            selectedAccount.setRole(comBoxRole.getValue());
            AccountDAO accountDAO = new AccountImple();
            boolean updated = accountDAO.updateAccount(selectedAccount);
            if (updated) {
                showStaff();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Update Failed", "Failed to update the staff member.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void deleteStaff(ActionEvent event) {
        Accounts selectedAccount = staffTable.getSelectionModel().getSelectedItem();
        if (selectedAccount == null) {
            showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a staff member to delete.");
            return;
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete this staff member?");
        confirmationAlert.setContentText("This action cannot be undone.");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AccountDAO accountDAO = new AccountImple();
            boolean deleted = accountDAO.deleteAccount(selectedAccount);
            if (deleted) {
                showStaff();
                clearFields();
            }
        }
    }
    private void clearFields() {
        txtFullname.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtPhone.clear();
        txtAddress.clear();
        comBoxRole.getSelectionModel().clearSelection();
    }
    @FXML
    private  void initialize() {
        ComBoxArea();
        ComBoxCategory();
        ComBoxChip();
        showProducts();
        displayUsername();
        RoleComBoBox();
        staffTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedAccount) -> {
            if (selectedAccount != null) {
                txtFullname.setText(selectedAccount.getFullName());
                txtEmail.setText(selectedAccount.getEmail());
                txtPassword.setText(selectedAccount.getPassword());
                txtPhone.setText(selectedAccount.getPhone());
                txtAddress.setText(selectedAccount.getAddress());
                comBoxRole.setValue(selectedAccount.getRole());
            }
        });
        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedProduct) -> {
            if (selectedProduct != null) {
                txtProductName.setText(selectedProduct.getProductName());
                txtPrice.setText(String.valueOf(selectedProduct.getPrice()));
                txtStock.setText(String.valueOf(selectedProduct.getQuantity()));
                String image = selectedProduct.getImage();
                if (image != null && !image.isEmpty()) {
                    Image images = new Image(new File(image).toURI().toString(), 136, 128, false, true);
                    images_add.setImage(images);
                }
            }
        });
    }
    public void displayUsername() {
        fullName.setText(String.valueOf(Data.fullName));
    }
    @FXML
    private void narBar(ActionEvent event){
        if (event.getSource() == btnHome) {
            home.setVisible(true);
            product.setVisible(false);
            staff.setVisible(false);
        } else if (event.getSource() == btnProduct) {
            home.setVisible(false);
            product.setVisible(true);
            staff.setVisible(false);
            ComBoxArea();
            ComBoxCategory();
            ComBoxArea();
        } else if (event.getSource() == btnStaff) {
            home.setVisible(false);
            product.setVisible(false);
            staff.setVisible(true);
            showStaff();
        }
    }
    private void showProducts() {
        ProductDAO productDAO = new ProductImple();
        List<Products> products = productDAO.AllProducts();
        ObservableList<Products> productsObservableList = FXCollections.observableList(products);
        productTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productTableName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTableStock.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productTableArea.setCellValueFactory(new PropertyValueFactory<>("areaName"));
        productTableCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        productTableChip.setCellValueFactory(new PropertyValueFactory<>("chipName"));
        productTable.setItems(productsObservableList);
    }
    @FXML
    private void searchProducts(ActionEvent event) {
        String keyword = productSearch.getText();
        ProductDAO productDAO = new ProductImple();
        List<Products> filteredProducts = productDAO.findProducts(keyword);
        ObservableList<Products> productsObservableList = FXCollections.observableList(filteredProducts);
        productTable.setItems(productsObservableList);
    }
    @FXML
    private void searchStaff(ActionEvent event){
        String keyword = staffSearch.getText();
        AccountDAO accountDAO = new AccountImple();
        List<Accounts> filteredStaff = accountDAO.findAccountByEmail(keyword);
        ObservableList<Accounts> staffObservableList = FXCollections.observableList(filteredStaff);
        staffTable.setItems(staffObservableList);
    }
    @FXML
    private void deleteStaffAction(ActionEvent event) {
        Products selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a product to delete.");
            return;
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Delete");
        confirmationAlert.setHeaderText("Do you want to delete this product?");
        confirmationAlert.setContentText("Product: " + selectedProduct.getProductName());
        ButtonType buttonYes = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonNo = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == buttonYes) {
            ProductDAO productDAO = new ProductImple();
            boolean deleted = productDAO.deleteProducts(selectedProduct);
            if (deleted) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product deleted successfully.");
                showProducts();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the product.");
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Cancelled", "Product deletion cancelled.");
        }
    }
    @FXML
    private void addProducts(ActionEvent event) {
        try {
            String productName = txtProductName.getText();
            Category category = comBoxCategory.getSelectionModel().getSelectedItem();
            Area area = comBoxArea.getSelectionModel().getSelectedItem();
            Chip chip = comBoxChip.getSelectionModel().getSelectedItem();
            double price = 0;
            try {
                price = Double.parseDouble(txtPrice.getText());
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Invalid price", "Please enter a valid price");
                return;
            }
            int stock = 0;
            try {
                stock = Integer.parseInt(txtStock.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid stock", "Please enter a valid stock");
                return;
            }
            if (productName.isEmpty() || category == null || area == null || chip == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all required fields!");
                return;
            }
            if (Data.path == null || Data.path.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please add photo!");
                return;
            }
            String images = Data.path;
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm product addition");
            confirmationAlert.setHeaderText("Would you like to add this product?");
            confirmationAlert.setContentText("Product: " + productName);
            ButtonType buttonYes = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonNo = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(buttonYes, buttonNo);
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
                Products newProducts = new Products();
                newProducts.setProductName(productName);
                newProducts.setAreaID(area.getId());
                newProducts.setChipID(chip.getId());
                newProducts.setCategoryID(category.getId());
                newProducts.setQuantity(stock);
                newProducts.setPrice(price);
                newProducts.setImage(images);
                ProductDAO productDAO = new ProductImple();
                boolean added = productDAO.addProducts(newProducts);
                if (added) {
                    showProducts();
                    clear();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    javafx.scene.image.Image image;
    public void addImages(ActionEvent event) {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));
        File file = openFile.showOpenDialog(product.getScene().getWindow());
        if (file != null) {
            Data.path = file.getAbsolutePath();
            image = new javafx.scene.image.Image(file.toURI().toString(),136,128,false,true);
            images_add.setImage(image);
        }
    }
    @FXML
    private void updateProducts(ActionEvent event) {
        try {
            Products selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a product to update!");
                return;
            }
            String productName = txtProductName.getText();
            Category category = comBoxCategory.getSelectionModel().getSelectedItem();
            Area area = comBoxArea.getSelectionModel().getSelectedItem();
            Chip chip = comBoxChip.getSelectionModel().getSelectedItem();
            double price;
            try {
                price = Double.parseDouble(txtPrice.getText());
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Invalid price", "Please enter a valid price");
                return;
            }
            int stock;
            try {
                stock = Integer.parseInt(txtStock.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid stock", "Please enter a valid stock");
                return;
            }
            if (productName.isEmpty() || category == null || area == null || chip == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all required fields!");
                return;
            }
            String images = Data.path;
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm product update");
            confirmationAlert.setHeaderText("Would you like to update this product?");
            confirmationAlert.setContentText("Product: " + productName);

            ButtonType buttonYes = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonNo = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(buttonYes, buttonNo);
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
                selectedProduct.setProductName(productName);
                selectedProduct.setAreaID(area.getId());
                selectedProduct.setChipID(chip.getId());
                selectedProduct.setCategoryID(category.getId());
                selectedProduct.setQuantity(stock);
                selectedProduct.setPrice(price);
                selectedProduct.setImage(images);
                ProductDAO productDAO = new ProductImple();
                boolean updated = productDAO.updateProducts(selectedProduct);
                if (updated) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Product updated successfully!");
                    clear();
                    showProducts();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update product!");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void clear(){
        txtProductName.clear();
        txtStock.clear();
        txtPrice.clear();
        comBoxArea.getSelectionModel().clearSelection();
        comBoxChip.getSelectionModel().clearSelection();
        comBoxCategory.getSelectionModel().clearSelection();
    }
    private void ComBoxCategory(){
        if(comBoxCategory != null){
            CategoryDAO categoryDAO = new CategoryImple();
            List<Category>categories = categoryDAO.getAllCategory();
            ObservableList<Category> categoryObservableList = FXCollections.observableList(categories);
            comBoxCategory.setItems(categoryObservableList);
        }
    }
    private void ComBoxArea(){
        if(comBoxArea != null){
            AreaDAO areaDAO = new AreaImple();
            List<Area> areas = areaDAO.GetAllArea();
            ObservableList<Area> areaObservableList = FXCollections.observableList(areas);
            comBoxArea.setItems(areaObservableList);
        }
    }
    private void ComBoxChip(){
        if(comBoxChip != null){
            ChipDAO chipDAO = new ChipImple();
            List<Chip> chips = chipDAO.getAllCategory();
            ObservableList<Chip> chipObservableList = FXCollections.observableList(chips);
            comBoxChip.setItems(chipObservableList);
        }
    }
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void Area(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cp2396g11gr1/Area.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void Chip(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cp2396g11gr1/Chip.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    private void Category(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cp2396g11gr1/Category.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void RoleComBoBox() {
        comBoxRole.getItems().addAll(0, 1);

        comBoxRole.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer role) {
                return role == 0 ? "Admin" : "Staff";
            }

            @Override
            public Integer fromString(String string) {
                return null;
            }
        });

        comBoxRole.setPromptText("Select Role");
    }

}
