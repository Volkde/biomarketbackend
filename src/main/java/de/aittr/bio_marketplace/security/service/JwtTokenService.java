package de.aittr.bio_marketplace.security.service;

import de.aittr.bio_marketplace.security.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

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
                .subject(email)
                .expiration(expirationDate)
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(secret)
                .compact();
    }
}
