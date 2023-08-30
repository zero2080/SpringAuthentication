package com.study.security.authentication.model;

import com.study.api.entity.Customer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomerDetail extends User {

  public CustomerDetail(Customer account) {
    super(account.getId().toString(), account.getPassword(), true, true, true, true, List.of(new SimpleGrantedAuthority("ROLE_USER")));
  }
}
