package controllers; /**
 * Created by Stephen on 3/27/17.
 */

import com.etrade.etws.account.*;
import com.etrade.etws.sdk.client.AccountsClient;
import com.etrade.etws.sdk.client.ClientRequest;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Accounts.Account;
import model.Accounts.AccountListResponse;
import model.ConnectionModel;
import model.Accounts.RecursiveAccount;
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
    private JFXButton menuFileButton, menuOrderButton, menuMarketDataButton, menuAccountsButton;

    @FXML
    private StackPane mainStackPane;

    private JFXTreeTableView<RecursiveAccount> accountsTreeTableView;

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

        try {
            connectionModel = (ConnectionModel)resources.getObject("model.ConnectionModel");
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            logger.info("Error retrieiving connection model object, continuing without one (OK for testing, BAD if you actually want to trade!");
            connectionModel = new ConnectionModel();
        }
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

        saveWorkspaceButton.setOnMouseClicked(event -> {

        });
        loadWorkspaceButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked", loadWorkspaceButton.getText()));
        });

        menuFileButton.setOnMouseClicked(event ->
                setupPopupForButton(menuHBox, menuFileButton, menuFilePopup));


        //'Order' Menu item events
        newOrderButton.setOnMouseClicked(event -> {
            logger.info(String.format("'%s' button clicked, creating new order dialog...", newOrderButton.getText()));
            try {
                FXMLLoader newOrderLoader = new FXMLLoader(getClass().getResource("../views/NewOrderController.fxml"));
                BorderPane newOrderBorderPane = newOrderLoader.load();

                Stage newOrderStage = new Stage();
                newOrderStage.setTitle("New Order");
                newOrderStage.setScene(new Scene(newOrderBorderPane));
                newOrderStage.show();
            } catch (Exception e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }
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


        //'Account' Menu item event
        menuAccountsButton.setOnMouseClicked( event -> {
            logger.info(String.format("'%s' button clicked", menuAccountsButton.getText()));

            setupAccountsTable();
            if (mainStackPane.getChildren().contains(accountsTreeTableView)) {
                logger.info("Accounts table already added to stack pane");
            }
            else {
                mainStackPane.getChildren().add(accountsTreeTableView);
                logger.info("Accounts table added into stack pane");
            }
        });
    }

    private void setupAccountsTable() {

        //Setup accounts tree table columns with recursive account wrapper class
        JFXTreeTableColumn<RecursiveAccount, String> accountNameColumn = new JFXTreeTableColumn<>("Account");
        accountNameColumn.setPrefWidth(150);
        accountNameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<RecursiveAccount, String> param) -> {
            if (accountNameColumn.validateValue(param))
            return new ReadOnlyStringWrapper(param.getValue().getValue().getAccount().getAccountDesc());
            else return accountNameColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<RecursiveAccount, String> accountTypeColumn = new JFXTreeTableColumn<>("Type");
        accountTypeColumn.setPrefWidth(150);
        accountTypeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<RecursiveAccount, String> param) -> {
            if (accountTypeColumn.validateValue(param))
            return new ReadOnlyStringWrapper(param.getValue().getValue().getAccount().getAccountType());
            else return accountTypeColumn.getComputedValue(param);
        });

        /*JFXTreeTableColumn<RecursiveAccount, String> accountValueColumn = new JFXTreeTableColumn<>("Net Value");
        accountValueColumn.setPrefWidth(150);
        accountValueColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<RecursiveAccount, String> param) -> {
            if (accountValueColumn.validateValue(param))
            return new ReadOnlyObjectWrapper<>(NumberFormat.getCurrencyInstance().format(param.getValue().getValue().getAccount().getNetAccountValue()));
            else return accountValueColumn.getComputedValue(param);
        });*/

        //Create observable list of recursive accounts from the account list response
        ObservableList<RecursiveAccount> observableAccountList = FXCollections.observableArrayList();
        for (Account account : accountList.getAccounts()){
            observableAccountList.add(new RecursiveAccount(account));
        }

        //Build tree
        final TreeItem<RecursiveAccount> rootTree = new RecursiveTreeItem<>(observableAccountList, RecursiveTreeObject::getChildren);
        rootTree.setExpanded(true);

        accountsTreeTableView = new JFXTreeTableView<>(rootTree);
        accountsTreeTableView.setShowRoot(false);
        accountsTreeTableView.setEditable(false);
        accountsTreeTableView.getColumns().setAll(accountNameColumn,accountTypeColumn);
        //accountsTreeTableView.getColumns().setAll(accountNameColumn, accountTypeColumn, accountValueColumn);
    }


    //TODO Create the layout for showing the user account


    private void setAccountListBalancesAndPositions() {
        accountsClient = new AccountsClient(clientRequestWithAccessToken);
        accountBalances = new ArrayList<>();
        accountPositions = new ArrayList<>();
        Thread getAccountThread = new Thread(() -> {
            try {
                logger.info("Getting accounts, balances and positions...");
                accountList = new AccountListResponse();
                for (Account account : accountList.getAccounts()) {
                    logger.info(String.format("Received account : {Desc: %s} {Id: %s} {Registration Type: %s}",
                            account.getAccountDesc(),
                            account.getAccountId(),
                            account.getAccountType()));

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
