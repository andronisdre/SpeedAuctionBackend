package com.spring.SpeedAuction.security.jwt;

import com.spring.SpeedAuction.Services.UserDetailImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;


import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${speedAuction.app.jwtSecret}")
    private String jwtSecret;

    @Value("${speedAuction.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${speedAuction.app.jwtCookieName}")
    private String jwtCookie;


    //for cookie

    public ResponseCookie generateJwtCookie(UserDetailImpl userPrincipal){

        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 *60).httpOnly(true).build();
        return cookie;
    }

    public String generateTokenFromUsername(String username){

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(),SignatureAlgorithm.HS256)
                .compact();
    }
    //rensa cookie

    public ResponseCookie getCleanJwtCookie(){
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }
    //hämta jwt from cookie
    public  String getJwtFromCookie(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null){
            return cookie.getValue();
        }else {
            return null;
        }
    }




    //generera JWT tocken
  /*  public String generateJwtToken(Authentication authentication){
        UserDetailImpl userPrincipal = (UserDetailImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(),SignatureAlgorithm.HS256)
                .compact();

    }

   */

    //Key method
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    //validera token me secret

    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;

        }catch (MalformedJwtException e){
            logger.error("Invalid JWT token:{}", e.getMessage());

        }catch (ExpiredJwtException e){
            logger.error("JWT token has expired: {}",e.getMessage());
        }catch (UnsupportedJwtException e ){
            logger.error("JWT token is Unsupported: {}", e.getMessage());
        }catch (IllegalArgumentException e){
            logger.error("JWT claim String is empty: {}", e.getMessage());

        }
        return false;
    }

}
