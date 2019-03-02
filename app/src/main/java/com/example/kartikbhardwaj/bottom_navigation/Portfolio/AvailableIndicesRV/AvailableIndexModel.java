package com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableIndicesRV;

public class AvailableIndexModel {
    private String indexName;
    private String indexValue;
    private double indexChanges;

    public AvailableIndexModel(String indexName, String indexValue, double indexChanges) {
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

    public String getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }

    public double getIndexChanges() {
        return indexChanges;
    }

    public void setIndexChanges(double indexChanges) {
        this.indexChanges = indexChanges;
    }
}
