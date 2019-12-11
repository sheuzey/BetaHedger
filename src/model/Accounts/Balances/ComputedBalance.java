package model.Accounts.Balances;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ComputedBalance {

    private Double cashAvailableForInvestment;
    private Double cashAvailableForWithdrawal;
    private Double totalAvailableForWithdrawal;
    private Double netCash;
    private Double cashBalance;
    private Double settledCashForInvestment;
    private Double unSettledCashForInvestment;
    private Double fundsWithheldFromPurchasePower;
    private Double fundsWithheldFromWithdrawal;
    private Double marginBuyingPower;
    private Double cashBuyingPower;
    private Double dtMarginBuyingPower;
    private Double dtCashBuyingPower;
    private Double marginBalance;
    private Double shortAdjustBalance;
    private Double regtEquity;
    private Double regtEquityPercent;
    private Double accountBalance;
    @XmlElement(name = "OpenCalls")
    private OpenCall openCalls;
    @XmlElement(name = "RealTimeValues")
    private RealTimeValues realTimeValues;
    private PortfolioMargin portfolioMargin;

    public Double getCashAvailableForInvestment() {
        return cashAvailableForInvestment;
    }
    public Double getCashAvailableForWithdrawal() {
        return cashAvailableForWithdrawal;
    }
    public Double getTotalAvailableForWithdrawal() {
        return totalAvailableForWithdrawal;
    }
    public Double getNetCash() {
        return netCash;
    }
    public Double getCashBalance() {
        return cashBalance;
    }
    public Double getSettledCashForInvestment() {
        return settledCashForInvestment;
    }
    public Double getUnSettledCashForInvestment() {
        return unSettledCashForInvestment;
    }
    public Double getFundsWithheldFromPurchasePower() {
        return fundsWithheldFromPurchasePower;
    }
    public Double getFundsWithheldFromWithdrawal() {
        return fundsWithheldFromWithdrawal;
    }
    public Double getMarginBuyingPower() {
        return marginBuyingPower;
    }
    public Double getCashBuyingPower() {
        return cashBuyingPower;
    }
    public Double getDtMarginBuyingPower() {
        return dtMarginBuyingPower;
    }
    public Double getDtCashBuyingPower() {
        return dtCashBuyingPower;
    }
    public Double getMarginBalance() {
        return marginBalance;
    }
    public Double getShortAdjustBalance() {
        return shortAdjustBalance;
    }
    public Double getRegtEquity() {
        return regtEquity;
    }
    public Double getRegtEquityPercent() {
        return regtEquityPercent;
    }
    public Double getAccountBalance() {
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

    public void setCashAvailableForInvestment(Double cashAvailableForInvestment) {
        this.cashAvailableForInvestment = cashAvailableForInvestment;
    }
    public void setCashAvailableForWithdrawal(Double cashAvailableForWithdrawal) {
        this.cashAvailableForWithdrawal = cashAvailableForWithdrawal;
    }
    public void setTotalAvailableForWithdrawal(Double totalAvailableForWithdrawal) {
        this.totalAvailableForWithdrawal = totalAvailableForWithdrawal;
    }
    public void setNetCash(Double netCash) {
        this.netCash = netCash;
    }
    public void setCashBalance(Double cashBalance) {
        this.cashBalance = cashBalance;
    }
    public void setSettledCashForInvestment(Double settledCashForInvestment) {
        this.settledCashForInvestment = settledCashForInvestment;
    }
    public void setUnSettledCashForInvestment(Double unSettledCashForInvestment) {
        this.unSettledCashForInvestment = unSettledCashForInvestment;
    }
    public void setFundsWithheldFromPurchasePower(Double fundsWithheldFromPurchasePower) {
        this.fundsWithheldFromPurchasePower = fundsWithheldFromPurchasePower;
    }
    public void setFundsWithheldFromWithdrawal(Double fundsWithheldFromWithdrawal) {
        this.fundsWithheldFromWithdrawal = fundsWithheldFromWithdrawal;
    }
    public void setMarginBuyingPower(Double marginBuyingPower) {
        this.marginBuyingPower = marginBuyingPower;
    }
    public void setCashBuyingPower(Double cashBuyingPower) {
        this.cashBuyingPower = cashBuyingPower;
    }
    public void setDtMarginBuyingPower(Double dtMarginBuyingPower) {
        this.dtMarginBuyingPower = dtMarginBuyingPower;
    }
    public void setDtCashBuyingPower(Double dtCashBuyingPower) {
        this.dtCashBuyingPower = dtCashBuyingPower;
    }
    public void setMarginBalance(Double marginBalance) {
        this.marginBalance = marginBalance;
    }
    public void setShortAdjustBalance(Double shortAdjustBalance) {
        this.shortAdjustBalance = shortAdjustBalance;
    }
    public void setRegtEquity(Double regtEquity) {
        this.regtEquity = regtEquity;
    }
    public void setRegtEquityPercent(Double regtEquityPercent) {
        this.regtEquityPercent = regtEquityPercent;
    }
    public void setAccountBalance(Double accountBalance) {
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
