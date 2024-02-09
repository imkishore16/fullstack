package com.example.demo.utils;

// import java.security.Key;
// import java.util.function.Function;

// import java.util.*;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Service;


// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;

// @Service
// public class JwtUtil {

//     @Value("${application.jwt.secret_key}")
//     private String secret_key;

//     public String extractUsername(String token) {
//         return extractCliam(token,Claims::getSubject);
//     }

//     private <T> T extractCliam(String token, Function<Claims,T> claimsResolver){
//         final Claims claims=extractAllCliams(token);
//         return claimsResolver.apply(claims);
//     }

//     private Claims extractAllCliams(String token){
//         return Jwts.parserBuilder()
//         .setSigningKey(getSigningKey())
//         .build()
//         .parseClaimsJws(token)
//         .getBody();
        
//     }

//     private Key getSigningKey(){
//         byte[] keyBytes=Decoders.BASE64.decode(secret_key);
//         return Keys.hmacShaKeyFor(keyBytes);
//     }

//     public boolean isTokenValid(String token, UserDetails userDetails) {
//         final String username=extractUsername(token);
//         return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        
//     }

//     private boolean isTokenExpired(String token) {
//         return extractExpiration(token).before(new Date());
        
//     }

//     private Date extractExpiration(String token) {
//         return extractCliam(token, Claims::getExpiration);
//     }

//     public String generateToken(UserDetails userDetails) {
//         return generateToken(new HashMap<>(),userDetails);        
//     }

//     private String generateToken(Map<String,Object> extraClaims,UserDetails userDetails){
//         return Jwts.builder()
//         .setClaims(extraClaims)
//         .setSubject(userDetails.getUsername())
//         .setIssuedAt(new Date(System.currentTimeMillis()))
//         .setExpiration(new Date(System.currentTimeMillis()))
//         .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//         .compact () ;
//     }


    

// }




import static io.jsonwebtoken.SignatureAlgorithm.HS256;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
    @Value("${application.jwt.secret_key}")
    private String secret_key;
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username =  extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpires(token);
    }

    private boolean isTokenExpires(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    //generic type{}
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    private Key getSigningKey(){
        byte[] keyBytes=Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+1000*24*60*60*1000))
        .signWith(getSigningKey(),HS256)
        .compact();//to generate it compact is used here
    }   
}