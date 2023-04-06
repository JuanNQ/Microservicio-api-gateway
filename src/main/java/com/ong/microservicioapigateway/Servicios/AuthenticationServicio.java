package com.ong.microservicioapigateway.Servicios;

import com.ong.microservicioapigateway.Entidades.UsuarioEntidad;

public interface AuthenticationServicio {
    String loginAndReturnJwt(UsuarioEntidad loginRequest);
}
