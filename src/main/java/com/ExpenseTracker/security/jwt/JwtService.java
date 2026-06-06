package com.ExpenseTracker.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	//for learning only
	//later we will move it to application properties
	private static final String SECRET_KEY = "my-super-secret-key-my-super-secret-key";
	
	private SecretKey getSignInKey() {
		//convert the secret string into a SecretKey used for signing/verifying JWTs
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); 
	}
	
	//Generate JWT for authentication
	public String generateToken(UserDetails userDetails) {
		
		return Jwts.builder()//configure how to build the JWT
				   .subject(userDetails.getUsername()) //stores sub - "username"
				   .issuedAt(new Date()) // stores iat - "time JWT was issued at"/now
				   .expiration(new Date(System.currentTimeMillis() + 1000 * 60 *60)) //1hr later
				   .signWith(getSignInKey()) //creates signature using the key
				   .compact();//returns final JWT String
	}
	
	//claims is extracting signed data from JWT string
	private Claims extractAllClaims(String token) {
		
		return Jwts.parser() //configure how to read the JWT token
			       .verifyWith(getSignInKey()) //verifying if the signature is valid
			       .build() //after verifying, create the parser object
			       .parseSignedClaims(token) //extract the verified claims(header, payload, signature)
			       .getPayload();//get data from the payload of JWT
	}
	
	//extract username from the JWT via claims
	public String extractUsername(String token) {
		
		return extractAllClaims(token).getSubject();
	}
	
	//extract expiration data from the JWT via claims
	private Date extractExpiration(String token) {
		
		return extractAllClaims(token).getExpiration();
	}
	
	//check if JWT expired 
	private boolean isTokenExpired(String token) {
		
		//checks if Expiration date < Current date(if true - expired, still valid)
		return extractExpiration(token).before(new Date());
	}
	
	//validate the JWT 
	public boolean validateToken(String token, UserDetails userDetails) {
		
		//get username from the token
		String username = extractUsername(token);
		
		//compare the username from the token with the username in userdetails and checks if the token is expired
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
}
