package com.scb.application.referencedata.instruments;

import java.time.LocalDate;

public abstract class BaseInstrument implements Instrument {
    protected LocalDate lastTradingDate;
    protected LocalDate deliveryDate;
    protected String market;
    protected String label;
    protected boolean tradable;


    public BaseInstrument(LocalDate lastTradingDate, LocalDate deliveryDate, String market, String label,
                          boolean tradable) {
        this.lastTradingDate = lastTradingDate;
        this.deliveryDate = deliveryDate;
        this.market = market;
        this.label = label;
        this.tradable = tradable;
    }

    public LocalDate getLastTradingDate() {
        return lastTradingDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public String getMarket() {
        return market;
    }

    public String getLabel() {
        return label;
    }

    public boolean isTradable() {
        return tradable;
    }


}
