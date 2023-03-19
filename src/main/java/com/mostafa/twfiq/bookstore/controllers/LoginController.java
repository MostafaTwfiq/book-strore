package com.mostafa.twfiq.bookstore.controllers;

import com.jfoenix.controls.JFXButton;
import com.mostafa.twfiq.bookstore.BookStoreApplication;
import com.mostafa.twfiq.bookstore.models.User;
import com.mostafa.twfiq.bookstore.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

@Scope("prototype")
@Controller
public class LoginController implements Initializable {

    @FXML
    private Label errorLbl;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private AnchorPane parentLayout;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label registerBtn;

    @FXML
    private TextField userNameField;

    @Autowired
    private UserService userService;

    private void setupLoginBtn() {
        loginBtn.setOnAction(e -> {
            String userName = userNameField.getText();
            String password = passwordField.getText();
            if (userName.isBlank() || password.isBlank()) {
                showErrorLbl("Please enter a valid username and password");
                return;
            }
            try {
                List<User> user = userService.findUsersByName(userName);
                if (user.isEmpty() || !user.get(0).getPassword().equals(password)) {
                    showErrorLbl("Username or password isn't correct");
                    return;
                }
                FXMLLoader fxmlLoader = new FXMLLoader(Path.of(FXMLViewPath.mainViewFXML).toAbsolutePath().toUri().toURL());
                fxmlLoader.setControllerFactory(BookStoreApplication.getApplicationContext()::getBean);
                Node parentLayout = fxmlLoader.load();
                ((MainViewController) fxmlLoader.getController()).setupUserProfile(user.get(0));
                BookStoreApplication.changeView(parentLayout);
            } catch (Exception ignored) {
                showErrorLbl("Something went wrong !!");
            }
        });
    }

    private void setupRegisterBtn() {
        registerBtn.setOnMouseClicked(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Path.of(FXMLViewPath.registerFXML).toAbsolutePath().toUri().toURL());
                fxmlLoader.setControllerFactory(BookStoreApplication.getApplicationContext()::getBean);
                BookStoreApplication.changeView(fxmlLoader.load());
            } catch (IOException ex) {
                showErrorLbl("Something went wrong !!");
            }
        });
    }

    private void showErrorLbl(String errorMsg) {
        errorLbl.setText(errorMsg);
        errorLbl.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupLoginBtn();
        setupRegisterBtn();
    }
}
