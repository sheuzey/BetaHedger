package controllers; /**
 * Created by Stephen on 3/27/17.
 */

import com.etrade.etws.sdk.client.ClientRequest;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ConnectionModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private HBox menuHBox;
    @FXML
    private JFXButton menuFileButton, menuOrderButton, menuMarketDataButton;

    private JFXButton saveWorkspaceButton, loadWorkspaceButton, orderBlotterButton, newOrderButton, newQuoteButton;
    private JFXPopup menuFilePopup, menuOrderPopup, menuMarketDataPopup;

    private ClientRequest clientRequestWithAccessToken;
    private ConnectionModel connectionModel;
    private static final Logger logger = LogManager.getLogger(MainController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupMenuPopups();
        setupMenuListItems();
        showAccountInfo();

        connectionModel = (ConnectionModel)resources.getObject("model.ConnectionModel");
        logger.info(String.format("Received connection model from Login Controller {%s}", connectionModel));
        clientRequestWithAccessToken = connectionModel.getRequestWithAccessToken();
        logger.info(String.format("Request with Access Token set {%s}", clientRequestWithAccessToken));
    }

    //TODO: Setup the JFXPopups so that the objects are persisted
    private void setupMenuPopups(){

    }

    private void setupPopupForButton(Node node, JFXButton button, JFXPopup popup){
        logger.info(String.format("'%s' button clicked", button.getText()));
        popup.show(node,
                JFXPopup.PopupVPosition.TOP,
                JFXPopup.PopupHPosition.LEFT,
                button.getLayoutX(),
                button.getLayoutY() + button.getHeight());
    }

    //TODO Setup the main menu list
    private void setupMenuListItems(){

        /** Create Popup buttons for when the 'File' button is pressed */
        saveWorkspaceButton = new JFXButton("Save Workspace");
        loadWorkspaceButton = new JFXButton("Load Workspace");

        saveWorkspaceButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", saveWorkspaceButton.getText()));
        });
        loadWorkspaceButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", loadWorkspaceButton.getText()));
        });

        menuFileButton.setOnMouseClicked(event ->
                setupPopupForButton(menuHBox, menuFileButton, new JFXPopup(new VBox(saveWorkspaceButton, loadWorkspaceButton))));

        /** Create popup buttons for when the 'Orders' button is pressed */
        orderBlotterButton = new JFXButton("Order Blotter");
        newOrderButton = new JFXButton("New Order");

        newOrderButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", newOrderButton.getText()));
        });

        orderBlotterButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", orderBlotterButton.getText()));
        });

        menuOrderButton.setOnMouseClicked(event ->
                setupPopupForButton(menuHBox, menuOrderButton, new JFXPopup(new VBox(newOrderButton, orderBlotterButton))));

        /** Create popup buttons for when the 'Market Data' button is pressed */
        newQuoteButton = new JFXButton("New Quote");
        newQuoteButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", newQuoteButton.getText()));
        });

        menuMarketDataButton.setOnMouseClicked(event ->
                setupPopupForButton(menuHBox, menuMarketDataButton, new JFXPopup(new VBox(newQuoteButton))));

    }

    //TODO Create the layout for showing the user account
    private void showAccountInfo(){

    }

}
