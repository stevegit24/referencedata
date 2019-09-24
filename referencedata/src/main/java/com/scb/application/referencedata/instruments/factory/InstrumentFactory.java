package com.scb.application.referencedata.instruments.factory;

import com.scb.application.referencedata.instruments.*;

import java.time.LocalDate;

public class InstrumentFactory {

    private static InstrumentFactory INSTANCE = new InstrumentFactory();

    private InstrumentFactory () {}

    public static InstrumentFactory getInstance() {
        return INSTANCE;
    }


    public Instrument createPrimeInstrument(LocalDate lastTradingDate, LocalDate deliveryDate,
                                            String market, String label, String exchangeCode, boolean tradable) {

        return new PrimeInstrument(lastTradingDate, deliveryDate, market, label, exchangeCode, tradable);
    }

    public Instrument createLmeInstrument(LocalDate lastTradingDate, LocalDate deliveryDate,
                                            String market, String label) {

        return new LmeInstrument(lastTradingDate, deliveryDate, market, label);
    }

    public Instrument createPublishedInstrument(LmeInstrument lmeInstrument) {
        return new PublishedInstrument(lmeInstrument.getLastTradingDate(), lmeInstrument.getDeliveryDate(),
                lmeInstrument.getMarket(), lmeInstrument.getLabel(), Source.LME, lmeInstrument.isTradable());
    }

    public Instrument createPublishedInstrument(PrimeInstrument primeInstrument) {
        return new PublishedInstrument(primeInstrument.getLastTradingDate(), primeInstrument.getDeliveryDate(),
                primeInstrument.getMarket(), primeInstrument.getLabel(), Source.PRIME, primeInstrument.isTradable());
    }


}
