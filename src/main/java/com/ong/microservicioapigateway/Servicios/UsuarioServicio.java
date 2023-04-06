package com.ong.microservicioapigateway.Servicios;

import com.ong.microservicioapigateway.Entidades.Rol;
import com.ong.microservicioapigateway.Entidades.UsuarioEntidad;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UsuarioServicio {
    UsuarioEntidad guardarUsuario(UsuarioEntidad usuario);
    UsuarioEntidad obtenerUsuarioforNombre(String nombre);

    UsuarioEntidad obtenerPerfilUsuario(String nombre);

    @Transactional
    void updateRolForUsuario(String correo, Rol role);
}
