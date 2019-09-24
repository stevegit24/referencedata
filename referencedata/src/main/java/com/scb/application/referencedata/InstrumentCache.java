package com.scb.application.referencedata;

import com.scb.application.referencedata.instruments.Instrument;
import com.scb.application.referencedata.instruments.visitor.InstrumentVisitor;
import com.scb.application.referencedata.rules.MergeRule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InstrumentCache<T> {

    private InstrumentVisitor<Void, T> codeMapper;
    private MergeRule rule;
    private Map<T, Instrument> data;

    public InstrumentCache(InstrumentVisitor<Void, T> codeMapper, MergeRule rule) {
        this.codeMapper = codeMapper;
        this.rule = rule;
        this.data = new ConcurrentHashMap<>();
    }

    public void addInstrument(Instrument instrument) {
        T code = instrument.accept(codeMapper, null);
        data.compute(code, (key, oldValue) -> {
            if(oldValue == null) {
                return rule.merge(instrument, null);
            }
            return rule.merge(oldValue, instrument);
        });
    }

    protected Map<T, Instrument> getState() {
        return new ConcurrentHashMap<>(data);
    }
}
