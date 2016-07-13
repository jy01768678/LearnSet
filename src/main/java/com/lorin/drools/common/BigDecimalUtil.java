package com.lorin.drools.common;

import com.lorin.drools.fact.BigDecimalFact;
import com.lorin.drools.fact.OriginalMapFact;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by lorin on 16/7/13.
 */

public class BigDecimalUtil {


    public static boolean compare2(OriginalMapFact map ,BigDecimalFact peCurrentBankCardIntradayTotalTradesFact,BigDecimalFact currentValue){

        Map<String,String> _map =map.getValue();
        String value = _map.get("tradeAmount");
        BigDecimal tradeAmount = new BigDecimal(value);
        BigDecimal a = tradeAmount.add(peCurrentBankCardIntradayTotalTradesFact.getValue());
        return compare(a,currentValue.getValue());

    }

    public static boolean compare(BigDecimal first,BigDecimal second){
        if(first.compareTo(second) > 0)
            return true;
        return false;
    }

    public static double string2Double(String param){
        if(param == null || "".equals(param)){
            return 0;
        }
        return new Double(param.replaceAll(" ", ""));
    }

    public static double judgeBigDecimalNull(BigDecimal b){
        if(b != null){
            return b.doubleValue();
        }else{
            return 0;
        }
    }

}

