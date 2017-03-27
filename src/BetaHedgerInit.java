/**
 * Created by Stephen Heuzey on 12/8/16.
 */

import org.apache.logging.log4j.*;
import javafx.event.ActionEvent;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;


public class BetaHedgerInit extends Application {

    private static final Logger logger = LogManager.getLogger(BetaHedgerInit.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.logger.info("Starting BetaHedger...");

        Parent root = FXMLLoader.load(getClass().getResource("LoginController.fxml"));

        primaryStage.setTitle("BetaHedger");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
