package model;

import com.etrade.etws.account.Account;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class RecursiveAccount extends RecursiveTreeObject<RecursiveAccount> {

    private Account account;

    public RecursiveAccount(Account account){
        this.account = account;
    }

    public Account getAccount(){
        return this.account;
    }
}
