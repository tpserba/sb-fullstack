package com.serbatic.lucasmoy.tpraga.sbfullstack.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.UUID;

/**
 * @author Mahesh
 */
@Component
public class JWTUtil {
    @Value("${security.jwt.secret}")
    private String key;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.ttlMillis}")
    private long ttlMillis;

    private final Logger log = LoggerFactory
            .getLogger(JWTUtil.class);

    /**
     * Create a new token.
     *
     * @param id
     * @param subject
     * @return
     */
    public String create(String id, String subject) {
        System.out.println("Data to create jwt");
        System.out.println("id: " + id);
        System.out.println("subject : " + subject);
        // The JWT signature algorithm used to sign the token
        Algorithm algorithm = Algorithm.HMAC256("5upm(b2ahS3cUh(n!2-fnfq89fgh32fb39f39h2BAIonaoHAUABIUAB");
        String jwt = JWT.create()
                .withIssuer("Tom")
                .withSubject(subject)
                .withClaim("userId", id)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 50000000L)) // 50000000L 13.8 hours
                .withJWTId(UUID.randomUUID()
                        .toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(algorithm);

        // Builds the JWT and serializes it to a compact, URL-safe string
        return jwt;
    }

    /**
     * Method to validate and read the JWT
     *
     * @param jwt
     * @return
     */
    public String getValue(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt).getBody();

        return claims.getSubject();
    }

    /**
     * Method to validate and read the JWT
     *
     * @param jwt
     * @return
     */
    public String getKey(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        System.out.println("incoming jwt");
        System.out.println(jwt);
        Algorithm algorithm = Algorithm.HMAC256("5upm(b2ahS3cUh(n!2-fnfq89fgh32fb39f39h2BAIonaoHAUABIUAB");
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("Tom").build();
        try {
            DecodedJWT decodedJWT = verifier.verify(jwt);
            Claim claim = decodedJWT.getClaim("userId");
            System.out.println("user id!");
            System.out.println(claim);
            return claim.asString();
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}