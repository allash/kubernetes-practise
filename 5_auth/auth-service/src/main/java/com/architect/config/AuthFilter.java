package com.architect.config;

import com.architect.persistence.SessionRepository;
import com.architect.persistence.entities.DbSession;
import com.architect.persistence.entities.DbUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class.getName());
    private final SessionRepository sessionRepository;
    private final AnonymousTokenGenerator anonymousTokenGenerator;

    @Autowired
    public AuthFilter(SessionRepository sessionRepository,
                      AnonymousTokenGenerator anonymousTokenGenerator) {
        this.sessionRepository = sessionRepository;
        this.anonymousTokenGenerator = anonymousTokenGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("x-auth-token");

        Optional<DbSession> session = sessionRepository.findByToken(token);

        if (session.isEmpty()) {
            LOGGER.info("No session found, creating anonymous token");
            SecurityContextHolder.getContext().setAuthentication(anonymousTokenGenerator.generate());
            filterChain.doFilter(request, response);
            return;
        }

        DbUser user = session.get().getUser();
        SessionInfo sessionInfo = SessionInfo.builder()
                .token(token)
                .email(user.getEmail())
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        Authentication authentication = new TokenAuthentication(token, sessionInfo, true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LOGGER.info("Authentication successful");
        filterChain.doFilter(request, response);
    }
}
