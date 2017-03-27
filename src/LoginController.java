/**
 * Created by Stephen on 3/6/17.
 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    @FXML
    private Button loginButton;
    @FXML
    private TextField codeField;
    @FXML
    private VBox vBox;
    private ConnectionModel connectionModel;

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logger.info("Login Screen created ");
        this.logger.info(String.format("Login Button Properties: {%s}", this.loginButton.getProperties().toString()));

        this.loginButton.defaultButtonProperty().bind(this.loginButton.focusedProperty());
        this.loginButton.setOnAction(event -> {
            logger.info("Login button clicked ...");

            switch (this.loginButton.getText()){
                case "Get Passcode":
                    //Update the login buttons' vbox margins to fit new text, and make code input field visible
                    this.loginButton.setText("Authorize Passcode");
                    this.vBox.setMargin(this.loginButton,new Insets(
                            this.vBox.getMargin(this.loginButton).getTop(),
                            this.vBox.getMargin(this.loginButton).getRight(),
                            this.vBox.getMargin(this.loginButton).getBottom(),
                            this.vBox.getMargin(this.loginButton).getLeft() - 20));
                    this.logger.info(String.format("Login Button Properties: {%s}", this.loginButton.getProperties().toString()));
                    this.codeField.setVisible(true);

                    // Set client, request and environment and get verification code
                    this.connectionModel = new ConnectionModel();
                    this.connectionModel.setRequestWithRequestToken();
                    this.connectionModel.getVerificationCode();
                    break;
                case "Authorize Passcode":
                    this.logger.info(String.format("Authorizing using passcode: {%s}", this.codeField.getText()));
                    this.connectionModel.getAccessToken(this.codeField.getText());

                    this.logger.info(String.format("Success! Created Authorized Request {%s}", this.connectionModel.getRequestWithAccessToken().toString()));
                    break;
            }
        });
    }

}
