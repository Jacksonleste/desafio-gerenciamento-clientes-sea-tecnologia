package com.seatecnologia.crudclientes.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

import static io.jsonwebtoken.Jwts.builder;

@Component
public class JwtTokenUtil {
    private static final String SECRET_KEY = "n$hcLzku#HC9tAKZQyp4r@&YM3xdDeRgv*m%!waEbU65GPJs2W";
    private static final Integer jwtExpirationMs = 1000 * 60 * 60;

    private static SecretKey getSigningKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
    }

    public static String generateToken(String username, String papel){
        return builder()
                .subject(username)
                .claim("papel", papel)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public static Claims getClaimsFromToken(CharSequence token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static boolean isTokenValid(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }
}
