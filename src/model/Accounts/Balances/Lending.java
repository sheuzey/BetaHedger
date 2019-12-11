package model.Accounts.Balances;

public class Lending {

    private Double currentBalance;
    private Double creditLine;
    private Double outstandingBalance;
    private Double minPaymentDue;
    private Double amountPastDue;
    private Double availableCredit;
    private Double ytdInterestPaid;
    private Double lastYtdInterestPaid;
    private Double paymentDueDate;
    private Double lastPaymentReceivedDate;
    private Double paymentReceivedMtd;

    public Double getCurrentBalance() {
        return currentBalance;
    }
    public Double getCreditLine() {
        return creditLine;
    }
    public Double getOutstandingBalance() {
        return outstandingBalance;
    }
    public Double getMinPaymentDue() {
        return minPaymentDue;
    }
    public Double getAmountPastDue() {
        return amountPastDue;
    }
    public Double getAvailableCredit() {
        return availableCredit;
    }
    public Double getYtdInterestPaid() {
        return ytdInterestPaid;
    }
    public Double getLastYtdInterestPaid() {
        return lastYtdInterestPaid;
    }
    public Double getPaymentDueDate() {
        return paymentDueDate;
    }
    public Double getLastPaymentReceivedDate() {
        return lastPaymentReceivedDate;
    }
    public Double getPaymentReceivedMtd() {
        return paymentReceivedMtd;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }
    public void setCreditLine(Double creditLine) {
        this.creditLine = creditLine;
    }
    public void setOutstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
    public void setMinPaymentDue(Double minPaymentDue) {
        this.minPaymentDue = minPaymentDue;
    }
    public void setAmountPastDue(Double amountPastDue) {
        this.amountPastDue = amountPastDue;
    }
    public void setAvailableCredit(Double availableCredit) {
        this.availableCredit = availableCredit;
    }
    public void setYtdInterestPaid(Double ytdInterestPaid) {
        this.ytdInterestPaid = ytdInterestPaid;
    }
    public void setLastYtdInterestPaid(Double lastYtdInterestPaid) {
        this.lastYtdInterestPaid = lastYtdInterestPaid;
    }
    public void setPaymentDueDate(Double paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }
    public void setLastPaymentReceivedDate(Double lastPaymentReceivedDate) {
        this.lastPaymentReceivedDate = lastPaymentReceivedDate;
    }
    public void setPaymentReceivedMtd(Double paymentReceivedMtd) {
        this.paymentReceivedMtd = paymentReceivedMtd;
    }

    public Lending(){
    }
}
