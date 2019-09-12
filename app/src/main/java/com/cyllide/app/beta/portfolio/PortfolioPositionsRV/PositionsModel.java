package com.cyllide.app.beta.portfolio.PortfolioPositionsRV;

public class PositionsModel {
    private String positionTicker,positionQuantity,positionCurrPrice,positionType,positionValue;
    String positionltp;
    String positionCost;
    boolean isProfit;

    public PositionsModel() {
    }

    public PositionsModel(String positionTicker, String positionQuantity, String positionCurrPrice, String positionltp) {
        this.positionTicker = positionTicker;
        this.positionQuantity = positionQuantity;
        this.positionCurrPrice = positionCurrPrice;
        this.positionltp = positionltp;
    }

    public boolean isProfit() {
        return isProfit;
    }

    public void setProfit(boolean profit) {
        isProfit = profit;
    }

    public String getPositionltp() {
        return positionltp;
    }

    public void setPositionltp(String positionltp) {
        this.positionltp = positionltp;
    }

    public String getPositionCost() {
        return positionCost;
    }

    public void setPositionCost(String positionCost) {
        this.positionCost = positionCost;
    }

    public PositionsModel(String positionTicker, int positionQuantity, String positionCurrPrice, Boolean positionType, Double positionValue, double positionltp, double positionCost ) {
        this.positionTicker = positionTicker;
        this.positionQuantity = String.valueOf(positionQuantity);
        this.positionCurrPrice = positionCurrPrice;
        this.positionltp = Double.toString(positionltp);
        this.positionCost = Double.toString(positionCost);
        if(positionType){
            this.positionType = "LONG";
        }
        else{
            this.positionType = "SHORT";
        }
        this.positionValue = String.valueOf(positionValue);
    } public PositionsModel(String positionTicker, int positionQuantity, String positionCurrPrice, Boolean positionType, Double positionValue ) {
        this.positionTicker = positionTicker;
        this.positionQuantity = String.valueOf(positionQuantity);
        this.positionCurrPrice = positionCurrPrice;
        if(positionType){
            this.positionType = "LONG";
        }
        else{
            this.positionType = "SHORT";
        }
        this.positionValue = String.valueOf(positionValue);
    }

    public String getPositionTicker() {
        return positionTicker;
    }

    public void setPositionTicker(String positionTicker) {
        this.positionTicker = positionTicker;
    }

    public String getPositionQuantity() {
        return positionQuantity;
    }

    public void setPositionQuantity(String positionQuantity) {
        this.positionQuantity = positionQuantity;
    }

    public String getPositionCurrPrice() {
        return positionCurrPrice;
    }

    public void setPositionCurrPrice(String positionCurrPrice) {
        this.positionCurrPrice = positionCurrPrice;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPositionValue() {
        return positionValue;
    }

    public void setPositionValue(String positionValue) {
        this.positionValue = positionValue;
    }
}
