package com.mostafa.twfiq.bookstore.controllers;

import com.mostafa.twfiq.bookstore.models.BookCategory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Scope("prototype")
@Controller
public class AddBookCardController implements Initializable {

    @FXML
    private ImageView addBtn;

    @FXML
    private VBox parentLayout;

    private BookCategory bookCategory;

    private void setupAddBtn() {
        addBtn.setOnMouseClicked(e -> {
            // TODO: Change view to add book view
        });
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
