package mx.com.camacho.mdnotes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mx.com.camacho.mdnotes.constants.ApplicationIcon;
import mx.com.camacho.mdnotes.constants.StringConstants;

import java.io.IOException;
import java.util.Objects;

public class MarkdownNotes extends Application {

    private static MarkdownNotes mainInstance;
    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        mainInstance = this;
        this.fxmlLoader = new FXMLLoader(MarkdownNotes.class.getResource(StringConstants.NOTE_LIST_FXML));
        Scene scene = new Scene(fxmlLoader.load(), 550, 800);
        primaryStage.setTitle(StringConstants.APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
