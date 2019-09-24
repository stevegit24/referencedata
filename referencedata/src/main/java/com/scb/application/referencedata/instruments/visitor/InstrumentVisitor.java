package com.scb.application.referencedata.instruments.visitor;


import com.scb.application.referencedata.instruments.LmeInstrument;
import com.scb.application.referencedata.instruments.PrimeInstrument;
import com.scb.application.referencedata.instruments.PublishedInstrument;

public interface InstrumentVisitor<I, O> {
    O visitLmeInstrument(LmeInstrument lmeInstrument, I input);

    O visitPrimeInstrument(PrimeInstrument primeInstrument, I input);

    O visitPublishedInstrument(PublishedInstrument publishedInstrument, I input);
}
