package com.scb.application.referencedata.rules;

import com.scb.application.referencedata.instruments.Instrument;

public class LmePrimeMergeRule implements MergeRule {

    private LmePrimeMergeRuleVisitor mergeRuleVisitor =
            new LmePrimeMergeRuleVisitor();

    @Override
    public Instrument merge(Instrument oldInstrument, Instrument newInstrument) {
            return oldInstrument.accept(mergeRuleVisitor, newInstrument);
    }
}
