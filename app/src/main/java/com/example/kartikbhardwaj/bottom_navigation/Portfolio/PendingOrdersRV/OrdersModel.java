package com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersRV;

public class OrdersModel {

  private String PositionType;
  private String Quantity;
  private String StockTicker;
  private String CurrentStockPrice;

    public OrdersModel(String positionType, String quantity, String stockTicker, String currentStockPrice) {
        PositionType = positionType;
        Quantity = quantity;
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
