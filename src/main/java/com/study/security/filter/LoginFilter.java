package com.study.security.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.security.authentication.AuthenticationHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
  public LoginFilter(AuthenticationHandler handler, AuthenticationProvider provider){
    super();
    setFilterProcessesUrl("/login/authenticate");
    setAuthenticationManager(provider::authenticate);

    setAuthenticationSuccessHandler(handler);
    setAuthenticationFailureHandler(handler);
  }
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    Map<String, String> loginRequest;

    try {
      loginRequest = objectMapper.readValue(request.getInputStream(), new TypeReference<>() {});
    } catch (IOException e) {
      throw new AuthenticationServiceException("Invalid Login Request");
    }

    String loginId = loginRequest.get("login_id");
    String password = loginRequest.get("password");

    if (loginId == null) {
      loginId = "";
    }

    if (password == null) {
      password = "";
    }

    loginId = loginId.trim();

    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginId, password);

    setDetails(request, authRequest);

    return getAuthenticationManager().authenticate(authRequest);
  }
}
