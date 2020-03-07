package com.task.wizbackend.serviceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

        private static final String SigningKey = "W!z-$ecret";
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            String user;
            try {
                Claims claims = Jwts.parser().setSigningKey(SigningKey).parseClaimsJws(token.replace("Bearer ", "")).getBody();
                user = claims.get("un", String.class);
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, Collections.EMPTY_LIST);
                }
            } catch (ExpiredJwtException expiredException) {
                logger.error("Expired Exception");
            } catch (Exception exception) {
                logger.error("Jwt Token Error", exception);
            }
        }
        return null;
    }
}