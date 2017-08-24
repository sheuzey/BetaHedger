/**
 * Created by Stephen on 3/27/17.
 */

import com.etrade.etws.sdk.client.ClientRequest;
import javafx.fxml.Initializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private ClientRequest clientRequestWithAccessToken;

    private static final Logger logger = LogManager.getLogger(MainController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {    }

    public void setClientRequestWithAccessToken(ClientRequest clientRequest){
        this.clientRequestWithAccessToken = clientRequest;
        this.logger.info(String.format("Client Request Token Set {Token: %s} {Secret: %s}", this.clientRequestWithAccessToken.getToken(), this.clientRequestWithAccessToken.getTokenSecret()));
    }
}
