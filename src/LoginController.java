/**
 * Created by Stephen on 3/6/17.
 */

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    @FXML
    private Button loginButton;
    @FXML
    private TextField codeField;
    @FXML
    private VBox vBox;
    @FXML
    private WebView etradeWebView;

    private ConnectionModel connectionModel;

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logger.info("Login Screen created ");
        this.logger.info(String.format("Login Button Properties: {%s}", this.loginButton.getProperties().toString()));

        this.loginButton.defaultButtonProperty().bind(this.loginButton.focusedProperty());

        // Set client, request and environment and get verification URI
        this.connectionModel = new ConnectionModel();
        this.connectionModel.setRequestWithRequestToken();
        URI verificationURI = this.connectionModel.getVerificationURI();
        WebEngine etradeWebEngine = this.etradeWebView.getEngine();

        //TODO: Add listener to the WebEngine State to relaunch the verification URI and update size of button
        etradeWebEngine.getLoadWorker().stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.logger.info(String.format("etradeWebEngine state changed from {%s} to {%s}", oldValue, newValue));
                    if (newValue == Worker.State.SUCCEEDED) {
                        if (this.loginButton.getText().equals("Login to E*TRADE Account")) {
                            this.loginButton.setText("Logging in ...");
                            this.loginButton.setDisable(true);
                            this.logger.info(String.format("Changed login button text to {%s} and is disabled", this.loginButton.getText()));
                        } else if (this.loginButton.getText().equals("Logging in ...")) {
                            this.loginButton.setDisable(false);
                            this.loginButton.setText("Authorize Passcode");
                            this.logger.info(String.format("Changed login button text to {%s} and is enabled, relaunching URI to get verification code", this.loginButton.getText()));
                            etradeWebEngine.load(verificationURI.toString());
                        }
                    }
                });

        this.loginButton.setOnAction(event -> {
            try {
                logger.info("Login button clicked ...");

                switch (this.loginButton.getText()){
                    case "Login to E*TRADE Account":
                        //Update the login buttons' vbox margins to fit new text, and make code input field visible
                        this.vBox.setMargin(this.loginButton,new Insets(
                                this.vBox.getMargin(this.loginButton).getTop(),
                                this.vBox.getMargin(this.loginButton).getRight(),
                                this.vBox.getMargin(this.loginButton).getBottom(),
                                this.vBox.getMargin(this.loginButton).getLeft() + 20));
                        this.logger.info(String.format("Login Button Properties: {%s}", this.loginButton.getProperties().toString()));
                        this.codeField.setVisible(true);

                        this.etradeWebView.setVisible(true);
                        VBox.setVgrow(this.etradeWebView, Priority.ALWAYS);
                        etradeWebEngine.load(verificationURI.toString());

                        break;

                    case "Authorize Passcode":
                        this.logger.info(String.format("Authorizing using passcode: {%s}", this.codeField.getText()));
                        if (this.codeField.getText().isEmpty()) {
                            this.logger.error("Did not provide Passcode!");
                            //TODO: Launch an error popup to the user
                            Alert noCodeProvidedPopup = new Alert(Alert.AlertType.ERROR, "No verification code provided!", ButtonType.OK);
                            noCodeProvidedPopup.showAndWait();
                        }
                        else {
                            this.connectionModel.setAccessToken(this.codeField.getText());

                            this.logger.info(String.format("Success! Created Authorized Request {%s}", this.connectionModel.getRequestWithAccessToken().toString()));

                            //Create Main Controller and pass Authorized Access Token / Token Secret
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
                                Window loginWindow = this.loginButton.getScene().getWindow();
                                loginWindow.hide();
                            } catch (Exception e) {
                                this.logger.error(ExceptionUtils.getStackTrace(e));
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                this.logger.error(ExceptionUtils.getStackTrace(e));
            }
        });
    }

}
