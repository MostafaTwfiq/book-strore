package com.mostafa.twfiq.bookstore.controllers;

import com.jfoenix.controls.JFXToggleNode;
import com.mostafa.twfiq.bookstore.BookStoreApplication;
import com.mostafa.twfiq.bookstore.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

@Controller
public class MainViewController implements Initializable {
    @FXML
    private JFXToggleNode libraryToggleBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private ToggleGroup options;

    @FXML
    private AnchorPane parentLayout;

    @FXML
    private TextField searchField;

    @FXML
    private JFXToggleNode storeToggleBtn;

    @FXML
    private Label titleLbl;

    @FXML
    private Circle userImage;

    @FXML
    private Label userNameLbl;

    @FXML
    private AnchorPane viewLayout;

    private User user;

    private MainViewObserver mainViewObserver;

    private void setupLibraryToggleBtn() {
        libraryToggleBtn.setOnAction(e -> {
            titleLbl.setText("Library");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Path.of(FXMLViewPath.librarySectionsFXML).toAbsolutePath().toUri().toURL());
                fxmlLoader.setControllerFactory(BookStoreApplication.getApplicationContext()::getBean);
                Node parentLayout = fxmlLoader.load();
                mainViewObserver = fxmlLoader.getController();
                mainViewObserver.updateSections(user);
                changeViewLayout(parentLayout);
            } catch (IOException ignored) {
            }
        });
    }

    private void setupStoreToggleBtn() {
        storeToggleBtn.setOnAction(e -> {
            titleLbl.setText("Store");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Path.of(FXMLViewPath.storeSectionsFXML).toAbsolutePath().toUri().toURL());
                fxmlLoader.setControllerFactory(BookStoreApplication.getApplicationContext()::getBean);
                Node parentLayout = fxmlLoader.load();
                mainViewObserver = fxmlLoader.getController();
                mainViewObserver.updateSections(user);
                changeViewLayout(parentLayout);
            } catch (IOException ignored) {
            }
        });
    }

    private void setupLogoutBtn() {
        logoutBtn.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Path.of(FXMLViewPath.loginFXML).toAbsolutePath().toUri().toURL());
                fxmlLoader.setControllerFactory(BookStoreApplication.getApplicationContext()::getBean);
                BookStoreApplication.changeView(fxmlLoader.load());
            } catch (IOException ignored) {
            }
        });
    }

    private void setupSearchField() {
        searchField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                mainViewObserver.search(searchField.getText());
            }
        });
    }

    private void loadUserImage(String path) {
        try {
            Image image = new Image(new FileInputStream(path));
            ImagePattern pattern = new ImagePattern(image);
            userImage.setFill(pattern);
        } catch (Exception ignored) {
        }
    }

    public void setupUserProfile(User user) {
        this.user = user;
        loadUserImage(user.getImagePath());
        userNameLbl.setText(user.getName());
        storeToggleBtn.fire();
    }

    private void changeViewLayout(Node view) {
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
        viewLayout.getChildren().clear();
        viewLayout.getChildren().add(0, view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupLibraryToggleBtn();
        setupStoreToggleBtn();
        setupLogoutBtn();
        setupSearchField();
    }
}
