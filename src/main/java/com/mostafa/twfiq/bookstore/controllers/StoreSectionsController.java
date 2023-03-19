package com.mostafa.twfiq.bookstore.controllers;

import com.mostafa.twfiq.bookstore.BookStoreApplication;
import com.mostafa.twfiq.bookstore.controllers.cards.StoreBookCard;
import com.mostafa.twfiq.bookstore.models.BookCategory;
import com.mostafa.twfiq.bookstore.models.StoreBook;
import com.mostafa.twfiq.bookstore.models.User;
import com.mostafa.twfiq.bookstore.service.StoreBookService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Scope("prototype")
@Controller
public class StoreSectionsController implements Initializable, MainViewObserver {

    List<StoreBook> storeBooks;
    @FXML
    private HBox handWrittenSection;
    @FXML
    private HBox myBooksSection;
    @FXML
    private VBox parentLayout;
    @FXML
    private HBox practicalManualSection;
    @FXML
    private HBox textBooksSection;
    private User user;
    @Autowired
    private StoreBookService storeBookService;

    private void clearSections() {
        myBooksSection.getChildren().clear();
        handWrittenSection.getChildren().clear();
        practicalManualSection.getChildren().clear();
        textBooksSection.getChildren().clear();
    }

    private HBox getSection(BookCategory category) {
        return switch (category) {
            case HAND_WRITTEN -> handWrittenSection;
            case PRACTICAL_MANUAL -> practicalManualSection;
            default -> textBooksSection;
        };
    }

    private void loadCard(User user, StoreBook storeBook, String cardFXMLPath, HBox section) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Path.of(cardFXMLPath).toAbsolutePath().toUri().toURL());
            fxmlLoader.setControllerFactory(BookStoreApplication.getApplicationContext()::getBean);
            Node parentLayout = fxmlLoader.load();
            ((StoreBookCard) fxmlLoader.getController()).setCardData(user, storeBook, this);
            section.getChildren().add(parentLayout);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void updateSections(List<StoreBook> storeBooksList) {
        clearSections();
        for (StoreBook storeBook : storeBooksList) {
            if (storeBook.getBook().getUser().getId() == user.getId()) {
                loadCard(user, storeBook, FXMLViewPath.bigStoreBookCardFXML, myBooksSection);
            } else {
                loadCard(user, storeBook, FXMLViewPath.smallStoreBookCardFXML, getSection(storeBook.getBook().getCategory()));
            }
        }
    }

    @Override
    public void updateSections(User user) {
        this.user = user;
        storeBooks = storeBookService.findAll();
        updateSections(storeBooks);
    }

    @Override
    public void updateSections() {
        updateSections(user);
    }

    @Override
    public void search(String searchString) {
        List<StoreBook> filteredStoreBooks = new ArrayList<>();
        for (StoreBook storeBook : storeBooks) {
            if (storeBook.getBook().getName().contains(searchString)) {
                filteredStoreBooks.add(storeBook);
            }
        }
        updateSections(filteredStoreBooks);
    }
}
