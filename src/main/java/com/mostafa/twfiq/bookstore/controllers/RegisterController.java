package com.mostafa.twfiq.bookstore.controllers;

import com.jfoenix.controls.JFXButton;
import com.mostafa.twfiq.bookstore.BookStoreApplication;
import com.mostafa.twfiq.bookstore.models.User;
import com.mostafa.twfiq.bookstore.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Scope("prototype")
@Controller
public class RegisterController implements Initializable {

    private final String imageExtensionsRegex = "^\\.(jpe?g|png|gif|bmp)$";
    @Autowired
    UserService userService;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorLbl;
    @FXML
    private AnchorPane parentLayout;
    @FXML
    private PasswordField passwordField;
    @FXML
    private JFXButton registerBtn;
    @FXML
    private ImageView uploadImageBtn;
    @FXML
    private Circle userImage;
    @FXML
    private TextField userNameField;
    private String userImagePath;

    private void setupUploadImageBtn() {
        uploadImageBtn.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {

                String filePath = file.getPath();
                String fileExtension = filePath.substring(filePath.lastIndexOf('.'));
                if (!Pattern.matches(imageExtensionsRegex, fileExtension))
                    return;

                userImagePath = filePath;

                try {
                    Image image = new Image(new FileInputStream(userImagePath));
                    ImagePattern pattern = new ImagePattern(image);
                    userImage.setFill(pattern);
                } catch (Exception exception) {
                    userImagePath = null;
                }

            }
        });
    }

    private void setupRegisterBtn() {
        registerBtn.setOnAction(e -> {
            String userName = userNameField.getText();
            String password = passwordField.getText();
            String confirmationPassword = confirmPasswordField.getText();
            if (userName.isBlank() || password.isBlank()) {
                showErrorLbl("Please enter a valid username and password");
                return;
            } else if (!password.equals(confirmationPassword)) {
                showErrorLbl("Password fields need to be identical");
                return;
            } else if (!userService.findUsersByName(userName).isEmpty()) {
                showErrorLbl("Username is used");
                return;
            }

            try {
                userService.saveUser(new User(userName, password, userImagePath));
                gotoLoginView();
            } catch (Exception ignored) {
                showErrorLbl("Something went wrong !!");
            }
        });
    }

    private void showErrorLbl(String errorMsg) {
        errorLbl.setText(errorMsg);
        errorLbl.setVisible(true);
    }

    private void setupCancelBtn() {
        cancelBtn.setOnAction(e -> {
            gotoLoginView();
        });
    }

    private void gotoLoginView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Path.of(FXMLViewPath.loginFXML).toAbsolutePath().toUri().toURL());
            fxmlLoader.setControllerFactory(BookStoreApplication.getApplicationContext()::getBean);
            BookStoreApplication.changeView(fxmlLoader.load());
        } catch (IOException ex) {
            showErrorLbl("Something went wrong !!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUploadImageBtn();
        setupRegisterBtn();
        setupCancelBtn();
    }
}
