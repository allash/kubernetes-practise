package com.architect.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class InternalApiAuthFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalApiAuthFilter.class.getName());
    private final InternalServiceConfig internalServiceConfig;

    @Autowired
    public InternalApiAuthFilter(InternalServiceConfig internalServiceConfig) {
        this.internalServiceConfig = internalServiceConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        LOGGER.info("Do internal api filter...");
        String token = request.getHeader("x-service-token");
        LOGGER.info("Current internal token: {}", token);

        TokenAuthentication authentication = internalServiceConfig.getUserService().equals(token)
                ? new TokenAuthentication(token, null, true)
                : null;
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !isInternalService(request);
    }

    private boolean isInternalService(HttpServletRequest request) {
        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/internal/**", null, true);
        List<AntPathRequestMatcher> matchers = Collections.singletonList(matcher);
        return matchers.stream().anyMatch(it -> it.matches(request));
    }
}
