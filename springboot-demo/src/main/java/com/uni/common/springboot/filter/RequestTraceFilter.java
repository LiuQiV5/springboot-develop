package com.uni.common.springboot.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


@Component
public class RequestTraceFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger (RequestTraceFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String requestId = httpServletRequest.getHeader (Params.REQUEST_ID);
        if (requestId == null) {
            requestId = UUID.randomUUID ().toString ();
        }
        MDC.put (Params.REQUEST_ID, requestId);
        //userId
        String uri = httpServletRequest.getRequestURI ();
        log.info ("requestUri={}", uri);
        try {
            filterChain.doFilter (httpServletRequest, httpServletResponse);
        } finally {
            MDC.clear ();
        }
    }

}