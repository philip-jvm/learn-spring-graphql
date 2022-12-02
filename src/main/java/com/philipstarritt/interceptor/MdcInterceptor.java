package com.philipstarritt.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class MdcInterceptor implements WebGraphQlInterceptor {

    public static final String VALUE_PROPAGATED = "VALUE_PROPAGATED";
    public static final String VALUE_NOT_PROPAGATED = "VALUE_NOT_PROPAGATED";

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        log.info("Intercepting request - Putting {} in MDC", VALUE_NOT_PROPAGATED);
        MDC.put(VALUE_NOT_PROPAGATED, UUID.randomUUID().toString());
        return chain.next(request);
    }

}
