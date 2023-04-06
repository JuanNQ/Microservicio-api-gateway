package com.ong.microservicioapigateway.Utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class SeguridadUtils {

    public static final String ROL_PREFIJO = "ROLE_";
    public static final String AUTH_HEADER = "authorization";
    public static final String AUTH_TOKEN_TYPE = "Bearer";
    public static final String AUTH_TOKEN_PREFIJO = AUTH_TOKEN_TYPE + " ";

    public static SimpleGrantedAuthority convertToAuthority(String rol){
        String formatoRol = rol.startsWith(ROL_PREFIJO)? rol: ROL_PREFIJO + rol;
        return new SimpleGrantedAuthority(formatoRol);
    }

    public static String extractTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTH_HEADER);

        if(StringUtils.hasLength(bearerToken) && bearerToken.startsWith(AUTH_TOKEN_PREFIJO)){
            return bearerToken.substring(7);
        }
        return null;
    }
}
