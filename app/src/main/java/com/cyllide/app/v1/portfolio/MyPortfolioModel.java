package com.cyllide.app.v1.portfolio;

public class MyPortfolioModel {

   private String portfolioname;
   private String capex;
   private String id;

    public String getCapex() {
        return capex;
    }

    public void setCapex(String capex) {
        this.capex = capex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPortfolioname() {

        return portfolioname;
    }

    public void setPortfolioname(String portfolioname) {
        this.portfolioname = portfolioname;
    }

    public MyPortfolioModel(String portfolioname, String capex, String id) {
        this.portfolioname = portfolioname;
        this.capex = capex;
        this.id = id;
    }
}
