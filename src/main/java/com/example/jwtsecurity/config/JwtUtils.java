package com.example.jwtsecurity.config;

import com.example.jwtsecurity.domain.vo.UserDetail;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Joetao
 * @time 2021/1/25 2:58 下午
 * @Email cutesimba@163.com
 */
@Component
public class JwtUtils {
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String CLAIM_KEY_USER_NAME = "username";
    private static final String CLAIM_KEY_NAME = "name";
    private static final String CLAIM_KEY_AUTHORITIES = "authorities";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access_token_expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh_token_expiration}")
    private Long refreshTokenExpiration;

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public UserDetail getUserDetailFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        String username = claims.getSubject();
        String userId = claims.get(CLAIM_KEY_USER_ID).toString();
        String authorities = claims.get(CLAIM_KEY_AUTHORITIES).toString();
        String name = claims.get(CLAIM_KEY_NAME).toString();
        Set<GrantedAuthority> grantedAuthorities = Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return UserDetail.builder().id(Long.parseLong(userId)).username(username).name(name).authorities(grantedAuthorities).build();
    }

    public String generateAccessToken(UserDetail userDetail) {
        return generateToken(userDetail, accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetail userDetail) {
        return generateToken(userDetail, refreshTokenExpiration);
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public Boolean isRefreshTokenExpired(String refreshToken) {
        Date expiration;
        try {
            expiration = Jwts.parser().setSigningKey(secret).parseClaimsJws(refreshToken).getBody().getExpiration();
        } catch (ExpiredJwtException e) {
            return true;
        }
        return expiration.before(new Date());
    }

    public String refreshToken(String refreshToken) {
        if (isRefreshTokenExpired(refreshToken)) {
            return "";
        }
        String token;
        synchronized (this) {
            final UserDetail userDetail = getUserDetailFromToken(refreshToken);
            if (userDetail == null) {
                return null;
            }
            token = generateAccessToken(userDetail);
        }
        return token;
    }

    private Map<String, Object> generateClaims(UserDetail userDetail) {
        Map<String, Object> claims = new HashMap<>(8);
        claims.put(CLAIM_KEY_USER_ID, String.valueOf(userDetail.getId()));
        claims.put(CLAIM_KEY_NAME, String.valueOf(userDetail.getName()));
        claims.put(CLAIM_KEY_USER_NAME, String.valueOf(userDetail.getUsername()));
        claims.put(CLAIM_KEY_USER_ID, String.valueOf(userDetail.getId()));
        claims.put(CLAIM_KEY_AUTHORITIES, userDetail.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));
        return claims;
    }

    private String generateToken(UserDetail userDetail, long expiration) {
        Map<String, Object> claims = generateClaims(userDetail);
        String subject = userDetail.getUsername();
        String userId = String.valueOf(userDetail.getId());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(userId)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(expiration))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }
}
