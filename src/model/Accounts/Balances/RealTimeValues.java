package model.Accounts.Balances;

public class RealTimeValues {

    private Double totalAccountValue;
    private Double netMv;
    private Double netMvLong;
    private Double netMvShort;
    private Double totalLongValue;

    public Double getTotalAccountValue() {
        return totalAccountValue;
    }
    public Double getNetMv() {
        return netMv;
    }
    public Double getNetMvLong() {
        return netMvLong;
    }
    public Double getNetMvShort() {
        return netMvShort;
    }
    public Double getTotalLongValue() {
        return totalLongValue;
    }

    public void setTotalAccountValue(Double totalAccountValue) {
        this.totalAccountValue = totalAccountValue;
    }
    public void setNetMv(Double netMv) {
        this.netMv = netMv;
    }
    public void setNetMvLong(Double netMvLong) {
        this.netMvLong = netMvLong;
    }
    public void setNetMvShort(Double netMvShort) {
        this.netMvShort = netMvShort;
    }
    public void setTotalLongValue(Double totalLongValue) {
        this.totalLongValue = totalLongValue;
    }

    public RealTimeValues(){
    }
}
