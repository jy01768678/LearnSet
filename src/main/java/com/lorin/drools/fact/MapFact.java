package com.lorin.drools.fact;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by baidu on 16/7/7.
 */
@SuppressWarnings("serial")
public class MapFact extends SuperFact implements Serializable {

    private String name;
    private Map<String,Object> value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String,Object> getValue() {
        return value;
    }

    public void setValue(Map<String,Object> value) {
        this.value = value;
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this);
    }

}
