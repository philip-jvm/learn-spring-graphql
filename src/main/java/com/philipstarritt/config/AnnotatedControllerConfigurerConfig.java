package com.philipstarritt.config;

import com.philipstarritt.context.MdcPropagator;
import io.micrometer.context.ContextRegistry;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.data.method.annotation.support.AnnotatedControllerConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AnnotatedControllerConfigurerConfig {

    @Bean
    public AnnotatedControllerConfigurer annotatedControllerConfigurer(ListableBeanFactory beanFactory) {
        AnnotatedControllerConfigurer controllerConfigurer = new AnnotatedControllerConfigurer();
        controllerConfigurer.addFormatterRegistrar((registry) -> ApplicationConversionService.addBeans(registry, beanFactory));
        // The executor that will execute all @BatchMapping callbacks
        controllerConfigurer.setExecutor(createBatchMappingExecutor());

        // Register with global context registry.
        ContextRegistry.getInstance().registerThreadLocalAccessor(new MdcPropagator());

        return controllerConfigurer;
    }

    private static ThreadPoolTaskExecutor createBatchMappingExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setPrestartAllCoreThreads(true);
        executor.initialize();
        return executor;
    }

}
