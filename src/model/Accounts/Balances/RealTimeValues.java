package model.Accounts.Balances;

public class RealTimeValues {

    private Number totalAccountValue;
    private Number netMv;
    private Number netMvLong;
    private Number netMvShort;
    private Number totalLongValue;

    public Number getTotalAccountValue() {
        return totalAccountValue;
    }
    public Number getNetMv() {
        return netMv;
    }
    public Number getNetMvLong() {
        return netMvLong;
    }
    public Number getNetMvShort() {
        return netMvShort;
    }
    public Number getTotalLongValue() {
        return totalLongValue;
    }

    public void setTotalAccountValue(Number totalAccountValue) {
        this.totalAccountValue = totalAccountValue;
    }
    public void setNetMv(Number netMv) {
        this.netMv = netMv;
    }
    public void setNetMvLong(Number netMvLong) {
        this.netMvLong = netMvLong;
    }
    public void setNetMvShort(Number netMvShort) {
        this.netMvShort = netMvShort;
    }
    public void setTotalLongValue(Number totalLongValue) {
        this.totalLongValue = totalLongValue;
    }

    public RealTimeValues(){
    }
}
