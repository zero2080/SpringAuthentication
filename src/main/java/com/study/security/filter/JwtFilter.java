package com.study.security.filter;

import com.study.security.authentication.JwtProvider;
import io.jsonwebtoken.lang.Assert;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
  private final JwtProvider provider;
  protected final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

  /**
   * AbstractAuthenticationProcessingFilter 시도 불가 OncePerRequestFilter로 변경해야 함
   */
  protected JwtFilter(JwtProvider provider) {
    super();
    this.provider = provider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String uriPath= request.getRequestURI();
    if(uriPath.equals("/login/authenticate")){
      filterChain.doFilter(request, response);
      return;
    }

    String token = resolveToken(request);
    provider.authenticate(token).ifPresent(authentication -> {
      authentication.setDetails(authenticationDetailsSource.buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    });

    // 결국 token이 없거나 user data와 sync가 맞지 않으면 skip
    filterChain.doFilter(request, response);
    SecurityContextHolder.clearContext();
  }

  private String resolveToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    Assert.notNull(token);

    if (token.startsWith("Bearer ")) {
      return token.substring(7);
    }
    return token;
  }
}
