package com.architect.api.token;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    String createToken();
    boolean checkToken(HttpServletRequest request) throws Exception;
}
