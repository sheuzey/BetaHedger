package model.Accounts;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "AccountListResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountListResponse {

    @XmlElementWrapper(name = "Accounts")
    @XmlElement(name = "Account")
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
