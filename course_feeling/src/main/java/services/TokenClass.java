package services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class TokenClass {

    private byte  [] secret = Base64.getDecoder()
            .decode("Wtep4j0WCw5tUsc/Y1OolKNFRowt59JlCmkQHIYKZI0=");

    // openssl rand -base64 32  => "S0HH5sd2qttYXC6Q2S2THvvOC55tSwXAyDLj8MGhDs0="

    /**
     * crée le token d'authentification.
     * @param email .
     * @return quelque chose.
     * @throws SignatureException .
     */
    public String createToken(String email) throws SignatureException {
        Instant now = Instant.now();

        try {
            return Jwts.builder()
                .setIssuer("/login")
                .signWith(Keys.hmacShaKeyFor(secret))
                .claim("email", email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                //.setExpiration(new Date(System.currentTimeMillis()))
                .signWith(Keys.hmacShaKeyFor(secret))
                .compact();
        } catch (SignatureException e) {
            e.getStackTrace();
            return "";
        }
    }

    /**
     * verifie la validité du token.
     * @param token .
     * @return .
     */

    public boolean isValidToken(String token) {
        Jws<Claims> jws =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        Instant now = Instant.now();
        long expireTime = jws.getBody().getExpiration().getTime();
        return now.getEpochSecond() <= expireTime;
    }

    public Jws<Claims> validateJwtToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
    }

}
