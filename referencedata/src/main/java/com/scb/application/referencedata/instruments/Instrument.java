package com.scb.application.referencedata.instruments;

import com.scb.application.referencedata.instruments.visitor.InstrumentVisitor;

public interface Instrument {

     <I, O> O accept(InstrumentVisitor<I, O> visitor, I input);

}
