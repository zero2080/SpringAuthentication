package com.study.security.authentication;

import com.study.api.entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
  private final String SECRET = Base64.getEncoder().encodeToString("json-web-token-secret-key-algorithm-bit-same-length".getBytes());
  private final int EXPIRATION_TIME = 60*60*24;

  public String generateToken(Customer customer){


    Claims claims = Jwts.claims().setSubject(customer.getLoginId()); // JWT payload 에 저장되는 정보단위
    claims.put("roles", "ROLE_USER");
    Date now = Date.from(Instant.now());
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime()+EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS256, SECRET)
        .compact();
  }

  public String getUsernameFromToken(String token){
    return null;
  }

  public boolean validateToken(String token){
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
      return claims.getBody().getExpiration().toInstant().isAfter(Instant.now());
    }finally{
      return false;
    }
  }

  public String resolveToken(String token){
    return null;
  }
}
