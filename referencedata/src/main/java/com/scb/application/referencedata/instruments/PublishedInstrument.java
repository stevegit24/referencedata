package com.scb.application.referencedata.instruments;

import com.scb.application.referencedata.instruments.visitor.InstrumentVisitor;

import java.time.LocalDate;

public class PublishedInstrument extends BaseInstrument {

    private Source source;

    public PublishedInstrument(LocalDate lastTradingDate, LocalDate deliveryDate,
                               String market, String label, Source source, boolean tradable) {
        super(lastTradingDate, deliveryDate, market, label, tradable);
        this.source = source;
    }

    @Override
    public <I, O> O accept(InstrumentVisitor<I, O> visitor, I input) {
        return visitor.visitPublishedInstrument(this, input);
    }

    public Source getSource() {
        return source;
    }

    public PublishedInstrument withTradeable(boolean isTradable) {
        this.tradable = isTradable;
        return this;
    }

    public PublishedInstrument withLastTradingDate(LocalDate lastTradingDate) {
        this.lastTradingDate = lastTradingDate;
        return this;
    }

    public PublishedInstrument withDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }


}
