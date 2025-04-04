package de.aittr.bio_marketplace.security.filter;

import de.aittr.bio_marketplace.security.service.CustomUserDetailsService;
import de.aittr.bio_marketplace.security.service.JwtTokenService;
import de.aittr.bio_marketplace.service.CookieService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final CookieService cookieService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenService jwtTokenService, CookieService cookieService, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.cookieService = cookieService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //1 Вытащить Access-токен из Cookie (уже есть метод в CookieService)
        String accessToken = cookieService.getAtJwtFromCookies(request);
        if (StringUtils.hasText(accessToken) && jwtTokenService.validateAccessToken(accessToken)) {
            //Парс.ю claims, берем email (subject)
            String email = jwtTokenService.getEmailFromAccessToken(accessToken);
            if (email != null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                //3 Создаю "Authentication"
                //не из базы, но можно и сразу передать email + пустые authorities
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                //principal
                                email,
                                null,
                                //authorities (можно достать роли из токена или из БД)
                                userDetails.getAuthorities()
                        );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //4 Сохраняю в SecurityContext
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        //Пропускаю дальше
        filterChain.doFilter(request, response);
    }
}
