package com.example.Clinic_API.security;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorResponse errorDTO = new ErrorResponse(new Date(), "Không có quyền truy cập", authException.getMessage());
        errorDTO.setResponseStatus(ResponseCode.ERROR.name());
        errorDTO.setResponseCode(ResponseCode.ERROR.getCode());
        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        String json = objectMapper.writeValueAsString(responseEntity.getBody());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
