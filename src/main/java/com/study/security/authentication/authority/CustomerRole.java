package com.study.security.authentication.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum CustomerRole implements GrantedAuthority {

  ROLE_USER_NORMAL("일반 회원"),
  ROLE_USER_BRONZE("브론즈 회원"),
  ROLE_USER_SILVER("실버 회원"),
  ROLE_USER_GOLD("골드 회원"),
  ROLE_USER_VIP("VIP 회원"),
  ROLE_USER_BLOCK("정지 회원"),
  ROLE_USER_DORMANT("휴면 회원");

  final String description;

  CustomerRole(String description) {
    this.description = description;
  }
  @Override
  public String getAuthority() {
    return new SimpleGrantedAuthority(name()).getAuthority();
  }
}
