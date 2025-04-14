package de.aittr.bio_marketplace.security.service;

import de.aittr.bio_marketplace.security.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenService {

    private final JwtProperties configuration;
    private final SecretKey accessTokenKey;
    private final SecretKey refreshTokenKey;

    public JwtTokenService(JwtProperties configuration) {
        this.configuration = configuration;
        this.accessTokenKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(configuration.getJwtAtSecret()));
        this.refreshTokenKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(configuration.getJwtRtSecret()));
    }

    public String generateAccessToken(String email) {
        Date expirationDate = new Date(System.currentTimeMillis() + configuration.getAtExpirationInMs());
        return generateJWTToken(email, expirationDate, accessTokenKey);
    }

    public String generateRefreshToken(String email) {
        Date expirationDate = new Date(System.currentTimeMillis() + configuration.getRtExpirationInMs());
        return generateJWTToken(email, expirationDate, refreshTokenKey);
    }

    private String generateJWTToken(String email, Date expirationDate, SecretKey secret) {
        return Jwts.builder()
                //ПРАВИЛЬНО для 0.11.5
                .setSubject(email)
                .setExpiration(expirationDate)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(secret)
                .compact();
    }

        public Claims parseAccessToken(String token) {
        try {
            return Jwts.parserBuilder()
                    //SecretKey для ACCESS
                    .setSigningKey(this.accessTokenKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            //token недействителен
            return null;
        }
    }

    public Claims parseRefreshToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(this.refreshTokenKey) // SecretKey для REFRESH
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            return null;
        }
    }

    //метод для проверки валидности
    public boolean validateAccessToken(String token) {
        return parseAccessToken(token) != null;
    }
    public boolean validateRefreshToken(String token) {
        return parseRefreshToken(token) != null;
    }

    //можно также забрать email/subject
    public String getEmailFromAccessToken(String token) {
        Claims claims = parseAccessToken(token);
        return (claims == null) ? null : claims.getSubject();
    }
    public String getEmailFromRefreshToken(String token) {
        Claims claims = parseRefreshToken(token);
        return (claims == null) ? null : claims.getSubject();
    }

    public String generatePasswordResetToken(String email) {
        Date expirationDate = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 10 минут
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .setIssuedAt(new Date())
                .claim("type", "password-reset")
                .signWith(accessTokenKey)
                .compact();
    }

    public boolean isValidPasswordResetToken(String token) {
        Claims claims = parseAccessToken(token);
        return claims != null && "password-reset".equals(claims.get("type"));
    }

}
