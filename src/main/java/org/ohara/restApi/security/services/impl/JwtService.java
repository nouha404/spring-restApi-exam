package org.ohara.restApi.security.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities){
        Map<String, Object> clains=new HashMap<String, Object>();
        clains.put("roles",authorities);
        return createToken(username, clains);
    }
    private String createToken(String username, Map<String, Object> clains){
        return Jwts.builder()
                .setClaims(clains)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+( 3*3600*1000)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//5minutes 3*3600*1000
    public String createToken(String username){
           return Jwts.builder()
                   .setClaims(new HashMap<String, Object>())
                   .setSubject(username)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis()+( 3*3600*1000)))
                   .signWith(getSignKey(), SignatureAlgorithm.HS256)
                   .compact();
    }
    private Key  getSignKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }
    private Claims extractAllClaim(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String  extractUsername(String token){
       return extractAllClaim(token).getSubject();
    }
    public Date extractExpiration(String token) {
        return extractAllClaim(token).getExpiration();
    }

    public Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        }
        catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
