package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;


/**
 * Main Application.
 */
public class Main extends Application {

    static Stage stage;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        stage.setTitle("Startpunkt und Zwischenstationen angeben");
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("Destinations.fxml"));
        ScrollPane page;
        try (InputStream in = Main.class.getResourceAsStream("Destinations.fxml")) {
            page = (ScrollPane) loader.load(in);
        }
        Scene scene = new Scene(page, 800, 600);
        stage.setScene(scene);
        stage.sizeToScene();
        primaryStage.show();
    }


}
