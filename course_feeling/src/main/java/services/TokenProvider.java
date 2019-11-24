package services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.servlet.ServletException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class TokenProvider {

    private byte[] secretKey = Base64.getDecoder()
            .decode("Wtep4j0WCw5tUsc/Y1OolKNFRowt59JlCmkQHIYKZI0=");

    // openssl rand -base64 32 => "S0HH5sd2qttYXC6Q2S2THvvOC55tSwXAyDLj8MGhDs0="

    /**
     * crée le token d'authentification.
     * 
     * @param email .
     * @return quelque chose.
     * @throws SignatureException .
     */
    public String createToken(String email) {
        Instant now = Instant.now();
        try {
            return Jwts.builder().setIssuer("/signin")
                    .signWith(Keys.hmacShaKeyFor(secretKey))
                    .claim("email", email).setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plus(720, ChronoUnit.HOURS)))
                    .signWith(Keys.hmacShaKeyFor(secretKey)).compact();
        } catch (SignatureException e) {
            e.getStackTrace();
            return null;
        }
    }

    /**
     * verifie si le token à expiré ou pas.
     * 
     * @param token .
     * @return .
     */

    public boolean expiredToken(String token) {
        Jws<Claims> jws = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token);
        Instant now = Instant.now();
        long expireTime = jws.getBody().getExpiration().getTime();
        return now.getEpochSecond() <= expireTime;
    }

    /**
     * return les claims d'un token.
     * 
     * @param token .
     * @return claims.
     * @throws ServletException Exception.
     */
    public Jws<Claims> validateJwtToken(String token) throws ServletException {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (final SignatureException e) {
            throw new ServletException("Invalid token.");
        }
    }
}
