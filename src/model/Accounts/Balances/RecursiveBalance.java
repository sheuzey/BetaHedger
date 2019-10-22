package model.Accounts.Balances;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class RecursiveBalance extends RecursiveTreeObject<RecursiveBalance> {

    private BalanceResponse balanceResponse;

    public RecursiveBalance(BalanceResponse balanceResponse) {
        this.balanceResponse = balanceResponse;
    }

    public BalanceResponse getBalanceResponse() {
        return balanceResponse;
    }

}
