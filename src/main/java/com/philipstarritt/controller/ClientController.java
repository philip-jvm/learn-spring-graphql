package com.philipstarritt.controller;

import com.philipstarritt.domain.Client;
import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

@Slf4j
@Controller
@Validated
public class ClientController {

    @QueryMapping
    public Client client(GraphQLContext context) {
        log.info("@QueryMapping(client)");

        MDC.put("mdcKey", "mdcValue");
        context.put("contextKey", "contextValue");

        return Client.builder()
                .id("id")
                .name("name")
                .build();
    }

    @BatchMapping
    public Callable<Map<Client, UUID>> advisorId(List<Client> clients,
                                                 /**
                                                  * parameter.getParameterName() returns null within ContextValueMethodArgumentResolver#getContextValueName
                                                  * when used with a @BatchMapping. This is not consistent with @QueryMapping.
                                                  */
                                                 // @ContextValue UUID integrationToken,
                                                 GraphQLContext context) {
        return () -> {
            // MDC is not propagated. MDC is not available in context.
            log.info("@BatchMapping(Client#advisorId). MDC {}", MDC.getCopyOfContextMap());
            log.info("@BatchMapping(Client#advisorId). Context {}", context);
            return clients.stream()
                    .collect(toUnmodifiableMap(
                            identity(),
                            ignore -> UUID.randomUUID()));
        };
    }

}
