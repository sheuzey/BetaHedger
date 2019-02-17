package model.Accounts.Balances;

public class ComputedBalance {

    private Number cashAvailableForInvestment;
    private Number cashAvailableForWithdrawal;
    private Number totalAvailableForWithdrawal;
    private Number netCash;
    private Number cashBalance;
    private Number settledCashForInvestment;
    private Number unSettledCashForInvestment;
    private Number fundsWithheldFromPurchasePower;
    private Number fundsWithheldFromWithdrawal;
    private Number marginBuyingPower;
    private Number cashBuyingPower;
    private Number dtMarginBuyingPower;
    private Number dtCashBuyingPower;
    private Number marginBalance;
    private Number shortAdjustBalance;
    private Number regtEquity;
    private Number regtEquityPercent;
    private Number accountBalance;
    private OpenCall openCalls;
    private RealTimeValues realTimeValues;
    private PortfolioMargin portfolioMargin;

    public Number getCashAvailableForInvestment() {
        return cashAvailableForInvestment;
    }
    public Number getCashAvailableForWithdrawal() {
        return cashAvailableForWithdrawal;
    }
    public Number getTotalAvailableForWithdrawal() {
        return totalAvailableForWithdrawal;
    }
    public Number getNetCash() {
        return netCash;
    }
    public Number getCashBalance() {
        return cashBalance;
    }
    public Number getSettledCashForInvestment() {
        return settledCashForInvestment;
    }
    public Number getUnSettledCashForInvestment() {
        return unSettledCashForInvestment;
    }
    public Number getFundsWithheldFromPurchasePower() {
        return fundsWithheldFromPurchasePower;
    }
    public Number getFundsWithheldFromWithdrawal() {
        return fundsWithheldFromWithdrawal;
    }
    public Number getMarginBuyingPower() {
        return marginBuyingPower;
    }
    public Number getCashBuyingPower() {
        return cashBuyingPower;
    }
    public Number getDtMarginBuyingPower() {
        return dtMarginBuyingPower;
    }
    public Number getDtCashBuyingPower() {
        return dtCashBuyingPower;
    }
    public Number getMarginBalance() {
        return marginBalance;
    }
    public Number getShortAdjustBalance() {
        return shortAdjustBalance;
    }
    public Number getRegtEquity() {
        return regtEquity;
    }
    public Number getRegtEquityPercent() {
        return regtEquityPercent;
    }
    public Number getAccountBalance() {
        return accountBalance;
    }
    public OpenCall getOpenCalls() {
        return openCalls;
    }
    public RealTimeValues getRealTimeValues() {
        return realTimeValues;
    }
    public PortfolioMargin getPortfolioMargin() {
        return portfolioMargin;
    }

    public void setCashAvailableForInvestment(Number cashAvailableForInvestment) {
        this.cashAvailableForInvestment = cashAvailableForInvestment;
    }
    public void setCashAvailableForWithdrawal(Number cashAvailableForWithdrawal) {
        this.cashAvailableForWithdrawal = cashAvailableForWithdrawal;
    }
    public void setTotalAvailableForWithdrawal(Number totalAvailableForWithdrawal) {
        this.totalAvailableForWithdrawal = totalAvailableForWithdrawal;
    }
    public void setNetCash(Number netCash) {
        this.netCash = netCash;
    }
    public void setCashBalance(Number cashBalance) {
        this.cashBalance = cashBalance;
    }
    public void setSettledCashForInvestment(Number settledCashForInvestment) {
        this.settledCashForInvestment = settledCashForInvestment;
    }
    public void setUnSettledCashForInvestment(Number unSettledCashForInvestment) {
        this.unSettledCashForInvestment = unSettledCashForInvestment;
    }
    public void setFundsWithheldFromPurchasePower(Number fundsWithheldFromPurchasePower) {
        this.fundsWithheldFromPurchasePower = fundsWithheldFromPurchasePower;
    }
    public void setFundsWithheldFromWithdrawal(Number fundsWithheldFromWithdrawal) {
        this.fundsWithheldFromWithdrawal = fundsWithheldFromWithdrawal;
    }
    public void setMarginBuyingPower(Number marginBuyingPower) {
        this.marginBuyingPower = marginBuyingPower;
    }
    public void setCashBuyingPower(Number cashBuyingPower) {
        this.cashBuyingPower = cashBuyingPower;
    }
    public void setDtMarginBuyingPower(Number dtMarginBuyingPower) {
        this.dtMarginBuyingPower = dtMarginBuyingPower;
    }
    public void setDtCashBuyingPower(Number dtCashBuyingPower) {
        this.dtCashBuyingPower = dtCashBuyingPower;
    }
    public void setMarginBalance(Number marginBalance) {
        this.marginBalance = marginBalance;
    }
    public void setShortAdjustBalance(Number shortAdjustBalance) {
        this.shortAdjustBalance = shortAdjustBalance;
    }
    public void setRegtEquity(Number regtEquity) {
        this.regtEquity = regtEquity;
    }
    public void setRegtEquityPercent(Number regtEquityPercent) {
        this.regtEquityPercent = regtEquityPercent;
    }
    public void setAccountBalance(Number accountBalance) {
        this.accountBalance = accountBalance;
    }
    public void setOpenCalls(OpenCall openCalls) {
        this.openCalls = openCalls;
    }
    public void setRealTimeValues(RealTimeValues realTimeValues) {
        this.realTimeValues = realTimeValues;
    }
    public void setPortfolioMargin(PortfolioMargin portfolioMargin) {
        this.portfolioMargin = portfolioMargin;
    }

    public ComputedBalance(){
    }
}
