package com.mostafa.twfiq.bookstore;

import com.mostafa.twfiq.bookstore.controllers.FXMLViewPath;
import com.mostafa.twfiq.bookstore.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Stack;

@Configuration
@SpringBootApplication(scanBasePackages = "com.mostafa.twfiq.bookstore")
public class BookStoreApplication extends Application {
    private final static AnchorPane sceneLayout = new AnchorPane();
    private static ConfigurableApplicationContext applicationContext;
    private static final Stack<Node> viewsStack = new Stack<>();

    public static void main(String[] args) {
        launch(args);
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void pushViewOnStackAndChange(Node view) {
        viewsStack.push(sceneLayout.getChildren().get(0));
        changeView(view);
    }

    public static void popViewFromStack() {
        Node view = viewsStack.pop();
        changeView(view);
    }
    public static void changeView(Node view) {
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
        sceneLayout.getChildren().clear();
        sceneLayout.getChildren().add(0, view);
    }

    @Override
    public void init() {
        applicationContext = SpringApplication.run(BookStoreApplication.class);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Path.of(FXMLViewPath.loginFXML).toAbsolutePath().toUri().toURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Scene scene = new Scene(BookStoreApplication.sceneLayout, 1000, 600);
        changeView(fxmlLoader.load());
        stage.setTitle("Book Store");
        stage.setScene(scene);
        stage.show();
    }
}
