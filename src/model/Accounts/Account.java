package model.Accounts;

public class Account {

    private String accountId;
    private String accountIdKey;
    private String accountMode;
    private String accountDesc;
    private String accountName;
    private String accountType;
    private String institutionType;
    private String accountStatus;
    private Integer closedDate;

    public String getAccountId() {
        return accountId;
    }
    public String getAccountIdKey() {
        return accountIdKey;
    }
    public String getAccountMode() {
        return accountMode;
    }
    public String getAccountDesc() {
        return accountDesc;
    }
    public String getAccountName() {
        return accountName;
    }
    public String getAccountType() {
        return accountType;
    }
    public String getInstitutionType() {
        return institutionType;
    }
    public String getAccountStatus() {
        return accountStatus;
    }
    public Integer getClosedDate() {
        return closedDate;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public void setAccountIdKey(String accountIdKey) {
        this.accountIdKey = accountIdKey;
    }
    public void setAccountMode(String accountMode) {
        this.accountMode = accountMode;
    }
    public void setAccountDesc(String accountDesc) {
        this.accountDesc = accountDesc;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
    public void setClosedDate(Integer closedDate) {
        this.closedDate = closedDate;
    }

    public Account() {
    }
}
