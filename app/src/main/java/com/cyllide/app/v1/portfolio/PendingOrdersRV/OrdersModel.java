package com.cyllide.app.v1.portfolio.PendingOrdersRV;

public class OrdersModel {

    private String PositionType;
    private String Quantity;
    private String StockTicker;


    public OrdersModel(Boolean positionType, Integer quantity, String stockTicker) {
        if(positionType){
            this.PositionType = "LONG";
        }
        else{
            this.PositionType = "SHORT";
        }
        this.Quantity = String.valueOf(quantity);
        this.StockTicker = stockTicker;
    }

    public void setStockTicker(String stockTicker) {
        StockTicker = stockTicker;
    }

    public String getStockTicker() {
        return StockTicker;
    }


    public String getPositionType() {
        return PositionType;
    }

    public void setPositionType(String positionType) {
        PositionType = positionType;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

}
