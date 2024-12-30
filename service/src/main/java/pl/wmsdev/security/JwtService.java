package pl.wmsdev.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.wmsdev.usos4j.model.auth.UsosAccessToken;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String extractUsername(JwtToken token) {
        return extractClaim(token, Claims::getSubject );
    }

    public <T> T extractClaim(JwtToken token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(String accessToken, String accessTokenSecret, String universityId, UserDetails userDetails) {
        var claims = Map.of(
                "accessToken", accessToken,
                "accessTokenSecret", accessTokenSecret,
                "universityId", universityId);

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 120))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(JwtToken token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(JwtToken token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(JwtToken token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(JwtToken token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token.jwt())
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public UsosAccessToken getAccessToken() {
        JwtToken jwt = getJwt();
        String accessToken = extractClaim(jwt, Claims -> Claims.get("accessToken").toString());
        String accessTokenSecret = extractClaim(jwt, Claims -> Claims.get("accessTokenSecret").toString());

        return new UsosAccessToken(accessToken, accessTokenSecret);
    }

    public String getUsersUniversityId() {
        return extractClaim(getJwt(), Claims -> Claims.get("universityId").toString());
    }

    private JwtToken getJwt() {
        return new JwtToken(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
    }
}
