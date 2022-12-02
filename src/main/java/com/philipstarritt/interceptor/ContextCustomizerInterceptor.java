package com.philipstarritt.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class ContextCustomizerInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        log.info("Intercepting request - Retrieving integration token");

        request.configureExecutionInput((executionInput, builder) ->
                builder.graphQLContext(
                                contextBuilder -> contextBuilder.of("integrationToken", UUID.randomUUID()).build())
                        .build());

        return chain.next(request);
    }
}
