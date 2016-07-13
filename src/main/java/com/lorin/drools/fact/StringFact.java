package com.lorin.drools.fact;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by baidu on 16/7/6.
 */
public class StringFact extends SuperFact implements Serializable {
    private static final long serialVersionUID = 7508848260464542634L;
    private String name;
    private String value;

    public StringFact() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
