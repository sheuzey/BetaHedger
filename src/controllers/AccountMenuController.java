package controllers;

import com.etrade.etws.account.AccountPositionsResponse;
import com.etrade.etws.sdk.client.AccountsClient;
import com.etrade.etws.sdk.client.ClientRequest;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import model.Accounts.Account;
import model.Accounts.AccountListResponse;
import model.Accounts.Balances.BalanceResponse;
import model.Accounts.Balances.RecursiveBalance;
import model.ConnectionModel;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AccountMenuController implements Initializable {

    private static final Logger logger = LogManager.getLogger(AccountMenuController.class);

    @FXML
    private JFXTreeTableView<RecursiveBalance> accountsTreeTableView;
    @FXML
    private StackPane accountViewStackPane;

    private ConnectionModel connectionModel;
    private AccountListResponse accountList;
    private List<BalanceResponse> accountBalances;

    private AccountsClient accountsClient;
    private List<AccountPositionsResponse> accountPositions;
    private ClientRequest clientRequestWithAccessToken;

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

        setupAccountsTable();
    }

    private void setupAccountsTable() {

        //Setup accounts tree table columns with recursive account wrapper class
        JFXTreeTableColumn<RecursiveBalance, String> accountNameColumn = new JFXTreeTableColumn<>("Account");
        accountNameColumn.setPrefWidth(150);
        accountNameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<RecursiveBalance, String> param) -> {
            if (accountNameColumn.validateValue(param))
                return new ReadOnlyStringWrapper(param.getValue().getValue().getBalanceResponse().getAccountDesc());
            else return accountNameColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<RecursiveBalance, String> accountTypeColumn = new JFXTreeTableColumn<>("Type");
        accountTypeColumn.setPrefWidth(150);
        accountTypeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<RecursiveBalance, String> param) -> {
            if (accountTypeColumn.validateValue(param))
                return new ReadOnlyStringWrapper(param.getValue().getValue().getBalanceResponse().getAccountType());
            else return accountTypeColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<RecursiveBalance, String> accountNetValueColumn = new JFXTreeTableColumn<>("Net Value");
        accountNetValueColumn.setPrefWidth(150);
        accountNetValueColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<RecursiveBalance, String> param) -> {
            if (accountNetValueColumn.validateValue(param))
                return new ReadOnlyStringWrapper(param.getValue().getValue().getBalanceResponse().getComputedBalance().getRealTimeValues().getNetMv().toString());
                //return new ReadOnlyObjectWrapper<>(NumberFormat.getNumberInstance().format(param.getValue().getValue().getBalanceResponse().getComputedBalance().getRealTimeValues().getNetMv()));
            else return accountNetValueColumn.getComputedValue(param);
        });

        //Get accounts / balances
        ObservableList<RecursiveBalance> observableBalanceList = FXCollections.observableArrayList();
        Thread getAccountBalancesThread = new Thread(() -> {
            accountList = connectionModel.getAccountLists();
            accountBalances = new ArrayList<>();
            for (Account account : accountList.getAccounts()) {
                accountBalances.add(connectionModel.getAccountBalance(account));
            }

            //Create observable list of recursive accounts from the account list response
            for (BalanceResponse balanceResponse : accountBalances){
                observableBalanceList.add(new RecursiveBalance(balanceResponse));
            }
        });

        getAccountBalancesThread.setName("Get Account Balances Thread");
        getAccountBalancesThread.start();

        //Build tree
        final TreeItem<RecursiveBalance> rootTree = new RecursiveTreeItem<>(observableBalanceList, RecursiveTreeObject::getChildren);
        rootTree.setExpanded(true);
        accountsTreeTableView = new JFXTreeTableView<>(rootTree);

        accountsTreeTableView.setShowRoot(false);
        accountsTreeTableView.setEditable(false);
        accountsTreeTableView.getColumns().setAll(accountNameColumn,accountTypeColumn,accountNetValueColumn);

        accountViewStackPane.getChildren().add(accountsTreeTableView);
    }
}
