package model.Accounts.Balances;

public class PortfolioMargin {

    private Number dtCashOpenOrderReserve;
    private Number dtMarginOpenOrderReserve;
    private Number liquidatingEquity;
    private Number houseExcessEquity;
    private Number totalHouseRequirement;
    private Number excessEquityMinusRequirement;
    private Number totalMarginRqmts;
    private Number availExcessEquity;
    private Number excessEquity;
    private Number openOrderReserve;
    private Number fundsOnHold;

    public Number getDtCashOpenOrderReserve() {
        return dtCashOpenOrderReserve;
    }
    public Number getDtMarginOpenOrderReserve() {
        return dtMarginOpenOrderReserve;
    }
    public Number getLiquidatingEquity() {
        return liquidatingEquity;
    }
    public Number getHouseExcessEquity() {
        return houseExcessEquity;
    }
    public Number getTotalHouseRequirement() {
        return totalHouseRequirement;
    }
    public Number getExcessEquityMinusRequirement() {
        return excessEquityMinusRequirement;
    }
    public Number getTotalMarginRqmts() {
        return totalMarginRqmts;
    }
    public Number getAvailExcessEquity() {
        return availExcessEquity;
    }
    public Number getExcessEquity() {
        return excessEquity;
    }
    public Number getOpenOrderReserve() {
        return openOrderReserve;
    }
    public Number getFundsOnHold() {
        return fundsOnHold;
    }

    public void setDtCashOpenOrderReserve(Number dtCashOpenOrderReserve) {
        this.dtCashOpenOrderReserve = dtCashOpenOrderReserve;
    }
    public void setDtMarginOpenOrderReserve(Number dtMarginOpenOrderReserve) {
        this.dtMarginOpenOrderReserve = dtMarginOpenOrderReserve;
    }
    public void setLiquidatingEquity(Number liquidatingEquity) {
        this.liquidatingEquity = liquidatingEquity;
    }
    public void setHouseExcessEquity(Number houseExcessEquity) {
        this.houseExcessEquity = houseExcessEquity;
    }
    public void setTotalHouseRequirement(Number totalHouseRequirement) {
        this.totalHouseRequirement = totalHouseRequirement;
    }
    public void setExcessEquityMinusRequirement(Number excessEquityMinusRequirement) {
        this.excessEquityMinusRequirement = excessEquityMinusRequirement;
    }
    public void setTotalMarginRqmts(Number totalMarginRqmts) {
        this.totalMarginRqmts = totalMarginRqmts;
    }
    public void setAvailExcessEquity(Number availExcessEquity) {
        this.availExcessEquity = availExcessEquity;
    }
    public void setExcessEquity(Number excessEquity) {
        this.excessEquity = excessEquity;
    }
    public void setOpenOrderReserve(Number openOrderReserve) {
        this.openOrderReserve = openOrderReserve;
    }
    public void setFundsOnHold(Number fundsOnHold) {
        this.fundsOnHold = fundsOnHold;
    }

    public PortfolioMargin(){
    }
}
