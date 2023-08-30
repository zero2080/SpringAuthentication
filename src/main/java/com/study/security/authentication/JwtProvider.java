package com.study.security.authentication;

import com.study.api.entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
  private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

  public String generateToken(Customer customer) {

    Claims claims = Jwts.claims().setSubject(String.valueOf(customer.getId())); // JWT payload 에 저장되는 정보단위
    claims.put("roles", "ROLE_USER");
    Date now = Date.from(Instant.now());
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + (60 * 60 * 24)))
        .signWith(SECRET_KEY)
        .compact();
  }

  public String getUsernameFromToken(String token) {
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
      return claims.getBody().getExpiration().toInstant().isAfter(Instant.now());
    } finally {
      return false;
    }
  }

  public String resolveToken(String token) {
    return null;
  }
}
