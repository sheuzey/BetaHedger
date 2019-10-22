package model.Accounts.Balances;

public class Lending {

    private Number currentBalance;
    private Number creditLine;
    private Number outstandingBalance;
    private Number minPaymentDue;
    private Number amountPastDue;
    private Number availableCredit;
    private Number ytdInterestPaid;
    private Number lastYtdInterestPaid;
    private Number paymentDueDate;
    private Number lastPaymentReceivedDate;
    private Number paymentReceivedMtd;

    public Number getCurrentBalance() {
        return currentBalance;
    }
    public Number getCreditLine() {
        return creditLine;
    }
    public Number getOutstandingBalance() {
        return outstandingBalance;
    }
    public Number getMinPaymentDue() {
        return minPaymentDue;
    }
    public Number getAmountPastDue() {
        return amountPastDue;
    }
    public Number getAvailableCredit() {
        return availableCredit;
    }
    public Number getYtdInterestPaid() {
        return ytdInterestPaid;
    }
    public Number getLastYtdInterestPaid() {
        return lastYtdInterestPaid;
    }
    public Number getPaymentDueDate() {
        return paymentDueDate;
    }
    public Number getLastPaymentReceivedDate() {
        return lastPaymentReceivedDate;
    }
    public Number getPaymentReceivedMtd() {
        return paymentReceivedMtd;
    }

    public void setCurrentBalance(Number currentBalance) {
        this.currentBalance = currentBalance;
    }
    public void setCreditLine(Number creditLine) {
        this.creditLine = creditLine;
    }
    public void setOutstandingBalance(Number outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
    public void setMinPaymentDue(Number minPaymentDue) {
        this.minPaymentDue = minPaymentDue;
    }
    public void setAmountPastDue(Number amountPastDue) {
        this.amountPastDue = amountPastDue;
    }
    public void setAvailableCredit(Number availableCredit) {
        this.availableCredit = availableCredit;
    }
    public void setYtdInterestPaid(Number ytdInterestPaid) {
        this.ytdInterestPaid = ytdInterestPaid;
    }
    public void setLastYtdInterestPaid(Number lastYtdInterestPaid) {
        this.lastYtdInterestPaid = lastYtdInterestPaid;
    }
    public void setPaymentDueDate(Number paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }
    public void setLastPaymentReceivedDate(Number lastPaymentReceivedDate) {
        this.lastPaymentReceivedDate = lastPaymentReceivedDate;
    }
    public void setPaymentReceivedMtd(Number paymentReceivedMtd) {
        this.paymentReceivedMtd = paymentReceivedMtd;
    }

    public Lending(){
    }
}
