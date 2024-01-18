package com.vizen.util;


import com.vizen.config.VizenSecrets;
import com.vizen.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import static com.vizen.util.Constants.AUTHORITIES_KEY;
import java.security.Key;

@Component
public class JwtTokenUtil implements Serializable {

   private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

   	   
    @Value("${app.jwt.ttl.minutes}")
    private long tokenTimeToLiveInMinutes;

    @Autowired
    private VizenSecrets vizenSecrets;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(vizenSecrets.getJwtSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String createTokenFromAuth(Authentication authentication) {
        return generateToken(authentication.getName());
    }

    public String createTokenFromUser(User user) {
        return generateToken(user.getEmail());
    }

    private String generateToken(String username) {
        long currentTimestampInMillis = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, "")
                .signWith(SignatureAlgorithm.HS256, vizenSecrets.getJwtSecretKey())
                .setIssuedAt(new Date(currentTimestampInMillis))
                .setExpiration(new Date(currentTimestampInMillis + (tokenTimeToLiveInMinutes * 60 * 1000)))
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(vizenSecrets.getJwtSecretKey()));
      }
    
    public boolean validateJwtToken(String authToken) {
        try {
          Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
          return true;
        } catch (MalformedJwtException e) {
          logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
          logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
          logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
          logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
      }
}
