package com.study.security.authentication;

import com.study.security.authentication.model.CustomerAuthenticationToken;
import com.study.security.authentication.model.CustomerDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginRequestAuthenticationProvider implements AuthenticationProvider {

  private final CustomerDetailService service;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    CustomerDetail customer =  (CustomerDetail)service.loadUserByUsername(authentication.getName());

    if(!passwordEncoder.matches((String)authentication.getCredentials(), customer.getPassword())) {
      throw new UsernameNotFoundException("아이디 혹은 비밀번호가 잘못되었습니다.");
    }

    CustomerAuthenticationToken token = new CustomerAuthenticationToken(customer);
    token.setDetails(authentication.getDetails());

    return token;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return CustomerAuthenticationToken.class.isAssignableFrom(authentication);
  }
}