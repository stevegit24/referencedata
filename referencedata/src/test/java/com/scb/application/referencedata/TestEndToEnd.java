package com.scb.application.referencedata;

import com.scb.application.referencedata.instruments.LmeInstrument;
import com.scb.application.referencedata.instruments.PrimeInstrument;
import com.scb.application.referencedata.instruments.PublishedInstrument;
import com.scb.application.referencedata.instruments.visitor.ExchangeCodeMapperExtractor;
import com.scb.application.referencedata.rules.LmePrimeMergeRule;
import com.scb.application.referencedata.rules.MergeRule;
import com.scb.application.referencedata.rules.MergeRuleBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestEndToEnd {

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

        assertTrue(fromCache.isTradable());
    	
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

        assertTrue(fromCache.isTradable());

        LocalDate primeTradingDate = LocalDate.of(2018, 3, 14);
        LocalDate primeDeliveryDate = LocalDate.of(2018, 3, 18);
 
        cache.addInstrument(new PrimeInstrument(primeTradingDate, primeDeliveryDate, market,
                label, "PB_03_2018", false));

        PublishedInstrument fromCacheAfterPrime = (PublishedInstrument) cache.getState().get("PB_03_2018");

        assertFalse(fromCacheAfterPrime.isTradable());
        assertTrue(fromCacheAfterPrime.getDeliveryDate().equals(deliveryDate));
        assertTrue(fromCacheAfterPrime.getLastTradingDate().equals(tradingDate));
        //System.out.println(fromCacheAfterPrime.getLastTradingDate() +"   del : "+fromCacheAfterPrime.getDeliveryDate());


    }
}
