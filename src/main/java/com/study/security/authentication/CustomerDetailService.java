package com.study.security.authentication;

import com.study.api.repository.CustomerRepository;
import com.study.security.authentication.model.CustomerDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailService implements UserDetailsService {

  private final CustomerRepository customerRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return customerRepository.findByLoginId(username)
        .map(CustomerDetail::new)
        .orElseThrow(() -> new UsernameNotFoundException("아이디 혹은 비밀번호가 잘못되었습니다."));
  }
}
