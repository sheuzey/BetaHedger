package controllers; /**
 * Created by Stephen on 3/27/17.
 */

import com.etrade.etws.account.*;
import com.etrade.etws.sdk.client.AccountsClient;
import com.etrade.etws.sdk.client.ClientRequest;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ConnectionModel;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private HBox menuHBox;
    @FXML
    private JFXButton menuFileButton, menuOrderButton, menuMarketDataButton;

    private JFXButton saveWorkspaceButton, loadWorkspaceButton, orderBlotterButton, newOrderButton, newQuoteButton;
    private JFXPopup menuFilePopup, menuOrderPopup, menuMarketDataPopup;

    private AccountsClient accountsClient;
    private List<AccountBalanceResponse> accountBalances;
    private List<AccountPositionsResponse> accountPositions;
    private AccountListResponse accountList;
    private ClientRequest clientRequestWithAccessToken;
    private ConnectionModel connectionModel;
    private static final Logger logger = LogManager.getLogger(MainController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connectionModel = (ConnectionModel)resources.getObject("model.ConnectionModel");
        logger.info(String.format("Received connection model from Login Controller {%s}", connectionModel));
        clientRequestWithAccessToken = connectionModel.getRequestWithAccessToken();
        logger.info(String.format("Request with Access Token set {%s}", clientRequestWithAccessToken));

        setupMenuListItems();
        setupMenuPopups();
        assignEventsForMenuItems();

        //Initialize E*TRADE account objects
        setAccountListBalancesAndPositions();
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


    private void setAccountListBalancesAndPositions() {
        accountsClient = new AccountsClient(clientRequestWithAccessToken);
        accountBalances = new ArrayList<>();
        accountPositions = new ArrayList<>();
        Thread getAccountThread = new Thread(() -> {
            try {
                logger.info("Getting accounts, balances and positions...");
                accountList = accountsClient.getAccountList();
                for (Account account : accountList.getResponse()) {
                    logger.info(String.format("Received account : {Desc: %s} {Id: %s} {Margin Level: %s} {Net Account Value: %s} {Registration Type: %s}",
                            account.getAccountDesc(),
                            account.getAccountId(),
                            account.getMarginLevel(),
                            account.getNetAccountValue(),
                            account.getRegistrationType()));

                    AccountBalanceResponse balanceResponse = accountsClient.getAccountBalance(account.getAccountId());
                    accountBalances.add(balanceResponse);
                    Balance balance = balanceResponse.getAccountBalance();
                    logger.info(String.format("Received account balance : {Id: %s} {Total Securities Mkt Value: %s} {Net Cash %s}",
                            balanceResponse.getAccountId(),
                            balance.getTotalSecuritiesMktValue(),
                            balance.getNetCash()));

                    AccountPositionsResponse positionsResponse = accountsClient.getAccountPositions(account.getAccountId(),new AccountPositionsRequest());
                    accountPositions.add(positionsResponse);
                    logger.info(String.format("Received %s positions for account %s",
                            positionsResponse.getResponse().size(),
                            account.getAccountId()));
                }
            } catch (Throwable e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }
        });
        getAccountThread.setName("Get Accounts Info Thread");
        getAccountThread.start();
    }
}
