package com.project.project.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtUtils {

    @Value("${jwt.issuer}")
    private String ISSUER ;
    @Value("${jwt.token-type}")
    private String TOKEN_TYPE;

    @Value("${jwt.secret}")

    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationMillis;
   private Key getkey(){
       return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
   }

   public String accesstoken(String role,String email){
       return GenrateToken(role,email,"access",15 * 60 * 1000);
   }

   public String refresh(String role,String email){
       return GenrateToken(role,email,"refresh",7L * 24 * 60 * 60 * 1000);
   }

   public String GenrateToken(String role,String email,String TOKEN_TYPE,long exp){
       Date now = new Date();

       return Jwts.builder()
               .setSubject(email)
               .setIssuer(ISSUER)
               .setIssuedAt(now)
               .setExpiration(new Date(now.getTime() + exp))
               .claim("role",role)
               .claim("typ",TOKEN_TYPE)
               .signWith(getkey(), SignatureAlgorithm.HS256)
               .compact();

   }

   private Claims getclain(String token){
       return Jwts.parserBuilder()
               .setSigningKey(getkey())
               .build()
               .parseClaimsJws(token)
               .getBody();

   }

   public String getRole(String token){
       return getclain(token).get("role",String.class);
   }

   public String getemail(String token){
       return getclain(token).getSubject();
   }

   public Boolean validate(String token){
       try {
           getclain(token);
           return true;
       }catch (JwtException e){
           return false;
       }
   }
    public String extractType(String token) {
        return getclain(token).get("type", String.class);
    }
}
