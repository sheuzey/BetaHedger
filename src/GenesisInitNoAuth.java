import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenesisInitNoAuth extends Application {

    private static final Logger logger = LogManager.getLogger(GenesisInitNoAuth.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        logger.info("Starting Genesis Application without authentication (FOR TESTING UI ONLY!)");

        Parent root = FXMLLoader.load(getClass().getResource("views/MainController.fxml"));
        root.getStylesheets().add(getClass().getResource("GenesisStyles.css").toExternalForm());

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("GenesisNoAuth");

        primaryStage.show();
    }
}
