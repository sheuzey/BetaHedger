package model.Accounts.Balances;

public class OpenCall {

    private Number minEquityCall;
    private Number fedCall;
    private Number cashCall;
    private Number houseCall;

    public Number getMinEquityCall() {
        return minEquityCall;
    }
    public Number getFedCall() {
        return fedCall;
    }
    public Number getCashCall() {
        return cashCall;
    }
    public Number getHouseCall() {
        return houseCall;
    }

    public void setMinEquityCall(Number minEquityCall) {
        this.minEquityCall = minEquityCall;
    }
    public void setFedCall(Number fedCall) {
        this.fedCall = fedCall;
    }
    public void setCashCall(Number cashCall) {
        this.cashCall = cashCall;
    }
    public void setHouseCall(Number houseCall) {
        this.houseCall = houseCall;
    }

    public OpenCall() {
    }
}
