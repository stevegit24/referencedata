package com.scb.application.referencedata.rules;

import com.scb.application.referencedata.instruments.Instrument;

public interface MergeRule {

    Instrument merge(Instrument first, Instrument second);
}
