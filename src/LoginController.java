/**
 * Created by Stephen on 3/6/17.
 */

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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

    private WebEngine etradeWebEngine;
    private String verificationCodeFromCallback;
    private static final String ETRADE_WELCOME_ADDRESS_SUBSTRING = "welcomecenter";
    private static final String BETAHEDGER_ADDRESS_SUBSTRING = "betahedger";
    private static final String OAUTH_VERIFIER = "oauth_verifier";

    private ConnectionModel connectionModel;

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logger.info("Login Screen created ");

        /**
         * Set client, request and environment and get verification URI
         */
        this.connectionModel = new ConnectionModel();
        String verificationURI = this.connectionModel.getVerificationURI().toString();

        this.etradeWebEngine = this.etradeWebView.getEngine();
        this.etradeWebEngine.load(verificationURI);

        //TODO: Implement callback authorization procedure
        etradeWebEngine.getLoadWorker().stateProperty().addListener(
                (observable, oldValue, newValue) -> {

                    //Get current URL address and create hashmap of parameters
                    String urlAddress = this.etradeWebEngine.getLocation();
                    this.logger.info(String.format("Etrade WebEngine state changed from {%s} to {%s} {Current Address {%s}}", oldValue, newValue, urlAddress));

                    Map<String, String> queryMap = this.getQueryMapFromUrl(urlAddress);

                    /**
                     * If the page succeeded in loading and is the welcome page, reload to get the verification code page
                     * If the page succeeded in loading and is the betahedger / callback url, grab the verification code
                    */
                    if (Worker.State.SUCCEEDED.equals(newValue) && urlAddress.contains(this.ETRADE_WELCOME_ADDRESS_SUBSTRING)) {
                        this.etradeWebEngine.load(verificationURI);
                    }
                    if (urlAddress.contains(this.BETAHEDGER_ADDRESS_SUBSTRING)) {
                        this.verificationCodeFromCallback = queryMap.get(this.OAUTH_VERIFIER);
                        this.logger.info(String.format("Received verification code from callback {Callback URL {%s}} {Code {%s}}",
                                this.etradeWebEngine.getLocation(),
                                this.verificationCodeFromCallback));
                        this.AuthorizeCodeAndLaunchMainController();
                    }
                });
    }

    private void AuthorizeCodeAndLaunchMainController(){
        this.logger.info(String.format("Authorizing using passcode: {%s}", this.verificationCodeFromCallback));
        this.connectionModel.setAccessToken(this.verificationCodeFromCallback);
        this.logger.info(String.format("Success! Created Authorized Request"));

        /**
         * Create Main Controller and pass Authorized Access Token / Token Secret
         */
        try {

            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("MainController.fxml"));
            BorderPane mainBorderPane = mainLoader.load();

            MainController mainController = mainLoader.getController();
            mainController.setClientRequestWithAccessToken(this.connectionModel.getRequestWithAccessToken());
            Stage mainStage = new Stage();
            mainStage.setTitle("BetaHedger");
            mainStage.setScene(new Scene(mainBorderPane));
            mainStage.show();

            //Get login window and hide
            Window loginWindow = this.etradeWebView.getScene().getWindow();
            loginWindow.hide();
        } catch (Exception e) {
            this.logger.error(ExceptionUtils.getStackTrace(e));
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
                        this.logger.info(String.format("Value added to URL parameter hashmap: {key {%s}} {value {%s}}", key,value));
                    }
                    return parameterMap;
                }
            }
            else {
                this.logger.info(String.format("No parameters from provided URL: {%s}",url));
            }
        }
        catch (Throwable e){
            this.logger.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }
}
