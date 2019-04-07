package com.cyllide.app.v1.contests.positionsRV;

public class Positions2 {

    String ticker;
    int quantity;
    double entryPrice;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(double entryPrice) {
        this.entryPrice = entryPrice;
    }

    public Positions2(String ticker, int quantity, double entryPrice) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.entryPrice = entryPrice;
    }
}
