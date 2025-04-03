package de.aittr.bio_marketplace.controller.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import java.util.concurrent.TimeUnit;

public class AuthUtils {

    public static final String COOKIE_NAME_AT = "Access-Token";
    public static final String COOKIE_NAME_RT = "Refresh-Token";

    public static void setCookie(HttpServletResponse response, String name, String value, long maxAgeInMillis) {
        long maxAgeInSeconds = TimeUnit.MILLISECONDS.toSeconds(maxAgeInMillis);
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .maxAge(maxAgeInSeconds)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public static void removeCookie(HttpServletResponse response) {
        ResponseCookie cookieAccess = ResponseCookie.from(COOKIE_NAME_AT, "")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookieAccess.toString());

        ResponseCookie cookieRefresh = ResponseCookie.from(COOKIE_NAME_RT, "")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookieRefresh.toString());
    }
}
