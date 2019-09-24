package com.scb.application.referencedata.rules;

import com.scb.application.referencedata.instruments.Instrument;
import com.scb.application.referencedata.instruments.LmeInstrument;
import com.scb.application.referencedata.instruments.PrimeInstrument;
import com.scb.application.referencedata.instruments.PublishedInstrument;
import com.scb.application.referencedata.instruments.factory.InstrumentFactory;
import com.scb.application.referencedata.instruments.visitor.MergeInstrumentVisitor;

public class LmePrimeMergeRuleVisitor implements MergeInstrumentVisitor {

    @Override
    public Instrument visitLmeInstrument(LmeInstrument lmeInstrument, Instrument input) {
        return InstrumentFactory.getInstance().createPublishedInstrument(lmeInstrument);
    }

    @Override
    public Instrument visitPrimeInstrument(PrimeInstrument primeInstrument, Instrument input) {
        return InstrumentFactory.getInstance().createPublishedInstrument(primeInstrument);
    }

    @Override
    public Instrument visitPublishedInstrument(PublishedInstrument publishedInstrument, Instrument input) {
        switch (publishedInstrument.getSource()) {
            case LME:
                if (input instanceof PrimeInstrument) {
                    return publishedInstrument.withTradeable(((PrimeInstrument) input).isTradable());
                }
                return publishedInstrument;
            case PRIME:
                if (input instanceof LmeInstrument) {
                    LmeInstrument lmeIn = (LmeInstrument) input;
                    return publishedInstrument
                            .withLastTradingDate(lmeIn.getLastTradingDate())
                            .withDeliveryDate(lmeIn.getDeliveryDate());
                }
                return publishedInstrument;
            default:
                throw new RuntimeException("Not implemented");
        }
    }
}
