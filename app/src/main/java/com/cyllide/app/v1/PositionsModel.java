package com.cyllide.app.v1;

public class PositionsModel {
    long quantity;
    String name;
    double avgPrice;
    long ltp;
    double cost;

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public long getLtp() {
        return ltp;
    }

    public void setLtp(long ltp) {
        this.ltp = ltp;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public PositionsModel(long quantity, String name, double avgPrice, long ltp, double cost) {
        this.quantity = quantity;
        this.name = name;
        this.avgPrice = avgPrice;
        this.ltp = ltp;
        this.cost = cost;
    }
}
