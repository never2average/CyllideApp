package com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersRV;

public class OrdersModel {

  private   String OrderType;
  private   String PositionType;
  private   String Quantity;
  private   String ExecutionPrice;
  private String StockTicker;
  private String CurrentStockPrice;

    public OrdersModel(String orderType, String positionType, String quantity, String executionPrice, String stockTicker, String currentStockPrice) {
        OrderType = orderType;
        PositionType = positionType;
        Quantity = quantity;
        ExecutionPrice = executionPrice;
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

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
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

    public String getExecutionPrice() {
        return ExecutionPrice;
    }

    public void setExecutionPrice(String executionPrice) {
        ExecutionPrice = executionPrice;
    }
}
