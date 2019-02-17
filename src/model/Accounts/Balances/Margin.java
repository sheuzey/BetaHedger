package model.Accounts.Balances;

public class Margin {

    private Number dtCashOpenOrderReserve;
    private Number dtMarginOpenOrderReserve;

    public Number getDtCashOpenOrderReserve() {
        return dtCashOpenOrderReserve;
    }
    public Number getDtMarginOpenOrderReserve() {
        return dtMarginOpenOrderReserve;
    }

    public void setDtCashOpenOrderReserve(Number dtCashOpenOrderReserve) {
        this.dtCashOpenOrderReserve = dtCashOpenOrderReserve;
    }
    public void setDtMarginOpenOrderReserve(Number dtMarginOpenOrderReserve) {
        this.dtMarginOpenOrderReserve = dtMarginOpenOrderReserve;
    }

    public Margin(){
    }
}
