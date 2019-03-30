package com.example.kartikbhardwaj.bottom_navigation.portfolio.PendingOrdersRV;

public class OrdersModel {

  private String PositionType;
  private String Quantity;
  private String StockTicker;
  private String CurrentStockPrice;

    public OrdersModel(Boolean positionType, Integer quantity, String stockTicker, String currentStockPrice) {
        if(positionType){
            PositionType = "LONG";
        }
        else{
            PositionType = "SHORT";
        }
        Quantity = String.valueOf(quantity);
        StockTicker = stockTicker;
        CurrentStockPrice = currentStockPrice;
    }

    public String getCurrentStockPrice() {

        return CurrentStockPrice;
    }

    public void setCurrentStockPrice(String currentStockPrice) {
        CurrentStockPrice = currentStockPrice;
    }

    public String getStockTicker() {
        return StockTicker;
    }

    public void setStockTicker(String stockTicker) {
        StockTicker = stockTicker;
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
