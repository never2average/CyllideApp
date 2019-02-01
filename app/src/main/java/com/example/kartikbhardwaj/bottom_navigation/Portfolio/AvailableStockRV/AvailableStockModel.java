package com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStockRV;

public class AvailableStockModel {
    private String indexName;
    private double indexValue;
    private double indexChanges;

    public AvailableStockModel(String indexName, double indexValue, double indexChanges) {
        this.indexName = indexName;
        this.indexValue = indexValue;
        this.indexChanges = indexChanges;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public double getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(double indexValue) {
        this.indexValue = indexValue;
    }

    public double getIndexChanges() {
        return indexChanges;
    }

    public void setIndexChanges(double indexChanges) {
        this.indexChanges = indexChanges;
    }
}
