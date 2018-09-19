/**
 * Created by Stephen Heuzey on 12/8/16.
 */

import org.apache.logging.log4j.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;


public class GenesisInit extends Application {

    private static final Logger logger = LogManager.getLogger(GenesisInit.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        logger.info("Starting Genesis Application...");

        Parent root = FXMLLoader.load(getClass().getResource("views/LoginController.fxml"));
        root.getStylesheets().add(getClass().getResource("GenesisStyles.css").toExternalForm());

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Genesis");
        
        primaryStage.show();
    }
}
