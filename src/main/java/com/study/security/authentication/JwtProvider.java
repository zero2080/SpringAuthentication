package com.study.security.authentication;

import com.study.api.entity.Customer;
import com.study.api.repository.CustomerRepository;
import com.study.security.authentication.model.CustomerAuthenticationToken;
import com.study.security.authentication.model.CustomerDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtProvider {
  private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  private static final JwtParser TOKEN_PARSER = Jwts.parser().setSigningKey(SECRET_KEY);

  private final CustomerRepository repository;

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

  public Optional<CustomerAuthenticationToken> authenticate(String token) {
    Jws<Claims> claims = parseToken(token);
    long id = Long.valueOf(claims.getBody().getSubject());

    return repository.findById(id)
        .map(CustomerDetail::new)
        .map(CustomerAuthenticationToken::new);
  }

  private Jws<Claims> parseToken(String token) {
    Jws<Claims> claims = TOKEN_PARSER.parseClaimsJws(token);
    Instant expirationDate = claims.getBody().getExpiration().toInstant();
    if (expirationDate.isAfter(Instant.now())) {
      return claims;
    }
    return null;
  }
}
