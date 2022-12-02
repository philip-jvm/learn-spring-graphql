package com.philipstarritt.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

import static com.philipstarritt.interceptor.MdcInterceptor.VALUE_PROPAGATED;

@Slf4j
@Component
public class MdcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        log.info("Filtering request - Putting {} in MDC", VALUE_PROPAGATED);
        MDC.put(VALUE_PROPAGATED, UUID.randomUUID().toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
