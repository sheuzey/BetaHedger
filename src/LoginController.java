/**
 * Created by Stephen on 3/6/17.
 */

import com.jfoenix.controls.JFXSpinner;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.net.URL;
import java.util.*;

public class LoginController implements Initializable{

    @FXML
    private WebView etradeWebView;
    @FXML
    private StackPane webViewStackPane;

    private WebEngine etradeWebEngine;
    private String verificationCodeFromCallback;
    private Label loadingLabel;
    private JFXSpinner loadingSpinner;
    private VBox loadingVBox;
    private final String ETRADE_LOGIN_ADDRESS_SUBSTRING = "authorize";
    private final String ETRADE_VERIFY_ADDRESS_SUBSTRING = "TradingAPICustomerInfo";
    private final String ETRADE_WELCOME_ADDRESS_SUBSTRING = "welcomecenter";
    private final String BETAHEDGER_ADDRESS_SUBSTRING = "betahedger";
    private final String OAUTH_VERIFIER = "oauth_verifier";
    private final String LOADING_VBOX_ID = "LoadingVboxId";

    private ConnectionModel connectionModel;

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Login Screen created, initializing 'loading' spinner and label...");
        this.initializeLoadingVBox();

        /**
         * Set client, request and environment and get verification URI
         */
        this.connectionModel = new ConnectionModel();
        String verificationURI = this.connectionModel.getVerificationURI().toString();

        this.etradeWebEngine = this.etradeWebView.getEngine();
        this.etradeWebEngine.load(verificationURI);

        etradeWebEngine.getLoadWorker().stateProperty().addListener(
                (observable, oldValue, newValue) -> {

                    /** Get current URL address */
                    String urlAddress = this.etradeWebEngine.getLocation();

                    logger.info(String.format("WebEngine state changed:\t {%s}\t-->\t{%s}\t{New Address: %s}", oldValue,newValue, urlAddress));

                    /**
                     * If the page succeeded in loading and is the welcome page (or not the login and verification site),
                     *      reload to get the verification site and make the verification site visible
                     *
                     * If the page did not succeed, remove the webview add the loading label (if the loading label isn't already shown)
                    */
                    if (Worker.State.SUCCEEDED.equals(newValue)) {
                        if (urlAddress.contains(this.ETRADE_WELCOME_ADDRESS_SUBSTRING) ||
                                (!urlAddress.contains(this.ETRADE_VERIFY_ADDRESS_SUBSTRING) && !urlAddress.contains(this.ETRADE_LOGIN_ADDRESS_SUBSTRING))) {
                            logger.info(String.format("Loaded an E*TRADE page. Reloading again to go to verification page {Current Address {%s}}", urlAddress));
                            this.etradeWebEngine.load(verificationURI);
                        } else {
                            this.webViewStackPane.getChildren().remove(this.webViewStackPane.lookup(String.format("#%s",this.LOADING_VBOX_ID)));
                            logger.info("Removed 'Loading' label from E*TRADE AnchorPane");

                            this.etradeWebView.setVisible(true);
                            logger.info("Made WebView visible again in the E*TRADE AnchorPane (to confirm BetaHedger access to E*TRADE account)");
                        }
                    } else {
                        if (this.etradeWebView.isVisible()){
                            this.etradeWebView.setVisible(false);
                            logger.info("Made WebView invisible");
                        }

                        if (this.webViewStackPane.lookup(String.format("#%s",this.LOADING_VBOX_ID)) == null) {

                            logger.info("Determining Loading VBox Label text...");
                            if (urlAddress.contains(this.ETRADE_VERIFY_ADDRESS_SUBSTRING))
                                this.setLoadingVBoxWithLabelText("Loading...");
                            else
                                this.setLoadingVBoxWithLabelText("Authenticating...");

                            this.webViewStackPane.getChildren().add(this.loadingVBox);
                            logger.info(String.format("Added 'Loading' VBox to E*TRADE AnchorPane with text {%s}\t{%s}",this.loadingLabel.getText(), loadingLabel.toString()));
                        }
                    }

                    /** If the page is the betahedger / callback url, grab the verification code */
                    if (urlAddress.contains(this.BETAHEDGER_ADDRESS_SUBSTRING)) {
                        logger.info(String.format("Received BetaHedger callback URL, getting parameters: {Callback URL {%s}}", urlAddress));
                        this.verificationCodeFromCallback = this.getQueryMapFromUrl(urlAddress).get(this.OAUTH_VERIFIER);
                        logger.info(String.format("Received verification code from callback {Callback URL {%s}} {Code {%s}}",
                                this.etradeWebEngine.getLocation(),
                                this.verificationCodeFromCallback));
                        this.authorizeCodeAndLaunchMainController();
                    }
                });
    }

    private void initializeLoadingVBox(){
        if (this.loadingVBox == null) {
            logger.info("Creating 'Loading' VBox for the E*TRADE AnchorPane...");
            this.loadingLabel = new Label();
            this.loadingSpinner = new JFXSpinner();
            this.loadingVBox = new VBox();

            this.loadingLabel.setFont(Font.font("Geneva", 25));

            this.loadingVBox.getChildren().add(this.loadingLabel);
            this.loadingVBox.getChildren().add(this.loadingSpinner);
            this.loadingVBox.setId(this.LOADING_VBOX_ID);
            this.loadingVBox.setAlignment(Pos.CENTER);
            logger.info(String.format("'Loading' VBox created {%s}", this.loadingVBox.toString()));
        }
    }

    private void setLoadingVBoxWithLabelText(String labelText){
        if (this.loadingVBox == null)
            this.initializeLoadingVBox();
        this.loadingLabel.setText(labelText);
        logger.info(String.format("Set 'Loading' label text to {%s}", labelText));
    }

    private void authorizeCodeAndLaunchMainController(){
        logger.info(String.format("Authorizing using passcode: {%s}", this.verificationCodeFromCallback));
        this.connectionModel.setAccessToken(this.verificationCodeFromCallback);
        logger.info("Success! Created Authorized Request");

        /**
         * Create Main Controller and pass Authorized Access Token / Token Secret
         */
        try {

            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("MainController.fxml"));
            BorderPane mainBorderPane = mainLoader.load();

            Stage mainStage = new Stage();
            mainStage.setTitle("BetaHedger");
            mainStage.setScene(new Scene(mainBorderPane));

            MainController mainController = mainLoader.getController();
            mainController.setClientRequestWithAccessToken(this.connectionModel.getRequestWithAccessToken());
            mainStage.show();

            //Get login window and hide
            Window loginWindow = this.etradeWebView.getScene().getWindow();
            loginWindow.hide();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    private Map<String, String> getQueryMapFromUrl(String url){
        try {
            /**
             * Split up the set of parameters in the URL from the rest of the URL via finding '?'
            */
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1 ) {
                String query = urlParts[1];
                /**
                 * If there are any parameters in the URL, split up their keys and values, and assign to the hashmap
                 */
                if (query.split("&").length > 1) {
                    Map<String, String> parameterMap = new HashMap<>();
                    for (String param : query.split("&")) {
                        String[] parameterPair = param.split("=");
                        String key = parameterPair[0];
                        String value = parameterPair[1];
                        parameterMap.put(key, value);
                        logger.info(String.format("Value added to URL parameter hashmap: {key {%s}} {value {%s}}", key,value));
                    }
                    return parameterMap;
                }
            }
            else {
                logger.info(String.format("No parameters from provided URL: {%s}",url));
            }
        }
        catch (Throwable e){
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }
}