package controllers; /**
 * Created by Stephen on 3/27/17.
 */

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.ConnectionModel;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    private static final Logger logger = LogManager.getLogger(MainController.class);

    @FXML
    private HBox menuHBox;
    @FXML
    private JFXButton menuFileButton, menuOrderButton, menuMarketDataButton, menuAccountsButton;

    private JFXButton saveWorkspaceButton, loadWorkspaceButton, orderBlotterButton, newOrderButton, newQuoteButton, accountsButton;
    private JFXPopup menuFilePopup, menuOrderPopup, menuMarketDataPopup, menuAccountsPopup;

    private ConnectionModel connectionModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            connectionModel = (ConnectionModel)resources.getObject("model.ConnectionModel");
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            logger.info("Error retrieiving connection model object, continuing without one (OK for testing, BAD if you actually want to trade!");
            connectionModel = new ConnectionModel();
        }
        logger.info(String.format("Received connection model from Login Controller {%s}", connectionModel));

        setupMenuListItems();
        setupMenuPopups();
        assignEventsForMenuItems();
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
        newOrderButton.setMaxWidth(Double.MAX_VALUE);
        newOrderButton.setAlignment(Pos.BASELINE_LEFT);

        // Create 'Market Data' Menu items
        newQuoteButton = new JFXButton("New Quote");

        // Create 'Accounts' Menu items
        accountsButton = new JFXButton("Account List");
    }

    private void setupMenuPopups(){
        menuFilePopup = new JFXPopup(new VBox(saveWorkspaceButton,loadWorkspaceButton));
        menuOrderPopup = new JFXPopup(new VBox(newOrderButton, orderBlotterButton));
        menuMarketDataPopup = new JFXPopup(new VBox(newQuoteButton));
        menuAccountsPopup = new JFXPopup(new VBox(accountsButton));
    }

    private void assignEventsForMenuItems() {

        //'File' Menu item events
        menuFileButton.setOnMouseClicked(event ->
                setupPopupForButton(menuHBox, menuFileButton, menuFilePopup));

        saveWorkspaceButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", saveWorkspaceButton.getText()));
        });

        loadWorkspaceButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", loadWorkspaceButton.getText()));
        });


        //'Order' Menu item events
        menuOrderButton.setOnMouseClicked(event ->
                setupPopupForButton(menuHBox, menuOrderButton, menuOrderPopup));

        newOrderButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked, creating new order dialog...", newOrderButton.getText()));
            showNewWindow("../views/NewOrderController.fxml", "New Order");
        });

        orderBlotterButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", orderBlotterButton.getText()));
        });


        //'Market Data' Menu item events
        menuMarketDataButton.setOnMouseClicked(event ->
                setupPopupForButton(menuHBox, menuMarketDataButton, menuMarketDataPopup));

        newQuoteButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", newQuoteButton.getText()));
        });


        //'Account' Menu item event
        menuAccountsButton.setOnMouseClicked(event ->
                setupPopupForButton(menuHBox, menuAccountsButton, menuAccountsPopup));


        accountsButton.setOnMouseClicked( event -> {
            logger.info(String.format("'%s' button clicked, creating new Account view...", menuAccountsButton.getText()));
            showNewWindow("../views/AccountMenuController.fxml", "Accounts");
        });
    }

    private void showNewWindow(String pathToFxmlView, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathToFxmlView), connectionModel);
            BorderPane borderPane = fxmlLoader.load();
            borderPane.getStylesheets().add(getClass().getResource("../GenesisStyles.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(borderPane));
            stage.show();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    //TODO Create the layout for showing the user account
}
