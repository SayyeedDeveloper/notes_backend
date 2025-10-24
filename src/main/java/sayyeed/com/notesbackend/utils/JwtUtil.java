package sayyeed.com.notesbackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sayyeed.com.notesbackend.dto.JwtDTO;
import sayyeed.com.notesbackend.enums.UserRoleEnum;
import sayyeed.com.notesbackend.exceptions.AppBadException;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static String secretKey;

    @Value("${app.jwt.secret.key}")
    public void setSecretKey(String value) {
        secretKey = value;
    }

    private static final int tokenLiveTime = 1000 * 3600 * 24 * 7; // week


    public static String encode(String email, List<UserRoleEnum> roles) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", email);
        extraClaims.put("role", roles);

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();

        // Get the role list from claims
        List<String> roleStrings = (List<String>) claims.get("role");

        // Convert string roles to UserRoleEnum values
        List<UserRoleEnum> roles = roleStrings.stream()
                .map(UserRoleEnum::valueOf)
                .collect(Collectors.toList());

        JwtDTO jwtDTO = new JwtDTO();
        jwtDTO.setEmail(username);
        jwtDTO.setRoles(roles);
        return jwtDTO;
    }

    /** Register JWT **/
    public static String encodeForRegister(String email, String code) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", email);
        extraClaims.put("code", code);

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static JwtDTO decodeForRegister(String token) {
        try {
            Claims claims = Jwts
                    .parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String email = (String) claims.get("email");
            String code = (String) claims.get("code");
            return new JwtDTO(email, code);
        } catch (Exception e) {
            throw new AppBadException("Something went wrong");
        }
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

