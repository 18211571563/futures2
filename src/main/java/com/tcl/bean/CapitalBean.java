package com.tcl.bean;

/**
 * Created by yangqj on 2017/1/18.
 */
public class CapitalBean {

    @Override
    public String toString() {
        return "CapitalBean >>>>>>>>>>>>> " +
                "totalCapital:" + totalCapital +
                " , cashCapital:" + cashCapital +
                " , nonCashAssets:" + nonCashAssets +
                " , riskRatio:" + riskRatio +
                " , tradeCount:" + tradeCount +
                " , addTradeCount:" + addTradeCount +
                " , singlePrice:" + singlePrice ;
    }

    public CapitalBean(double totalCapital, double cashCapital, double nonCashAssets, double riskRatio, int tradeCount,  int addTradeCount, double singlePrice){
        this.totalCapital = totalCapital;
        this.cashCapital = cashCapital;
        this.nonCashAssets = nonCashAssets;
        this.riskRatio = riskRatio;
        this.tradeCount = tradeCount;
        this.addTradeCount = addTradeCount;
        this.singlePrice = singlePrice;
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>> 资金信息 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 总资金
    private double totalCapital = 100000;
    // 现金资产
    private double cashCapital = 100000;
    // 非现金总资产
    private double nonCashAssets = 0;
    // 风险率
    private double riskRatio;

    // >>>>>>>>>>>>>>>>>>>>>>>>>> 交易记录 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 交易量
    private int tradeCount = 0;
    // 交易增量
    private int addTradeCount = 0;
    // 交易单价
    private double singlePrice = 0;

    public double getTotalCapital() {
        return totalCapital;
    }

    public void setTotalCapital(double totalCapital) {
        this.totalCapital = totalCapital;
    }

    public double getCashCapital() {
        return cashCapital;
    }

    public void setCashCapital(double cashCapital) {
        this.cashCapital = cashCapital;
    }

    public double getNonCashAssets() {
        return nonCashAssets;
    }

    public void setNonCashAssets(double nonCashAssets) {
        this.nonCashAssets = nonCashAssets;
    }

    public double getRiskRatio() {
        return riskRatio;
    }

    public void setRiskRatio(double riskRatio) {
        this.riskRatio = riskRatio;
    }

    public int getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(int tradeCount) {
        this.tradeCount = tradeCount;
    }

    public int getAddTradeCount() {
        return addTradeCount;
    }

    public void setAddTradeCount(int addTradeCount) {
        this.addTradeCount = addTradeCount;
    }

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }
}
