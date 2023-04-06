package com.ong.microservicioapigateway.Seguridad.jwt;

import com.ong.microservicioapigateway.Entidades.UsuarioEntidad;
import com.ong.microservicioapigateway.Seguridad.UserPrincipal;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;


public interface JwtProvider {
    String crearToken(UserPrincipal user);

    String crearToken(UsuarioEntidad user);

    Authentication getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);
}
