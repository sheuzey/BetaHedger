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

        setupMenuListItems();
        setupMenuPopups();
        assignEventsForMenuItems();
        showAccountInfo();

        connectionModel = (ConnectionModel)resources.getObject("model.ConnectionModel");
        logger.info(String.format("Received connection model from Login Controller {%s}", connectionModel));
        clientRequestWithAccessToken = connectionModel.getRequestWithAccessToken();
        logger.info(String.format("Request with Access Token set {%s}", clientRequestWithAccessToken));
    }

    private void setupPopupForButton(Node node, JFXButton button, JFXPopup popup){
        logger.info(String.format("'%s' button clicked", button.getText()));
        popup.show(node,
                JFXPopup.PopupVPosition.TOP,
                JFXPopup.PopupHPosition.LEFT,
                button.getLayoutX(),
                button.getLayoutY() + button.getHeight());
    }

    private void setupMenuListItems(){

        // Create 'File' Menu items
        saveWorkspaceButton = new JFXButton("Save Workspace");
        loadWorkspaceButton = new JFXButton("Load Workspace");

        // Create 'Orders' Menu items
        orderBlotterButton = new JFXButton("Order Blotter");
        newOrderButton = new JFXButton("New Order");

        // Create 'Market Data' Menu items
        newQuoteButton = new JFXButton("New Quote");
    }

    private void setupMenuPopups(){
        menuFilePopup = new JFXPopup(new VBox(saveWorkspaceButton,loadWorkspaceButton));
        menuOrderPopup = new JFXPopup(new VBox(newOrderButton, orderBlotterButton));
        menuMarketDataPopup = new JFXPopup(new VBox(newQuoteButton));
    }

    private void assignEventsForMenuItems() {

        //'File' Menu item events
        saveWorkspaceButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", saveWorkspaceButton.getText()));
        });
        loadWorkspaceButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", loadWorkspaceButton.getText()));
        });

        menuFileButton.setOnMouseClicked(event ->
                setupPopupForButton(menuHBox, menuFileButton, menuFilePopup));


        //'Order' Menu item events
        newOrderButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", newOrderButton.getText()));
        });

        orderBlotterButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", orderBlotterButton.getText()));
        });

        menuOrderButton.setOnMouseClicked(event ->
                setupPopupForButton(menuHBox, menuOrderButton, menuOrderPopup));


        //'Order' Menu item events
        newQuoteButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", newQuoteButton.getText()));
        });

        menuMarketDataButton.setOnMouseClicked(event ->
                setupPopupForButton(menuHBox, menuMarketDataButton, menuMarketDataPopup));
    }


    //TODO Create the layout for showing the user account
    private void showAccountInfo(){

    }

}
