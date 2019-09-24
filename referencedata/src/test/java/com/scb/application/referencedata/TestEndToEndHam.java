package com.scb.application.referencedata;

import com.scb.application.referencedata.instruments.LmeInstrument;
import com.scb.application.referencedata.instruments.PrimeInstrument;
import com.scb.application.referencedata.instruments.PublishedInstrument;
import com.scb.application.referencedata.instruments.visitor.ExchangeCodeMapperExtractor;
import com.scb.application.referencedata.rules.LmePrimeMergeRule;
import com.scb.application.referencedata.rules.MergeRule;
import com.scb.application.referencedata.rules.MergeRuleBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class TestEndToEndHam {

    private InstrumentCache<String> cache;
    private MergeRule mergeRule;
    
    @Before
    public void setUp() {
        this.mergeRule = new MergeRuleBuilder().addRule(new LmePrimeMergeRule()).build();
        this.cache = new InstrumentCache<>(new ExchangeCodeMapperExtractor(), mergeRule);
    }

    @Test
    public void testLme() {
        LocalDate tradingDate = LocalDate.of(2018, 3, 15);
        LocalDate deliveryDate = LocalDate.of(2018, 3, 17);
        String market = "PB";
        String label = "Lead 13 March 2018";

        LmeInstrument lmeInstrument = new LmeInstrument(tradingDate, deliveryDate, market, label);
        cache.addInstrument(lmeInstrument);

        PublishedInstrument fromCache = (PublishedInstrument) cache.getState().get("PB_03_2018");

        //assertTrue(fromCache.isTradable());
        assertThat(fromCache, hasProperty("tradable", is(true)));
    	
    }
    @Test
    public void testPrimeAfterLme() {
        LocalDate tradingDate = LocalDate.of(2018, 3, 15);
        LocalDate deliveryDate = LocalDate.of(2018, 3, 17);
        String market = "LME_PB";
        String label = "Lead 13 March 2018";

        LmeInstrument lmeInstrument = new LmeInstrument(tradingDate, deliveryDate, market, label);
        cache.addInstrument(lmeInstrument);

        PublishedInstrument fromCache = (PublishedInstrument) cache.getState().get("PB_03_2018");

        assertThat(fromCache, hasProperty("tradable", is(true)));

        LocalDate primeTradingDate = LocalDate.of(2018, 3, 14);
        LocalDate primeDeliveryDate = LocalDate.of(2018, 3, 18);
 
        cache.addInstrument(new PrimeInstrument(primeTradingDate, primeDeliveryDate, market,
                label, "PB_03_2018", false));

        PublishedInstrument fromCacheAfterPrime = (PublishedInstrument) cache.getState().get("PB_03_2018");

        assertThat(fromCacheAfterPrime, hasProperty("tradable", is(false)));
        assertThat(fromCacheAfterPrime, hasProperty("deliveryDate", is(deliveryDate)));
        assertThat(fromCacheAfterPrime, hasProperty("lastTradingDate", is(tradingDate)));

   
    }


}
