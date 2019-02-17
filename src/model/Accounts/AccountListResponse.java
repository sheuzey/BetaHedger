package model.Accounts;

import java.util.ArrayList;
import java.util.List;

public class AccountListResponse {

    private List<Account> accounts;

    public List<Account> getAccounts() {
        if (accounts == null)
            return new ArrayList<>();
        return accounts;
    }
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public AccountListResponse(){
    }

}
