package net.ljw.backend.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Signature;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {

    private String secretKey = "abb12be0ssx123@@!1267444adb90";

    @Override
    public String getToken(String key, Object value) {

        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + (1000 * 60 * 30));
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] keyBytes = encoder.encode(secretKey.getBytes());
        Key signKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<>();
        claims.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(claims)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);


        return builder.compact();
    }

    @Override
    public Claims getClaims(String token) {
        if(token != null && !token.isEmpty()){
            try{
                Base64.Encoder encoder = Base64.getEncoder();
                byte[] keyBytes = encoder.encode(secretKey.getBytes());
                Key signKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
                return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException e) {
                // 만료됨
            } catch (JwtException e) {
                // 유효하지 않음
            }
        }
        return null;
    }

    @Override
    public boolean isValid(String token) {
        return this.getClaims(token) != null;
    }

    @Override
    public int getId(String token) {
        Claims claims=  this.getClaims(token);
        if(claims!=null){ return Integer.parseInt(claims.get("id").toString()); }
        return 0;
    }
}
