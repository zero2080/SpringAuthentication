package com.study.security.authentication.model;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Getter
public class CustomerAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public CustomerAuthenticationToken(User detail) {
        super(detail, null, detail.getAuthorities());
    }

    @Override
    public WebAuthenticationDetails getDetails() {
        return (WebAuthenticationDetails) super.getDetails();
    }
}
