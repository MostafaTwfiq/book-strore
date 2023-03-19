package com.mostafa.twfiq.bookstore.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.mostafa.twfiq.bookstore.models.Book;
import com.mostafa.twfiq.bookstore.models.StoreBook;
import com.mostafa.twfiq.bookstore.models.User;
import com.mostafa.twfiq.bookstore.service.StoreBookService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Scope("prototype")
@Controller
public class LibraryCardController implements Initializable {

    MainViewObserver mainViewObserver;
    @FXML
    private Label authorName;
    @FXML
    private ImageView bookImage;
    @FXML
    private Label bookName;
    @FXML
    private VBox parentLayout;
    @FXML
    private JFXButton sellBtn;
    @Autowired
    private StoreBookService storeBookService;
    private JFXPopup popupOptions;
    private User user;
    private Book book;

    private void setupSellBtn() {
        sellBtn.setOnAction(e -> {
            popupOptions.show(sellBtn, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.LEFT);
        });
    }

    private void setupSellPopup() {
        popupOptions = new JFXPopup();

        TextField priceField = new TextField();
        priceField.setStyle("-fx-background-color: transparent");
        priceField.setPromptText("Price");
        JFXButton confirmBtn = new JFXButton("Confirm");
        VBox vBox = new VBox(priceField, confirmBtn);
        popupOptions.setPopupContent(vBox);

        confirmBtn.setCursor(Cursor.HAND);

        confirmBtn.setOnAction(e -> {
            if (priceField.getText().isBlank())
                return;
            try {
                storeBookService.saveStoreBook(new StoreBook(Double.parseDouble(priceField.getText()), book));
                popupOptions.hide();
                mainViewObserver.updateSections();
            } catch (Exception ignored) {}
        });
    }

    public void setCardData(User user, Book book, MainViewObserver mainViewObserver) {
        this.user = user;
        this.book = book;
        this.mainViewObserver = mainViewObserver;
        bookName.setText(book.getName());
        authorName.setText(book.getAuthor());
        bookImage.setImage(new Image(new File(book.getImagePath()).toURI().toString()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupSellPopup();
        setupSellBtn();
    }
}
