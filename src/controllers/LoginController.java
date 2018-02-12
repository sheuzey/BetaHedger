package controllers; /**
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
import model.ConnectionModel;
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
    private final String GENESIS_ADDRESS_SUBSTRING = "betahedger";      //TODO Update Callback URL w/E*TRADE Support
    private final String OAUTH_VERIFIER = "oauth_verifier";
    private final String LOADING_VBOX_ID = "LoadingVboxId";

    private ConnectionModel connectionModel;

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Login Screen created, initializing 'loading' spinner and label...");
        initializeLoadingVBox();

        /**
         * Set client, request and environment and get verification URI
         */
        connectionModel = new ConnectionModel();
        String verificationURI = connectionModel.getVerificationURI().toString();

        etradeWebEngine = etradeWebView.getEngine();

        etradeWebEngine.getLoadWorker().stateProperty().addListener(
                (observable, oldValue, newValue) -> {

                    /** Get current URL address */
                    String urlAddress = etradeWebEngine.getLocation();

                    logger.info(String.format("WebEngine state changed:\t {%s}\t-->\t{%s}\t{New Address: %s}", oldValue,newValue, urlAddress));

                    /**
                     * If the page succeeded in loading and is the welcome page (or not the login and verification site),
                     *      reload to get the verification site and make the verification site visible
                     *
                     * If the page did not succeed, remove the webview add the loading label (if the loading label isn't already shown)
                    */
                    if (Worker.State.SUCCEEDED.equals(newValue)) {
                        if (urlAddress.contains(ETRADE_WELCOME_ADDRESS_SUBSTRING) ||
                                (!urlAddress.contains(ETRADE_VERIFY_ADDRESS_SUBSTRING) && !urlAddress.contains(ETRADE_LOGIN_ADDRESS_SUBSTRING))) {
                            logger.info(String.format("Loaded an E*TRADE page. Reloading again to go to verification page {Current Address {%s}}", urlAddress));
                            etradeWebEngine.load(verificationURI);
                        } else {
                            webViewStackPane.getChildren().remove(webViewStackPane.lookup(String.format("#%s",LOADING_VBOX_ID)));
                            logger.info("Removed 'Loading' label from E*TRADE AnchorPane");

                            etradeWebView.setVisible(true);
                            logger.info("Made WebView visible again in the E*TRADE AnchorPane (to confirm Genesis access to E*TRADE account)");
                        }
                    } else {
                        if (etradeWebView.isVisible()){
                            etradeWebView.setVisible(false);
                            logger.info("Made WebView invisible");
                        }

                        if (webViewStackPane.lookup(String.format("#%s",LOADING_VBOX_ID)) == null) {

                            logger.info("Determining Loading VBox Label text...");
                            if (urlAddress.contains(ETRADE_VERIFY_ADDRESS_SUBSTRING))
                                setLoadingVBoxWithLabelText("Loading...");
                            else
                                setLoadingVBoxWithLabelText("Authenticating...");

                            webViewStackPane.getChildren().add(loadingVBox);
                            logger.info(String.format("Added 'Loading' VBox to E*TRADE AnchorPane with text {%s}\t{%s}",loadingLabel.getText(), loadingLabel.toString()));
                        }
                    }

                    /** If the page is the genesis / callback url, grab the verification code */
                    if (urlAddress.contains(GENESIS_ADDRESS_SUBSTRING)) {
                        logger.info(String.format("Received Genesis callback URL, getting parameters: {Callback URL {%s}}", urlAddress));
                        verificationCodeFromCallback = getQueryMapFromUrl(urlAddress).get(OAUTH_VERIFIER);
                        logger.info(String.format("Received verification code from callback {Callback URL {%s}} {Code {%s}}",
                                etradeWebEngine.getLocation(),
                                verificationCodeFromCallback));
                        authorizeCodeAndLaunchMainController();
                    }
                });
        etradeWebView.setVisible(false);

        setLoadingVBoxWithLabelText("Loading E*TRADE Login Screen...");
        webViewStackPane.getChildren().add(loadingVBox);
        etradeWebEngine.load(verificationURI);
    }

    private void initializeLoadingVBox(){
        if (loadingVBox == null) {
            logger.info("Creating 'Loading' VBox for the E*TRADE AnchorPane...");
            loadingLabel = new Label();
            loadingSpinner = new JFXSpinner();
            loadingVBox = new VBox();

            loadingLabel.setFont(Font.font("Geneva", 25));

            loadingVBox.getChildren().add(loadingLabel);
            loadingVBox.getChildren().add(loadingSpinner);
            loadingVBox.setId(LOADING_VBOX_ID);
            loadingVBox.setAlignment(Pos.CENTER);
            logger.info(String.format("'Loading' VBox created {%s}", loadingVBox.toString()));
        }
    }

    private void setLoadingVBoxWithLabelText(String labelText){
        if (loadingVBox == null)
            initializeLoadingVBox();
        loadingLabel.setText(labelText);
        logger.info(String.format("Set 'Loading' label text to {%s}", labelText));
    }

    private void authorizeCodeAndLaunchMainController(){
        logger.info(String.format("Authorizing using passcode: {%s}", verificationCodeFromCallback));
        connectionModel.setAccessToken(verificationCodeFromCallback);
        logger.info("Success! Created Authorized Request");

        /**
         * Create Main Controller and pass Authorized Access Token / Token Secret
         */
        try {

            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("../views/MainController.fxml"), connectionModel);
            BorderPane mainBorderPane = mainLoader.load();

            Stage mainStage = new Stage();
            mainStage.setTitle("Genesis");
            mainStage.setScene(new Scene(mainBorderPane));
            mainStage.show();

            //Get login window and hide
            Window loginWindow = etradeWebView.getScene().getWindow();
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