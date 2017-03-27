/**
 * Created by Stephen on 1/12/17.
 */

import com.etrade.etws.sdk.client.Environment;
import com.etrade.etws.oauth.sdk.client.IOAuthClient;
import com.etrade.etws.oauth.sdk.client.OAuthClientImpl;
import com.etrade.etws.oauth.sdk.common.Token;
import com.etrade.etws.sdk.client.ClientRequest;

import java.awt.*;
import java.net.URI;
import org.apache.logging.log4j.*;


public class ConnectionModel {

    private static final Logger logger = LogManager.getLogger(ConnectionModel.class);

    private IOAuthClient client;
    private ClientRequest request;
    private Token token;
    private String oauth_request_token;
    private String oauth_request_token_secret;
    private final String oauth_consumer_key;
    private final String oauth_consumer_secret;
    private String authorizeURL;
    private URI uri;
    private String oauth_access_token;
    private String oauth_access_token_secret;
    private String oauth_verify_code;

    public ConnectionModel(){
        this.oauth_consumer_key = "5df67ac77ab84a3522d691f2b65328c7";
        this.oauth_consumer_secret = "50cf251bea54d5a6d89f437714ee0c7e";
        this.oauth_request_token = null;
        this.oauth_request_token_secret = null;
        this.oauth_access_token = null;
        this.oauth_access_token_secret = null;
        this.oauth_verify_code = null;
        this.token = null;
        this.authorizeURL = null;

        this.client = OAuthClientImpl.getInstance();
        this.logger.info(String.format("OAuth Client created {%s}",this.client.toString()));

        this.request = new ClientRequest();
        this.request.setConsumerKey(this.oauth_consumer_key);
        this.request.setConsumerSecret(this.oauth_consumer_secret);
        this.logger.info(String.format("Request Created {%s}", this.request.toString()));

        this.request.setEnv(Environment.SANDBOX);
        this.logger.info("Environment set to " + this.request.getEnv());
    }

    public void getVerificationCode() {
        try {
            this.authorizeURL = this.client.getAuthorizeUrl(this.request);
            this.uri = new URI(this.authorizeURL);
            this.logger.info(String.format("Authorized URL {%s} URI {%s}", this.authorizeURL, this.uri));
            this.logger.info("Creating E*TRADE Authorization Page ...");
            Desktop desktop = Desktop.getDesktop();
            this.logger.info(String.format("Desktop Created {%s}", desktop.toString()));
            desktop.browse(this.uri);

        } catch (Throwable e) {
            this.logger.error(e.getCause().getMessage());
        }
    }

    public void getAccessToken(String verificationCode){
        try {
            this.oauth_verify_code = verificationCode;
            this.request.setVerifierCode(this.oauth_verify_code);
            this.token = this.client.getAccessToken(this.request);
            this.logger.info(String.format("Access Token set {%s}",this.token.toString()));

            this.oauth_access_token = this.token.getToken();
            this.oauth_access_token_secret = this.token.getSecret();
            this.logger.info(String.format("Access Token {%s} Access Token Secret {%s}", this.oauth_access_token, this.oauth_access_token_secret));
        } catch (Throwable e) {
            this.logger.error(e.getCause().getMessage());
        }
    }

    public ClientRequest getRequestWithAccessToken(){
        this.request.setToken(this.oauth_access_token);
        this.request.setTokenSecret(this.oauth_access_token_secret);
        return this.request;
    }

    public void setRequestWithRequestToken() {

        try {
            this.logger.info("Setting Request Token ...");
            this.token = this.client.getRequestToken(this.request);
            this.oauth_request_token = this.token.getToken();
            this.oauth_request_token_secret = this.token.getSecret();
            this.request.setToken(this.oauth_request_token);
            this.request.setTokenSecret(this.oauth_request_token_secret);
            this.logger.info(String.format("Request token set {Object {%s}} {Token {%s}} {Token Secret {%s}}", this.token.toString(), this.oauth_request_token, this.oauth_request_token_secret));
        } catch (Throwable e) {
            this.logger.error(e.getCause().getMessage());
        }

    }

}
