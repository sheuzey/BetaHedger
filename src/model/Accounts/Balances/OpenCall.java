package model.Accounts.Balances;

public class OpenCall {

    private Double minEquityCall;
    private Double fedCall;
    private Double cashCall;
    private Double houseCall;

    public Double getMinEquityCall() {
        return minEquityCall;
    }
    public Double getFedCall() {
        return fedCall;
    }
    public Double getCashCall() {
        return cashCall;
    }
    public Double getHouseCall() {
        return houseCall;
    }

    public void setMinEquityCall(Double minEquityCall) {
        this.minEquityCall = minEquityCall;
    }
    public void setFedCall(Double fedCall) {
        this.fedCall = fedCall;
    }
    public void setCashCall(Double cashCall) {
        this.cashCall = cashCall;
    }
    public void setHouseCall(Double houseCall) {
        this.houseCall = houseCall;
    }

    public OpenCall() {
    }
}
