package assegnamento2.assegnamento2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The class {@code Store} represent
 * the opening of the first screen of the GUI through the fxml files.
 * This class inherit from Application abstract class.
 *
 * @author Michele Cairone 284972 {@literal <}michele.cairone{@literal @}studenti.unipr.it{@literal >}
 *
 * @author Leonardo Minaudo 297792 {@literal <}leonardo.minaudo{@literal @}studenti.unipr.it{@literal >}
 */
public class Store extends Application {

    /**
     * This method is used to start the GUI (javaFX).
     *
     * @param stage It is Stage object represents the primary window of your JavaFX application.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Store.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(Objects.requireNonNull(Store.class.getResourceAsStream("/assegnamento2/assegnamento2/Pics/logo.png"))));
        stage.setResizable(false);
        stage.setTitle("Electronic Store");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * This method is used to execute the application.
     *
     * @param args the method does not requires arguments.
     */
    public static void main(String[] args) {

        launch(args);
    }

}