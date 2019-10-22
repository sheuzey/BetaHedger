package model;

public interface EtradeURLs {
    String AccountList = "https://apisb.etrade.com/v1/accounts/list";
    String AccountBalances = "https://apisb.etrade.com/v1/accounts/%s/balance?instType=%s&realTimeNAV=%s";
    String AuthorizationUrlBase = "https://us.etrade.com/e/t/etws/authorize?key=%s&token=%s";
    String AccessTokenUrl = "https://api.etrade.com/oauth/access_token";
    String RequestTokenUrl = "https://api.etrade.com/oauth/request_token";
}
