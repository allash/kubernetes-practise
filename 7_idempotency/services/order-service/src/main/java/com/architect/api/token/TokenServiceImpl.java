package com.architect.api.token;

import com.architect.exceptions.IdempotenceKeyConflictException;
import com.architect.exceptions.IdempotenceKeyMissingException;
import com.architect.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);
    private final RedisService redisService;

    @Autowired
    public TokenServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public String createToken() {
        String token = null;
        try {
            token = UUID.randomUUID().toString();
            redisService.setEx(token, token, 1000L);
            return token;
        } catch (Exception e) {
            LOGGER.error("Exception: " + e.getLocalizedMessage());
        }

        return token;
    }

    @Override
    public boolean checkToken(HttpServletRequest request) {

        String token = request.getHeader("x-request-id");
        if (token == null) {
            throw new IdempotenceKeyMissingException();
        }

        if (!redisService.exists(token)) {
            throw new IdempotenceKeyMissingException();
        }

        if (!redisService.remove(token)) {
           throw new IdempotenceKeyConflictException();
        }

        return true;
    }
}
