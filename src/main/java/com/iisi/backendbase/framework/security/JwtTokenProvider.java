package com.iisi.backendbase.framework.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {
    //有效期为
    public static final Long JWT_TTL = 60 * 60 * 1000L;// 60 * 60 *1000  一个小时
    private String jwtIssuer;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    public JwtTokenProvider(@Value("${app.jwt-secret}") String secret, @Value("${app.jwt-issuer}") String jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
        this.algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).withIssuer(jwtIssuer).build();
    }

    /**
     * 生成token
     *
     * @param subject
     * @return
     */
    public String generateToken(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withIssuer(jwtIssuer)
                .withExpiresAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).plusSeconds(JWT_TTL).toInstant()))
                .sign(algorithm);
    }

    /**
     * 验证token並取出結果
     *
     * @param token
     * @return
     */
    public String getSubject(String token) {
        return verifier.verify(token).getSubject();
    }

}