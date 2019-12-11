package model.Accounts.Balances;

public class Cash {

    private Double fundsForOpenOrdersCash;
    private Double moneyMktBalance;

    public Double getFundsForOpenOrdersCash() {
        return fundsForOpenOrdersCash;
    }
    public Double getMoneyMktBalance() {
        return moneyMktBalance;
    }

    public void setFundsForOpenOrdersCash(Double fundsForOpenOrdersCash) {
        this.fundsForOpenOrdersCash = fundsForOpenOrdersCash;
    }
    public void setMoneyMktBalance(Double moneyMktBalance) {
        this.moneyMktBalance = moneyMktBalance;
    }

    public Cash(){
    }
}
