package com.study.api.entity;

import com.study.security.authentication.authority.CustomerRole;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {
  private Long id;
  private String loginId;
  private String name;
  private CustomerRole role = CustomerRole.ROLE_USER_NORMAL;
  private String password;
  private ZonedDateTime joinedAt = ZonedDateTime.now();

  public Customer(long id, String loginId, String password, String name) {
    this.id = id;
    this.loginId = loginId;
    this.password = password;
    this.name = name;
  }

  public static CustomerBuilder builder() {
    return new CustomerBuilder();
  }

  public static class CustomerBuilder {
    private Long id;
    private String loginId;
    private String name;
    private String password;

    public CustomerBuilder loginId(String loginId) {
      this.loginId = loginId;
      return this;
    }

    public CustomerBuilder id(long id) {
      this.id = id;
      return this;
    }

    public CustomerBuilder name(String name) {
      this.name = name;
      return this;
    }

    public CustomerBuilder password(String password) {
      this.password = password;
      return this;
    }

    public Customer build() {
      return new Customer(id, loginId, password, name);
    }
  }
}
