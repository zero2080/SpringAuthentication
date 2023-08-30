package com.study.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.api.entity.Customer;
import com.study.security.authentication.model.CustomerDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

  private final ObjectMapper objectMapper;
  private final JwtProvider tokenProvider;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());

    Customer principal = ((CustomerDetail)authentication.getPrincipal()).getAccount();

    Writer writer = response.getWriter();
//    writer.write(objectMapper.writeValueAsString(Map.of("message", "와우")));

    Map<String,String> body = Map.of("accessToken", tokenProvider.generateToken(principal));
    writer.write(objectMapper.writeValueAsString(body));
    writer.flush();
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException{
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    Writer writer = response.getWriter();
    writer.write(objectMapper.writeValueAsString(Map.of("message", "ㅠㅠ")));
    writer.flush();
  }
}
