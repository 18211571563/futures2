package com.tcl.bean;

/**
 * Created by yangqj on 2017/1/19.
 */
public class TradeRecordBean {

    public TradeRecordBean(int tradeCount, double singlePrice){
        this.tradeCount = tradeCount;
        this.singlePrice = singlePrice;
    }

    private int tradeCount;
    private double singlePrice;

    public int getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(int tradeCount) {
        this.tradeCount = tradeCount;
    }

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }
}
