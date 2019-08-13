package com.itdevstar.excelparser;

public class TableDataModel {
    public String symbol;
    public String entranceType;
    public String optionType;
    public String strike;
    public String orderDate;
    public String orderTime;
    public String expiration;
    public String contacts;
    public String premium;
    public String totalValue;
    public String strategy;
    public String bearish_bullish;

    public TableDataModel(String symbol, String entranceType, String optionType, String strike,
                          String orderDate, String orderTime, String expiration, String contacts,
                          String premium, String totalValue, String strategy, String bearish_bullish) {
        this.symbol = symbol;
        this.entranceType = entranceType;
        this.optionType = optionType;
        this.strike = strike;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.expiration = expiration;
        this.contacts = contacts;
        this.premium = premium;
        this.totalValue = totalValue;
        this.strategy = strategy;
        this.bearish_bullish = bearish_bullish;
    }
}
