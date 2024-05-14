package com.blog.carta.security;

import com.blog.carta.response.UnauthorizedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // Set the content type of the response
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Write the custom error message to the response body using UnauthorizedResponse
        UnauthorizedResponse unauthorizedResponse = new UnauthorizedResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: Please provide a valid bearer token");
        String jsonResponse = new ObjectMapper().writeValueAsString(unauthorizedResponse);
        response.getWriter().write(jsonResponse);
    }
}
