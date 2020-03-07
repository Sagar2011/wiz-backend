package com.task.wizbackend.util;

import com.task.wizbackend.model.Student;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class JwtUtil {
    static final long EXPIRATIONTIME = 3600000; // 1 hour in milliseconds
    private static final String PREFIX = "Bearer";
    static String SIGNINGKEY;

    @Value("${SIGNING_KEY}")
    public void setSigningkey(String signingkey) {
        SIGNINGKEY = signingkey;
    }


    public static String addToken(Student student) {

        Claims claims = Jwts.claims();
        claims.put("un", student.getUsername()); // username
        claims.put("em", student.getEmail()); //email
        String jwtToken = Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SIGNINGKEY).compact();
        return jwtToken;
    }

}