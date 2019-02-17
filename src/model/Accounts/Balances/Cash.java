package model.Accounts.Balances;

public class Cash {

    private Number fundsForOpenOrdersCash;
    private Number moneyMktBalance;

    public Number getFundsForOpenOrdersCash() {
        return fundsForOpenOrdersCash;
    }
    public Number getMoneyMktBalance() {
        return moneyMktBalance;
    }

    public void setFundsForOpenOrdersCash(Number fundsForOpenOrdersCash) {
        this.fundsForOpenOrdersCash = fundsForOpenOrdersCash;
    }
    public void setMoneyMktBalance(Number moneyMktBalance) {
        this.moneyMktBalance = moneyMktBalance;
    }

    public Cash(){
    }
}
