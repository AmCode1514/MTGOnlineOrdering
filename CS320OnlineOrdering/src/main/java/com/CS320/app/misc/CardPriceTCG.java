package com.CS320.app.misc;

public class CardPriceTCG {
    private String printingType;
    private double marketPrice;
    private double buylistMarketPrice; 
    private double listedMedianPrice;
    
    public String getPrintingType() {
        return printingType;
    }

    public void setPrintingType(String printingType) {
        this.printingType = printingType;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getBuylistMarketPrice() {
        return buylistMarketPrice;
    }

    public void setBuylistMarketPrice(double buylistMarketPrice) {
        this.buylistMarketPrice = buylistMarketPrice;
    }

    public double getListedMedianPrice() {
        return listedMedianPrice;
    }

    public void setListedMedianPrice(double listedMedianPrice) {
        this.listedMedianPrice = listedMedianPrice;
    }
}