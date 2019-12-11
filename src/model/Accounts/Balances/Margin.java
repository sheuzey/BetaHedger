package model.Accounts.Balances;

public class Margin {

    private Double dtCashOpenOrderReserve;
    private Double dtMarginOpenOrderReserve;

    public Double getDtCashOpenOrderReserve() {
        return dtCashOpenOrderReserve;
    }
    public Double getDtMarginOpenOrderReserve() {
        return dtMarginOpenOrderReserve;
    }

    public void setDtCashOpenOrderReserve(Double dtCashOpenOrderReserve) {
        this.dtCashOpenOrderReserve = dtCashOpenOrderReserve;
    }
    public void setDtMarginOpenOrderReserve(Double dtMarginOpenOrderReserve) {
        this.dtMarginOpenOrderReserve = dtMarginOpenOrderReserve;
    }

    public Margin(){
    }
}
