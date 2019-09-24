package com.scb.application.referencedata.rules;

import com.scb.application.referencedata.instruments.Instrument;

import java.util.ArrayList;
import java.util.List;

public class MergeRuleBuilder {

    private List<MergeRule> rules;

    public MergeRuleBuilder() {
        this.rules = new ArrayList<>();
    }

    public MergeRuleBuilder addRule(MergeRule mergeRule) {
        this.rules.add(mergeRule);
        return this;
    }

    public MergeRule build() {
        return new CompositeRule(rules);
    }

    private class CompositeRule implements MergeRule {

        List<MergeRule> rules;

        public CompositeRule(List<MergeRule> rules) {
            this.rules = rules;
        }

        @Override
        public Instrument merge(Instrument first, Instrument second) {
            Instrument newInstrument = first;
            for (MergeRule rule : rules) {
                newInstrument = rule.merge(newInstrument, second);
            }
            return newInstrument;
        }
    }

}
