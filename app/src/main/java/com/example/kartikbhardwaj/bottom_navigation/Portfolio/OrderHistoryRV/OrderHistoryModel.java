package com.example.kartikbhardwaj.bottom_navigation.Portfolio.OrderHistoryRV;

public class OrderHistoryModel {
    private String stockTicker;
    private String positionType;
    private String exitTime;
    private double entryPrice, exitPrice;
    private int quantity;

    public OrderHistoryModel(String stockTicker, String positionType, String exitTime, double entryPrice, double exitPrice, int quantity) {
        this.stockTicker = stockTicker;
        this.positionType = positionType;
        this.exitTime = exitTime;
        this.entryPrice = entryPrice;
        this.exitPrice = exitPrice;
        this.quantity = quantity;
    }

    public String getStockTicker() {
        return stockTicker;
    }

    public void setStockTicker(String stockTicker) {
        this.stockTicker = stockTicker;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(double entryPrice) {
        this.entryPrice = entryPrice;
    }

    public double getExitPrice() {
        return exitPrice;
    }

    public void setExitPrice(double exitPrice) {
        this.exitPrice = exitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}