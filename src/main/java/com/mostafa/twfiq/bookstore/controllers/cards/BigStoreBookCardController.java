package com.mostafa.twfiq.bookstore.controllers.cards;

import com.jfoenix.controls.JFXButton;
import com.mostafa.twfiq.bookstore.controllers.MainViewObserver;
import com.mostafa.twfiq.bookstore.models.Book;
import com.mostafa.twfiq.bookstore.models.StoreBook;
import com.mostafa.twfiq.bookstore.models.User;
import com.mostafa.twfiq.bookstore.service.StoreBookService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
public class BigStoreBookCardController implements Initializable, StoreBookCard {
    @FXML
    private Label authorName;
    @FXML
    private ImageView bookImage;
    @FXML
    private Label bookName;
    @FXML
    private JFXButton buyBtn;
    @FXML
    private VBox parentLayout;
    private StoreBook storeBook;

    private User user;

    private MainViewObserver mainViewObserver;

    @Autowired
    private StoreBookService storeBookService;

    private void setupBuyBtn() {
        buyBtn.setText("un-sell");
        buyBtn.setOnAction(e -> {
            storeBookService.delete(storeBook);
            mainViewObserver.updateSections();
        });
    }

    @Override
    public void setCardData(User user, StoreBook storeBook, MainViewObserver mainViewObserver) {
        this.user = user;
        this.storeBook = storeBook;
        this.mainViewObserver = mainViewObserver;
        Book book = storeBook.getBook();
        bookName.setText(book.getName());
        authorName.setText(book.getAuthor());
        bookImage.setImage(new Image(new File(book.getImagePath()).toURI().toString()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupBuyBtn();
    }
}
