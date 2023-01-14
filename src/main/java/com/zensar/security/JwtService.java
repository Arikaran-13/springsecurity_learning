package com.zensar.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtService {


    public Claims extractAllClaims(String token){

        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
public String extractUsername(String token){

        return extractClaim(token , Claims::getSubject);
}
    private final static String  SECRET_KEY="2D4B6150645267556B58703273357638792F423F4528482B4D6251655468566D";
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
    public <T> T extractClaim(String token , Function<Claims,T> claimResolver){

        final Claims claim = extractAllClaims(token);

        return claimResolver.apply(claim);
    }

    public String generateToken(Map<String,Object> extractClaim , UserDetails details){

        return Jwts.builder()
                .setClaims(extractClaim)
                .setSubject(details.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*60*24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS512)
                .compact();

    }
    public String generateToken(UserDetails details){
        return generateToken(new HashMap<>(),details);
    }
    public boolean isTokenValid(String token, UserDetails details){
        final String username = extractUsername(token);

      boolean res =   username.equals(details.getUsername()) && isTokenExpired(token);
      return res;

    }
    public boolean isTokenExpired(String token){

      return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token,Claims::getExpiration);
    }

}
