package com.ong.microservicioapigateway.Seguridad.jwt;

import com.ong.microservicioapigateway.Entidades.UsuarioEntidad;
import com.ong.microservicioapigateway.Seguridad.UserPrincipal;
import com.ong.microservicioapigateway.Utils.SeguridadUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl implements JwtProvider{

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRED;

    @Override
    public String crearToken(UserPrincipal user){
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(user.getNombre())
                .claim("roles",authorities)
                .claim("userId", user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRED))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

    }

    @Override
    public String crearToken(UsuarioEntidad user){

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(user.getNombre())
                .claim("roles",user.getRole())
                .claim("userId", user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRED))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request){
        Claims claims = extractClaims(request);
        if(claims==null)
        {
            return null;
        }
        String nombre = claims.getSubject();
        Double userId = claims.get("userId", Double.class);
        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SeguridadUtils::convertToAuthority)
                .collect(Collectors.toSet());

        UserDetails userDetails = UserPrincipal.builder()
                .nombre(nombre)
                .id(userId)
                .authorities(authorities)
                .build();

        if(nombre==null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,authorities);
    }

    @Override
    public boolean  isTokenValid(HttpServletRequest request){
        Claims claims = extractClaims(request);

        if(claims==null)
        {
            return false;
        }
        if(claims.getExpiration().before(new Date())){
            return false;
        }
        return true;

    }

    private Claims extractClaims(HttpServletRequest request){
        String token = SeguridadUtils.extractTokenFromRequest(request);

        if(token==null){
            return null;
        }

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
