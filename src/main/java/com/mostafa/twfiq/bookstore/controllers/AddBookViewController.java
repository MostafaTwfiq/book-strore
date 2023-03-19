package com.mostafa.twfiq.bookstore.controllers;

import com.jfoenix.controls.JFXButton;
import com.mostafa.twfiq.bookstore.BookStoreApplication;
import com.mostafa.twfiq.bookstore.models.Book;
import com.mostafa.twfiq.bookstore.models.BookCategory;
import com.mostafa.twfiq.bookstore.models.User;
import com.mostafa.twfiq.bookstore.service.BookService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Scope("prototype")
@Controller
public class AddBookViewController implements Initializable {

    private final String imageExtensionsRegex = "^\\.(jpe?g|png|gif|bmp)$";
    @FXML
    private JFXButton addBtn;

    @FXML
    private TextField authorNameField;

    @FXML
    private ImageView bookImage;
    @FXML
    private TextField bookNameField;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private Label errorLbl;

    @FXML
    private AnchorPane parentLayout;

    @FXML
    private ImageView uploadImageBtn;

    private String bookImagePath;
    private BookCategory bookCategory;

    private User user;

    private MainViewObserver mainViewObserver;

    @Autowired
    private BookService bookService;


    public void setControllerData(User user, BookCategory bookCategory, MainViewObserver mainViewObserver) {
        this.user = user;
        this.bookCategory = bookCategory;
        this.mainViewObserver = mainViewObserver;
    }

    private void setupUploadImageBtn() {
        uploadImageBtn.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {

                String filePath = file.getPath();
                String fileExtension = filePath.substring(filePath.lastIndexOf('.'));
                if (!Pattern.matches(imageExtensionsRegex, fileExtension))
                    return;

                bookImagePath = filePath;

                try {
                    Image image = new Image(new FileInputStream(bookImagePath));
                    bookImage.setImage(image);
                } catch (Exception exception) {
                    bookImagePath = null;
                }

            }
        });
    }

    private void setupAddBtn() {
        addBtn.setOnAction(e -> {
            String bookName = bookNameField.getText();
            String authorName = authorNameField.getText();
            if (bookName.isBlank() || authorName.isBlank()) {
                showErrorLbl("Please enter a valid book name and author name");
                return;
            } else if (bookImagePath == null || bookImagePath.isBlank()) {
                showErrorLbl("Please upload the book photo");
                return;
            }
            try {
                bookService.saveBook(new Book(bookName, authorName, bookImagePath, user, bookCategory));
                gotoMainView();
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
            gotoMainView();
        });
    }

    private void gotoMainView() {
        mainViewObserver.updateSections();
        BookStoreApplication.popViewFromStack();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUploadImageBtn();
        setupAddBtn();
        setupCancelBtn();
    }
}
