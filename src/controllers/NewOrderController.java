package controllers;

import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class NewOrderController implements Initializable {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private JFXToggleButton verbToggleButton;

    private static final Logger logger = LogManager.getLogger(NewOrderController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //mainBorderPane.setStyle("-fx-background-color: #D69A9A");
        verbToggleButton.setOnAction(event -> {
            if (verbToggleButton.isSelected()) {
                mainBorderPane.setStyle("-fx-background-color: #D69A9A");
                verbToggleButton.setText("Sell");
                logger.info("Verb set to Sell");
            }
            else {
                mainBorderPane.setStyle("-fx-background-color: #ADD1BE");
                verbToggleButton.setText("Buy");
                logger.info("Verb set to Buy");
            }
        });
    }
}
