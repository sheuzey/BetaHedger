/**
 * Created by Stephen on 3/27/17.
 */

import com.etrade.etws.sdk.client.ClientRequest;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private StackPane menuStackPane;
    @FXML
    private JFXListView<JFXButton> menuListView;

    private ClientRequest clientRequestWithAccessToken;
    private static final Logger logger = LogManager.getLogger(MainController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.setupMenuList();
        this.showAccountInfo();

    }

    public void setClientRequestWithAccessToken(ClientRequest clientRequest){
        this.clientRequestWithAccessToken = clientRequest;
        logger.info(String.format("Client Request Token Set {Token: %s} {Secret: %s}", this.clientRequestWithAccessToken.getToken(), this.clientRequestWithAccessToken.getTokenSecret()));
    }

    //TODO Setup the main menu list
    private void setupMenuList(){

    }

    //TODO Create the layout for showing the user account
    private void showAccountInfo(){

    }

}
