package com.myspring.models;

public class Rates {

    private Long id;

    private String base;

    private double currency;

    private String rates;

    public Rates() {
    }

    public Rates(Long id, String base, double currency, String rates) {
        this.id = id;
        this.base = base;
        this.currency = currency;
        this.rates = rates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public double getCurrency() {
        return currency;
    }

    public void setCurrency(double currency) {
        this.currency = currency;
    }

    public String toString() {
        return  base  + " to " + rates  + ": " +  currency ;
    }
}
