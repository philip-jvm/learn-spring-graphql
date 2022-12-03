package com.philipstarritt.context;

import io.micrometer.context.ThreadLocalAccessor;
import org.slf4j.MDC;

import java.util.Map;

public class MdcPropagator implements ThreadLocalAccessor<Map<String, String>> {

    @Override
    public Object key() {
        return "MDC";
    }

    @Override
    public Map<String, String> getValue() {
        return MDC.getCopyOfContextMap();
    }

    @Override
    public void setValue(Map<String, String> value) {
        MDC.setContextMap(value);
    }

    @Override
    public void reset() {
        MDC.clear();
    }

}
