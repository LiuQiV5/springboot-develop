package com.uni.common.springboot.util;

import com.uni.common.springboot.core.JwtParseException;
import com.uni.common.springboot.dto.UserSchoolDTO;
import com.uni.common.springboot.model.School;
import com.uni.common.springboot.model.SecurityUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.uni.common.springboot.core.Constants.AUTH_TOKEN_START;
import static com.uni.common.springboot.core.Constants.CLAIM_KEY_AUTHORITIES;
import static com.uni.common.springboot.core.Constants.PREFIX;

@Slf4j
@Component
@Setter
@Getter
@ConfigurationProperties(
        prefix = "token",
        ignoreUnknownFields = false
)
public class JwtUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    public String secret;

    private int expireTime;

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public UserSchoolDTO getUserSchoolFromToken(String token) {
        UserSchoolDTO userSchoolDTO;
        try {
            final Claims claims = getClaimsFromToken(token);
            String key = getKey(claims.getId());
            userSchoolDTO = UserSchoolDTO
                    .builder()
                    .userName(claims.getSubject())
                    .schoolName((String) redisTemplate.opsForValue().get(key))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            userSchoolDTO = null;
        }
        return userSchoolDTO;
    }

    private Boolean isTokenExpired(String token) {
        return getClaimsFromToken(token) == null;
    }

    public void switchSchool(HttpServletRequest request, UserSchoolDTO userSchoolDTO) {
        final String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String authToken = requestHeader.substring(AUTH_TOKEN_START.length());
        redisTemplate.opsForValue().set(getKeyFromToken(authToken), userSchoolDTO.getSchoolName(), expireTime, TimeUnit.HOURS);
    }

    private String getKeyFromToken(String token) {
        String key;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            String jti = claims.getId();
            key = getKey(jti);
            if (!redisTemplate.hasKey(key)) {
                throw JwtParseException.create("error.TokenExpired");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            key = null;
        }
        return key;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            String jti = claims.getId();
            String key = getKey(jti);
            if (redisTemplate.hasKey(key)) {
                redisTemplate.expire(key, expireTime, TimeUnit.HOURS);
            } else {
                throw JwtParseException.create("error.TokenExpired");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            claims = null;
        }
        return claims;
    }

    public Boolean validateToken(String token, final UserDetails userDetails) {
        SecurityUserDetails userDetail = (SecurityUserDetails) userDetails;
        final UserSchoolDTO userSchoolDTO = getUserSchoolFromToken(token);
        return (userSchoolDTO.getUserName().equals(userDetail.getUsername()) && !isTokenExpired(token));
    }

    private Map<String, Object> generateClaims(final SecurityUserDetails userDetail) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_AUTHORITIES, userDetail.getAuthorities());
        return claims;
    }

    public String generateAccessToken(final SecurityUserDetails userDetail, School school) {
        Map<String, Object> claims = generateClaims(userDetail);
        return generateAccessToken(userDetail, school, claims);
    }

    private String generateAccessToken(final SecurityUserDetails userDetail, School school, Map<String, Object> claims) {
        return generateToken(userDetail, school, claims, expireTime);
    }

    private String generateToken(final SecurityUserDetails userDetail, School school, Map<String, Object> claims, int expiration) {
        String jti = String.valueOf(userDetail.getId());
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetail.getUsername())
                .setId(jti)
                .setIssuedAt(new Date())
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
        if (redisTemplate.hasKey(getKey(jti))) {
            redisTemplate.expire(getKey(jti), expireTime, TimeUnit.HOURS);
        } else {
            redisTemplate.opsForValue().set(getKey(jti), school.getName(), expiration , TimeUnit.HOURS);
        }
        return accessToken;
    }

    public static String getKey(String jti){
        return PREFIX + ":" + jti;
    }
}
