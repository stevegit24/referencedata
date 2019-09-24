package com.scb.application.referencedata.instruments.visitor;

import com.scb.application.referencedata.instruments.BaseInstrument;
import com.scb.application.referencedata.instruments.LmeInstrument;
import com.scb.application.referencedata.instruments.PrimeInstrument;
import com.scb.application.referencedata.instruments.PublishedInstrument;

import static java.lang.String.format;

public class ExchangeCodeMapperExtractor implements InstrumentVisitor<Void, String> {

    @Override
    public String visitLmeInstrument(LmeInstrument lmeInstrument, Void input) {
        return getExchangeCode(lmeInstrument);
    }

    private String getExchangeCode(BaseInstrument baseInstrument) {
        int monthValue = baseInstrument.getDeliveryDate().getMonthValue();
        int year = baseInstrument.getDeliveryDate().getYear();
        
        String code = "";
        if(baseInstrument.getMarket().contains("_"))
        	code = baseInstrument.getMarket().split("_")[1];
        else
        	code = baseInstrument.getMarket();
        return format("%s_%02d_%s", code, monthValue, year);
    }

    @Override
    public String visitPrimeInstrument(PrimeInstrument primeInstrument, Void input) {
        return primeInstrument.getExchangeCode();
    }

    @Override
    public String visitPublishedInstrument(PublishedInstrument publishedInstrument, Void input) {
        return getExchangeCode(publishedInstrument);
    }
}
