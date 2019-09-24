package com.scb.application.referencedata.instruments;

import com.scb.application.referencedata.instruments.visitor.InstrumentVisitor;

import java.time.LocalDate;

public class PrimeInstrument extends BaseInstrument {

    private String exchangeCode;

    public PrimeInstrument(LocalDate lastTradingDate, LocalDate deliveryDate,
                           String market, String label, String exchangeCode, boolean tradable) {
        super(lastTradingDate, deliveryDate, market, label,tradable);
        this.exchangeCode = exchangeCode;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    @Override
    public <I, O> O accept(InstrumentVisitor<I, O> visitor, I input) {
        return visitor.visitPrimeInstrument(this, input);
    }
}
