package com.cyllide.app.v1.contests.positionsRV;

public class Positions2 {

    String name;
    double value;
    double returns;


    public Positions2(String name, double value, double returns) {
        this.name = name;
        this.value = value;
        this.returns = returns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getReturns() {
        return returns;
    }

    public void setReturns(double returns) {
        this.returns = returns;
    }
}
