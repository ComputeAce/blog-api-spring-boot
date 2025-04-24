package blank.blank.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username) {
        System.out.println("\n=== Generating JWT Token ===");
        System.out.println("For username: " + username);
        
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) 
                .signWith(key)
                .compact();
        
        System.out.println("Generated Token: " + token);
        System.out.println("Token Length: " + token.length() + " characters");
        System.out.println("=== Token Generation Complete ===\n");
        return token;
    }

    public String extractUserName(String token) {
        System.out.println("\n=== Extracting Username ===");
        System.out.println("From Token: " + abbreviateToken(token));
        
        String username = extractClaim(token, Claims::getSubject);
        
        System.out.println("Extracted Username: " + username);
        System.out.println("=== Username Extraction Complete ===\n");
        return username;
    }

    public boolean validateToken(String token, String username) {
        System.out.println("\n=== Validating Token ===");
        System.out.println("Token to Validate: " + abbreviateToken(token));
        System.out.println("Expected Username: " + username);
        
        try {
            final String extractedUsername = extractUserName(token);
            boolean isValid = extractedUsername.equals(username) && !isTokenExpired(token);
            
            System.out.println("Validation Result: " + (isValid ? "VALID" : "INVALID"));
            if (!isValid) {
                if (!extractedUsername.equals(username)) {
                    System.out.println("Reason: Username mismatch (Expected: " + username + 
                                      ", Actual: " + extractedUsername + ")");
                }
                if (isTokenExpired(token)) {
                    System.out.println("Reason: Token has expired");
                }
            }
            System.out.println("=== Token Validation Complete ===\n");
            return isValid;
        } catch (JwtException e) {
            System.err.println("Validation Error: " + e.getClass().getSimpleName() + 
                             " - " + e.getMessage());
            System.out.println("=== Token Validation Failed ===\n");
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        System.out.println("\nChecking Token Expiration");
        System.out.println("Token: " + abbreviateToken(token));
        
        Date expiration = extractExpiration(token);
        Date now = new Date();
        boolean expired = expiration.before(now);
        
        System.out.println("Expiration Date: " + expiration);
        System.out.println("Current Date:    " + now);
        System.out.println("Is Expired:      " + expired);
        return expired;
    }

    private Date extractExpiration(String token) {
        System.out.println("\nExtracting Expiration Date");
        System.out.println("From Token: " + abbreviateToken(token));
        
        Date expiration = extractClaim(token, Claims::getExpiration);
        
        System.out.println("Extracted Expiration: " + expiration);
        return expiration;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println("\nExtracting Claim");
        System.out.println("From Token: " + abbreviateToken(token));
        
        final Claims claims = extractAllClaims(token);
        T result = claimsResolver.apply(claims);
        
        System.out.println("Claim Value: " + result);
        return result;
    }

    private Claims extractAllClaims(String token) {
        System.out.println("\nExtracting All Claims");
        System.out.println("From Token: " + abbreviateToken(token));
        
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            System.out.println("Claims Extracted Successfully");
            System.out.println("Claims Content: " + claims);
            return claims;
        } catch (ExpiredJwtException e) {
            System.err.println("Token Expired: " + e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            System.err.println("Malformed Token: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            throw e;
        }
    }

    private String abbreviateToken(String token) {
        if (token == null) return "null";
        if (token.length() <= 20) return token;
        return token.substring(0, 10) + "..." + token.substring(token.length() - 10);
    }
}