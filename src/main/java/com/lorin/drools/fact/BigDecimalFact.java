package com.lorin.drools.fact;

//import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Created by baidu on 16/7/7.
 */
public class BigDecimalFact extends com.lorin.drools.fact.SuperFact implements Serializable {
    private static final long serialVersionUID = 1501204781800592325L;
    private String name;
    private BigDecimal value;

    public BigDecimalFact() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }
}
