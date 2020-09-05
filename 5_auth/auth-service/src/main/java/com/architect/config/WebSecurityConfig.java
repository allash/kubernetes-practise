package com.architect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenAuthenticationProvider tokenAuthenticationProvider;
    private final UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final AuthFilter authFilter;
    private final InternalApiAuthFilter internalApiAuthFilter;

    @Autowired
    public WebSecurityConfig(TokenAuthenticationProvider tokenAuthenticationProvider,
                             UnauthorizedEntryPoint unauthorizedEntryPoint,
                             AuthFilter authFilter,
                             InternalApiAuthFilter internalApiAuthFilter) {
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
        this.authFilter = authFilter;
        this.internalApiAuthFilter = internalApiAuthFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(authFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(internalApiAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint);

        http.authorizeRequests()
                .antMatchers("/register", "/login", "/health", "/", "/signin")
                .permitAll();

        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/**")
                .fullyAuthenticated();

        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(tokenAuthenticationProvider);
    }
}
