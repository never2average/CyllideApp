package com.example.kartikbhardwaj.bottom_navigation.portfolio.PortfolioPositionsRV;

public class PositionsModel {
    private String positionTicker,positionQuantity,positionCurrPrice,positionType,positionValue;

    public PositionsModel(String positionTicker, int positionQuantity, String positionCurrPrice, Boolean positionType, Double positionValue) {
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
