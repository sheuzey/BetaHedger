package model; /**
 * Created by Stephen on 1/12/17.
 */

import com.etrade.etws.sdk.client.Environment;
import com.etrade.etws.oauth.sdk.client.IOAuthClient;
import com.etrade.etws.oauth.sdk.client.OAuthClientImpl;
import com.etrade.etws.oauth.sdk.common.Token;
import com.etrade.etws.sdk.client.ClientRequest;

import java.io.FileInputStream;
import java.net.URI;
import java.util.ListResourceBundle;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.*;


public class ConnectionModel extends ListResourceBundle {

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
            input = new FileInputStream("config/Genesis.properties");
            prop.load(input);
            oauth_consumer_key = prop.getProperty("oauth_consumer_key");
            oauth_consumer_secret = prop.getProperty("oauth_consumer_secret");
        } catch (Throwable e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (input != null){
                try {
                    input.close();
                }
                catch (Throwable e) {
                    logger.error(ExceptionUtils.getStackTrace(e));
                }
            }
        }

        oauth_request_token = null;
        oauth_request_token_secret = null;
        oauth_access_token = null;
        oauth_access_token_secret = null;
        oauth_verify_code = null;
        requestToken = null;
        accessToken = null;
        authorizeURL = null;

        client = OAuthClientImpl.getInstance();
        logger.info(String.format("OAuth Client created {%s}",client.toString()));

        request = new ClientRequest();
        request.setConsumerKey(oauth_consumer_key);
        request.setConsumerSecret(oauth_consumer_secret);
        logger.info(String.format("Request Created {%s}", request.toString()));

        request.setEnv(Environment.SANDBOX);
        logger.info("Environment set to " + request.getEnv());

        setRequestWithRequestToken();
    }

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"model.ConnectionModel",this}
        };
    }

    public URI getVerificationURI() {
        try {
            authorizeURL = client.getAuthorizeUrl(request);
            uri = new URI(authorizeURL);
            logger.info(String.format("Authorized URL {%s} URI {%s}", authorizeURL, uri));
            return uri;

        } catch (Throwable e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public void setAccessToken(String verificationCode){
        try {
            oauth_verify_code = verificationCode;
            request.setVerifierCode(oauth_verify_code);
            accessToken = client.getAccessToken(request);
            logger.info(String.format("Access Token set {%s}",accessToken.toString()));

            oauth_access_token = accessToken.getToken();
            oauth_access_token_secret = accessToken.getSecret();
            logger.info(String.format("Access Token {%s} Access Token Secret {%s}", oauth_access_token, oauth_access_token_secret));
        } catch (Throwable e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    public ClientRequest getRequestWithAccessToken(){
        request.setToken(oauth_access_token);
        request.setTokenSecret(oauth_access_token_secret);
        return request;
    }

    public void setRequestWithRequestToken() {

        try {
            logger.info("Setting Request Token ...");
            requestToken = client.getRequestToken(request);
            oauth_request_token = requestToken.getToken();
            oauth_request_token_secret = requestToken.getSecret();
            request.setToken(oauth_request_token);
            request.setTokenSecret(oauth_request_token_secret);
            logger.info(String.format("Request token set {Object {%s}} {Token {%s}} {Token Secret {%s}}", requestToken.toString(), oauth_request_token, oauth_request_token_secret));
        } catch (Throwable e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }

    }

}
