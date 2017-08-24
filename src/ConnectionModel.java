/**
 * Created by Stephen on 1/12/17.
 */

import com.etrade.etws.sdk.client.Environment;
import com.etrade.etws.oauth.sdk.client.IOAuthClient;
import com.etrade.etws.oauth.sdk.client.OAuthClientImpl;
import com.etrade.etws.oauth.sdk.common.Token;
import com.etrade.etws.sdk.client.ClientRequest;

import java.io.FileInputStream;
import java.net.URI;
import java.util.Properties;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.*;


public class ConnectionModel {

    private static final Logger logger = LogManager.getLogger(ConnectionModel.class);

    private IOAuthClient client;
    private ClientRequest request;
    private Token requestToken;
    private Token accessToken;
    private String oauth_request_token;
    private String oauth_request_token_secret;
    private String oauth_access_token;
    private String oauth_access_token_secret;
    private String oauth_consumer_key;
    private String oauth_consumer_secret;
    private String authorizeURL;
    private URI uri;
    private String oauth_verify_code;

    public ConnectionModel(){
        Properties prop = new Properties();
        FileInputStream input = null;

        try {
            input = new FileInputStream("config/BetaHedger.properties");
            prop.load(input);
            this.oauth_consumer_key = prop.getProperty("oauth_consumer_key");
            this.oauth_consumer_secret = prop.getProperty("oauth_consumer_secret");
        } catch (Throwable e) {
            this.logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (input != null){
                try {
                    input.close();
                }
                catch (Throwable e) {
                    this.logger.error(ExceptionUtils.getStackTrace(e));
                }
            }
        }

        this.oauth_request_token = null;
        this.oauth_request_token_secret = null;
        this.oauth_access_token = null;
        this.oauth_access_token_secret = null;
        this.oauth_verify_code = null;
        this.requestToken = null;
        this.accessToken = null;
        this.authorizeURL = null;

        this.client = OAuthClientImpl.getInstance();
        this.logger.info(String.format("OAuth Client created {%s}",this.client.toString()));

        this.request = new ClientRequest();
        this.request.setConsumerKey(this.oauth_consumer_key);
        this.request.setConsumerSecret(this.oauth_consumer_secret);
        this.logger.info(String.format("Request Created {%s}", this.request.toString()));

        this.request.setEnv(Environment.SANDBOX);
        this.logger.info("Environment set to " + this.request.getEnv());

        setRequestWithRequestToken();
    }

    public URI getVerificationURI() {
        try {
            this.authorizeURL = this.client.getAuthorizeUrl(this.request);
            this.uri = new URI(this.authorizeURL);
            this.logger.info(String.format("Authorized URL {%s} URI {%s}", this.authorizeURL, this.uri));
            return this.uri;

        } catch (Throwable e) {
            this.logger.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public void setAccessToken(String verificationCode){
        try {
            this.oauth_verify_code = verificationCode;
            this.request.setVerifierCode(this.oauth_verify_code);
            this.accessToken = this.client.getAccessToken(this.request);
            this.logger.info(String.format("Access Token set {%s}",this.accessToken.toString()));

            this.oauth_access_token = this.accessToken.getToken();
            this.oauth_access_token_secret = this.accessToken.getSecret();
            this.logger.info(String.format("Access Token {%s} Access Token Secret {%s}", this.oauth_access_token, this.oauth_access_token_secret));
        } catch (Throwable e) {
            this.logger.error(ExceptionUtils.getStackTrace(e));
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
            this.requestToken = this.client.getRequestToken(this.request);
            this.oauth_request_token = this.requestToken.getToken();
            this.oauth_request_token_secret = this.requestToken.getSecret();
            this.request.setToken(this.oauth_request_token);
            this.request.setTokenSecret(this.oauth_request_token_secret);
            this.logger.info(String.format("Request token set {Object {%s}} {Token {%s}} {Token Secret {%s}}", this.requestToken.toString(), this.oauth_request_token, this.oauth_request_token_secret));
        } catch (Throwable e) {
            this.logger.error(ExceptionUtils.getStackTrace(e));
        }

    }

}
