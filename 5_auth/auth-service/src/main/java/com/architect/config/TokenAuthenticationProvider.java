package com.architect.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final Set<Class<? extends Authentication>> supportedTokens
            = new HashSet<>(Collections.singletonList(TokenAuthentication.class));

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof TokenAuthentication)) {
            throw new IllegalStateException("Unsupported authentication object: " + authentication);
        }

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return supportedTokens.contains(authentication);
    }
}
