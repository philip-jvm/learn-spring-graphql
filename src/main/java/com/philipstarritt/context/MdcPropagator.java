package com.philipstarritt.context;

import io.micrometer.context.ThreadLocalAccessor;
import org.slf4j.MDC;

import java.util.Map;

/**
 * Previously at the time of switching thread, org.springframework.graphql.execution.ThreadLocalAccessor propagated all thread local values.
 * Now at the time of switching thread, io.micrometer.context.ThreadLocalAccessor propagates a subset of thread local values. It does not propagate thread locals that were set in the interceptors or @*Mapping.
 * Snapshot is taken before the interceptors execute? WebGraphQlHandler#handleRequest?
 * MdcPropagator is registered in AnnotatedControllerConfigurerConfig.
 */
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
