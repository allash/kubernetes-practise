package com.architect.config;

import com.architect.annotations.IdempotenceKey;
import com.architect.exceptions.IdempotenceKeyConflictException;
import com.architect.api.token.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class IdempotentInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    public IdempotentInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        IdempotenceKey methodAnnotation = method.getAnnotation(IdempotenceKey.class);
        if (methodAnnotation != null) {
            try {
                return tokenService.checkToken(request);
            } catch (Exception e) {
                throw new IdempotenceKeyConflictException();
            }
        }

        return true;
    }
}
