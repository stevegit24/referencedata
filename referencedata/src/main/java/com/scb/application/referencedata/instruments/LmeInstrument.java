package com.scb.application.referencedata.instruments;

import com.scb.application.referencedata.instruments.visitor.InstrumentVisitor;

import java.time.LocalDate;

public class LmeInstrument extends BaseInstrument {


    public LmeInstrument(LocalDate lastTradingDate, LocalDate deliveryDate,
                         String market, String label) {
        super(lastTradingDate, deliveryDate, market, label, true);
    }

    @Override
    public <I, O> O accept(InstrumentVisitor<I, O> visitor, I input) {
        return visitor.visitLmeInstrument(this, input);
    }

}
