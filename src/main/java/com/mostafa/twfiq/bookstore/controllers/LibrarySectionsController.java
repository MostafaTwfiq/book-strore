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
public class LibrarySectionsController implements Initializable, MainViewObserver {

    private void setupAddHandWrittenBookBtn() {
        addHandWrittenBookBtn.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Path.of(FXMLViewPath.addBookViewFXML).toAbsolutePath().toUri().toURL());
                fxmlLoader.setControllerFactory(BookStoreApplication.getApplicationContext()::getBean);
                Node parentLayout = fxmlLoader.load();
                ((AddBookViewController) fxmlLoader.getController()).setControllerData(user, BookCategory.HAND_WRITTEN, this);
                BookStoreApplication.pushViewOnStackAndChange(parentLayout);
            } catch (Exception ignored) {ignored.printStackTrace();}
        });
    }

    private void setupAddTextBookBtn() {
        addTextBookBtn.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Path.of(FXMLViewPath.addBookViewFXML).toAbsolutePath().toUri().toURL());
                fxmlLoader.setControllerFactory(BookStoreApplication.getApplicationContext()::getBean);
                Node parentLayout = fxmlLoader.load();
                ((AddBookViewController) fxmlLoader.getController()).setControllerData(user, BookCategory.TEXT, this);
                BookStoreApplication.pushViewOnStackAndChange(parentLayout);
            } catch (Exception ignored) {}
        });
    }

    private void setupAddPracticalBookBtn() {
        addPracticalBookBtn.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Path.of(FXMLViewPath.addBookViewFXML).toAbsolutePath().toUri().toURL());
                fxmlLoader.setControllerFactory(BookStoreApplication.getApplicationContext()::getBean);
                Node parentLayout = fxmlLoader.load();
                ((AddBookViewController) fxmlLoader.getController()).setControllerData(user, BookCategory.PRACTICAL_MANUAL, this);
                BookStoreApplication.pushViewOnStackAndChange(parentLayout);
            } catch (Exception ignored) {}
        });
    }
    @FXML
    private JFXButton addHandWrittenBookBtn;

    @FXML
    private JFXButton addPracticalBookBtn;

    @FXML
    private JFXButton addTextBookBtn;

    @FXML
    private HBox handWrittenSection;

    @FXML
    private VBox parentLayout;

    @FXML
    private HBox practicalManualSection;

    @FXML
    private HBox textBooksSection;
    private User user;

    private List<Book> books;
    @Autowired
    private BookService bookService;
    private void clearSections() {
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

    private void loadCard(User user, Book book, HBox section) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Path.of(FXMLViewPath.libraryCardFXML).toAbsolutePath().toUri().toURL());
            fxmlLoader.setControllerFactory(BookStoreApplication.getApplicationContext()::getBean);
            Node parentLayout = fxmlLoader.load();
            ((LibraryCardController) fxmlLoader.getController()).setCardData(user, book, this);
            section.getChildren().add(parentLayout);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupAddHandWrittenBookBtn();
        setupAddPracticalBookBtn();
        setupAddTextBookBtn();
    }

    private void updateSections(List<Book> booksList) {
        clearSections();
        for (Book book : booksList) {
            loadCard(user, book, getSection(book.getCategory()));
        }
    }

    @Override
    public void updateSections(User user) {
        this.user = user;
        books = bookService.findUnsoldBooksByUserID(user.getId());
        updateSections(books);
    }

    @Override
    public void updateSections() {
        updateSections(user);
    }

    @Override
    public void search(String searchString) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getName().contains(searchString)) {
                filteredBooks.add(book);
            }
        }
        updateSections(filteredBooks);
    }
}
