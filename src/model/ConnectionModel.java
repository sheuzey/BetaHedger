package model; /**
 * Created by Stephen on 1/12/17.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ListResourceBundle;
import java.util.Properties;
import com.google.api.client.auth.oauth.*;
import com.google.api.client.http.*;
import com.google.api.client.http.apache.ApacheHttpTransport;
import model.Accounts.Account;
import model.Accounts.AccountListResponse;
import model.Accounts.Balances.BalanceResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


public class ConnectionModel extends ListResourceBundle {

    private static final Logger logger = LogManager.getLogger(ConnectionModel.class);

    private OAuthCredentialsResponse requestTokenResponse;
    private OAuthHmacSigner signer;
    private HttpTransport transport;
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

        oauth_access_token = null;
        oauth_access_token_secret = null;
        oauth_verify_code = null;
        authorizeURL = null;

        //Trying google oauth library
        OAuthGetTemporaryToken requestTemporaryToken = new OAuthGetTemporaryToken(EtradeURLs.RequestTokenUrl);
        requestTemporaryToken.callback = "oob";
        requestTemporaryToken.consumerKey = oauth_consumer_key;
        transport = new ApacheHttpTransport();
        requestTemporaryToken.transport = transport;
        signer = new OAuthHmacSigner();
        requestTemporaryToken.signer = signer;
        signer.clientSharedSecret = oauth_consumer_secret;

        try {
            requestTokenResponse = requestTemporaryToken.execute();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }

        signer.tokenSharedSecret = requestTokenResponse.tokenSecret;
    }

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"model.ConnectionModel",this}
        };
    }

    public URI getVerificationURI() {
        try {
            OAuthAuthorizeTemporaryTokenUrl temporaryTokenUrl = new OAuthAuthorizeTemporaryTokenUrl(String.format(EtradeURLs.AuthorizationUrlBase, oauth_consumer_key, requestTokenResponse.token));
            temporaryTokenUrl.temporaryToken = requestTokenResponse.token;
            authorizeURL = temporaryTokenUrl.build();
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

            OAuthGetAccessToken accessToken = new OAuthGetAccessToken(EtradeURLs.AccessTokenUrl);
            accessToken.consumerKey = oauth_consumer_key;
            accessToken.signer = signer;
            accessToken.transport = transport;
            accessToken.temporaryToken = requestTokenResponse.token;
            accessToken.verifier = oauth_verify_code;
            OAuthCredentialsResponse accessTokenResponse = accessToken.execute();

            logger.info(String.format("Access Token set {%s}",accessToken.toString()));

            oauth_access_token = accessTokenResponse.token;
            oauth_access_token_secret = accessTokenResponse.tokenSecret;
            signer.tokenSharedSecret = oauth_access_token_secret;

            logger.info(String.format("Access Token {%s} Access Token Secret {%s}", oauth_access_token, oauth_access_token_secret));
        } catch (Throwable e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    public HttpResponse responseForUrl(String url) throws IOException {
        OAuthParameters parameters = new OAuthParameters();
        parameters.consumerKey = oauth_consumer_key;
        parameters.token = oauth_access_token;
        parameters.signer = signer;
        HttpRequestFactory factory = transport.createRequestFactory(parameters);

        HttpRequest request = factory.buildGetRequest(new GenericUrl(url));
        HttpResponse response = request.execute();

        return response;
    }

    private <T> T parseXmlResponse(String response, Class<T> tClass){
        logger.info(String.format("Parsing {%s} to %s", response, tClass.toString()));
        try {
            StringReader stringReader = new StringReader(response);
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            @SuppressWarnings("unchecked")
            T parsedObject = (T) unmarshaller.unmarshal(stringReader);
            return parsedObject;
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public AccountListResponse getAccountLists() {
        try {
            HttpResponse response = responseForUrl(EtradeURLs.AccountList);
            AccountListResponse accountListResponse = parseXmlResponse(response.parseAsString(), AccountListResponse.class);
            return accountListResponse;
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return new AccountListResponse();
    }

    public BalanceResponse getAccountBalance(Account account){
        try {
            HttpResponse response = responseForUrl(String.format(EtradeURLs.AccountBalances, account.getAccountIdKey(), account.getInstitutionType(), "true"));
            BalanceResponse balanceResponse = parseXmlResponse(response.parseAsString(), BalanceResponse.class);
            return balanceResponse;
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return new BalanceResponse();
    }

}
