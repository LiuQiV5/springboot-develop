package com.uni.common.springboot.config;


import com.uni.common.springboot.core.Context;
import com.uni.common.springboot.dto.UserSchoolDTO;
import com.uni.common.springboot.model.SecurityUserDetails;
import com.uni.common.springboot.service.JwtUserDetailsService;
import com.uni.common.springboot.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.uni.common.springboot.core.Constants.AUTH_TOKEN_START;


@Slf4j
public class JwtAuthenticationTokenFilter  extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;

    private final JwtUtils jwtUtils;

    public JwtAuthenticationTokenFilter(JwtUserDetailsService jwtUserDetailsService, JwtUtils jwtUtils) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String authToken;
        if (requestHeader != null && requestHeader.startsWith(AUTH_TOKEN_START)) {
            authToken = requestHeader.substring(AUTH_TOKEN_START.length());

            UserSchoolDTO userSchoolDTO = jwtUtils.getUserSchoolFromToken(authToken);

            if (Objects.nonNull(userSchoolDTO) && SecurityContextHolder.getContext().getAuthentication() == null) {

                Context.renewContext("", "", "");
                SecurityUserDetails userDetails = (SecurityUserDetails)this.jwtUserDetailsService.loadUserByUsername("");

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}