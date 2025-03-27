package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.security.config.JwtProperties;
import de.aittr.bio_marketplace.security.service.JwtTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

@Service
public class CookieService {

    private final JwtProperties configuration;
    private final JwtTokenService jwtTokenService;

    public CookieService(JwtProperties configuration, JwtTokenService jwtTokenService) {
        this.configuration = configuration;
        this.jwtTokenService = jwtTokenService;
    }

    public ResponseCookie generateAccessTokenCookie(String email) {
        final String jwt = jwtTokenService.generateAccessToken(email);
        return generateCookie(configuration.getAtCookieName(), jwt, configuration.getAtExpirationInMs());
    }

    public ResponseCookie generateRefreshTokenCookie(String email) {
        final String refreshToken = jwtTokenService.generateRefreshToken(email);
        return generateCookie(configuration.getRtCookieName(), refreshToken, configuration.getRtExpirationInMs());
    }

    public ResponseCookie getCleanAtJwtCookie() {
        return ResponseCookie.from(configuration.getAtCookieName(), "").path("/")
                .maxAge(0)
                .httpOnly(true)
                .build();
    }

    public ResponseCookie getCleanRtJwtCookie() {
        return ResponseCookie.from(configuration.getRtCookieName(), "").path("/")
                .maxAge(0)
                .httpOnly(true)
                .build();
    }

    private ResponseCookie generateCookie(String name, String value, int maxAge) {
        return ResponseCookie
                .from(name, value)
                .path("/")
                .maxAge(maxAge)
                .secure(true)
                .httpOnly(true)
                .build();
    }

    public String getAtJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, configuration.getAtCookieName());
    }

    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, configuration.getRtCookieName());
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }
}
