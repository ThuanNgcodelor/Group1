package com.example.cp2396g11gr1.controller;
//@David Nguyen
import com.example.cp2396g11gr1.model.account.AccountDAO;
import com.example.cp2396g11gr1.model.account.AccountImple;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
public class LoginController {
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button btnLogin;
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
    public void Login(ActionEvent event)throws IOException {
        if (txtEmail.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi!", "Vui lòng nhập lại email.");
            return;
        }
        if (txtPassword.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi!", "Vui lòng nhập lại mật khẩu.");
            return;
        }
        AccountDAO accountDAO = new AccountImple();
        int role = accountDAO.checkLogin(txtEmail.getText(), txtPassword.getText());
        if (role == 0) {
            Stage adminStage = (Stage) btnLogin.getScene().getWindow();
            adminStage.close();
            FXMLLoader fxmlLoader  = new FXMLLoader(getClass().getResource("/com/example/cp2396g11gr1/admin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene  = new Scene(root);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin");
            adminStage.show();
        } else if (role == 1) {
            Stage staffStage = (Stage) btnLogin.getScene().getWindow();
            staffStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cp2396g11gr1/staff.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene  = new Scene(root);
            staffStage.setScene(scene);
            staffStage.setTitle("Staff");
            staffStage.show();
        }else {
            infoBox("Logins fails", null, "Fails");
        }
    }
}
