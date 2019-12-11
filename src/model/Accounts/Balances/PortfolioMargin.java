package model.Accounts.Balances;

public class PortfolioMargin {

    private Double dtCashOpenOrderReserve;
    private Double dtMarginOpenOrderReserve;
    private Double liquidatingEquity;
    private Double houseExcessEquity;
    private Double totalHouseRequirement;
    private Double excessEquityMinusRequirement;
    private Double totalMarginRqmts;
    private Double availExcessEquity;
    private Double excessEquity;
    private Double openOrderReserve;
    private Double fundsOnHold;

    public Double getDtCashOpenOrderReserve() {
        return dtCashOpenOrderReserve;
    }
    public Double getDtMarginOpenOrderReserve() {
        return dtMarginOpenOrderReserve;
    }
    public Double getLiquidatingEquity() {
        return liquidatingEquity;
    }
    public Double getHouseExcessEquity() {
        return houseExcessEquity;
    }
    public Double getTotalHouseRequirement() {
        return totalHouseRequirement;
    }
    public Double getExcessEquityMinusRequirement() {
        return excessEquityMinusRequirement;
    }
    public Double getTotalMarginRqmts() {
        return totalMarginRqmts;
    }
    public Double getAvailExcessEquity() {
        return availExcessEquity;
    }
    public Double getExcessEquity() {
        return excessEquity;
    }
    public Double getOpenOrderReserve() {
        return openOrderReserve;
    }
    public Double getFundsOnHold() {
        return fundsOnHold;
    }

    public void setDtCashOpenOrderReserve(Double dtCashOpenOrderReserve) {
        this.dtCashOpenOrderReserve = dtCashOpenOrderReserve;
    }
    public void setDtMarginOpenOrderReserve(Double dtMarginOpenOrderReserve) {
        this.dtMarginOpenOrderReserve = dtMarginOpenOrderReserve;
    }
    public void setLiquidatingEquity(Double liquidatingEquity) {
        this.liquidatingEquity = liquidatingEquity;
    }
    public void setHouseExcessEquity(Double houseExcessEquity) {
        this.houseExcessEquity = houseExcessEquity;
    }
    public void setTotalHouseRequirement(Double totalHouseRequirement) {
        this.totalHouseRequirement = totalHouseRequirement;
    }
    public void setExcessEquityMinusRequirement(Double excessEquityMinusRequirement) {
        this.excessEquityMinusRequirement = excessEquityMinusRequirement;
    }
    public void setTotalMarginRqmts(Double totalMarginRqmts) {
        this.totalMarginRqmts = totalMarginRqmts;
    }
    public void setAvailExcessEquity(Double availExcessEquity) {
        this.availExcessEquity = availExcessEquity;
    }
    public void setExcessEquity(Double excessEquity) {
        this.excessEquity = excessEquity;
    }
    public void setOpenOrderReserve(Double openOrderReserve) {
        this.openOrderReserve = openOrderReserve;
    }
    public void setFundsOnHold(Double fundsOnHold) {
        this.fundsOnHold = fundsOnHold;
    }

    public PortfolioMargin(){
    }
}
