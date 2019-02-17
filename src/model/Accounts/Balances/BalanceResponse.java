package model.Accounts.Balances;

import java.util.List;

public class BalanceResponse {

    private String accountId;
    private String institutionType;
    private Integer asOfDate;
    private String accountType;
    private String optionLevel;
    private String accountDescription;
    private Integer quoteMode;
    private String dayTraderStatus;
    private String accountMode;
    private String accountDesc;
    private List<OpenCall> openCalls;
    private Cash cash;
    private Margin margin;
    private Lending lending;
    private ComputedBalance computedBalance;

    public String getAccountId() {
        return accountId;
    }
    public String getInstitutionType() {
        return institutionType;
    }
    public Integer getAsOfDate() {
        return asOfDate;
    }
    public String getAccountType() {
        return accountType;
    }
    public String getOptionLevel() {
        return optionLevel;
    }
    public String getAccountDescription() {
        return accountDescription;
    }
    public Integer getQuoteMode() {
        return quoteMode;
    }
    public String getDayTraderStatus() {
        return dayTraderStatus;
    }
    public String getAccountMode() {
        return accountMode;
    }
    public String getAccountDesc() {
        return accountDesc;
    }
    public List<OpenCall> getOpenCalls() {
        return openCalls;
    }
    public Cash getCash() {
        return cash;
    }
    public Margin getMargin() {
        return margin;
    }
    public Lending getLending() {
        return lending;
    }
    public ComputedBalance getComputedBalance() {
        return computedBalance;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }
    public void setAsOfDate(Integer asOfDate) {
        this.asOfDate = asOfDate;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public void setOptionLevel(String optionLevel) {
        this.optionLevel = optionLevel;
    }
    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }
    public void setQuoteMode(Integer quoteMode) {
        this.quoteMode = quoteMode;
    }
    public void setDayTraderStatus(String dayTraderStatus) {
        this.dayTraderStatus = dayTraderStatus;
    }
    public void setAccountMode(String accountMode) {
        this.accountMode = accountMode;
    }
    public void setAccountDesc(String accountDesc) {
        this.accountDesc = accountDesc;
    }
    public void setOpenCalls(List<OpenCall> openCalls) {
        this.openCalls = openCalls;
    }
    public void setCash(Cash cash) {
        this.cash = cash;
    }
    public void setMargin(Margin margin) {
        this.margin = margin;
    }
    public void setLending(Lending lending) {
        this.lending = lending;
    }
    public void setComputedBalance(ComputedBalance computedBalance) {
        this.computedBalance = computedBalance;
    }

    public BalanceResponse(){
    }
}
