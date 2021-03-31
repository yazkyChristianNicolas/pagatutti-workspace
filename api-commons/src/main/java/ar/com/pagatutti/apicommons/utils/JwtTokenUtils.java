package  ar.com.pagatutti.apicommons.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtils{
	
	public static final String AUTHENTICATED_USER = "authenticated_user";
		
	@Value("${jwt.expirations.days:}")
	private long jwt_token_validity_days;
	
	@Value("${jwt.secret:}")
	private String secret;
	
	//retrieve username from jwt token
	public String getUserNameFromToken(String token) {
		return  getClaimFromToken(token, Claims::getSubject);
	}
	
	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
    //for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	//check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	
	//generate token for user
	public String generateToken(UserDetails subjectDetail) {
		return generateToken(subjectDetail.getUsername());
	}	
	
	public String generateToken(String subject) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, subject);
	}
	
	
	//while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string 
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(jwt_token_validity_days, TimeUnit.DAYS)))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	//validate token
	public Boolean validateToken(String token, String subject) {
		final String userName = getUserNameFromToken(token);
		return (userName.equalsIgnoreCase(subject) && !isTokenExpired(token));
	}
}
