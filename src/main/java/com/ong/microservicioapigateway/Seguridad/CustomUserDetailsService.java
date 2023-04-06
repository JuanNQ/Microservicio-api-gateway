package com.ong.microservicioapigateway.Seguridad;

import com.ong.microservicioapigateway.Entidades.UsuarioEntidad;
import com.ong.microservicioapigateway.Servicios.UsuarioServicio;
import com.ong.microservicioapigateway.Utils.SeguridadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntidad usuario = usuarioServicio.obtenerUsuarioforNombre(username);

        Set<GrantedAuthority> authorities = Set.of(SeguridadUtils.convertToAuthority(usuario.getRole().name()));
        return UserPrincipal.builder()
                .usuario(usuario)
                .id(usuario.getId())
                .nombre(username)
                .contrasenia(usuario.getContrasenia())
                .authorities(authorities)
                .build();
    }
}
