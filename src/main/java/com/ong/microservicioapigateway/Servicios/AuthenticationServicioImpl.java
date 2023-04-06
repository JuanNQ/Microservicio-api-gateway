package com.ong.microservicioapigateway.Servicios;

import com.ong.microservicioapigateway.Entidades.UsuarioEntidad;
import com.ong.microservicioapigateway.Repositorios.UsuarioRepositorio;
import com.ong.microservicioapigateway.Seguridad.UserPrincipal;
import com.ong.microservicioapigateway.Seguridad.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServicioImpl implements AuthenticationServicio {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public String loginAndReturnJwt(UsuarioEntidad loginRequest){

        UsuarioEntidad user = usuarioRepositorio.findByNombre(loginRequest.getNombre())
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no fue encontrado:" + loginRequest.getNombre()));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getNombre(),loginRequest.getContrasenia())
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.crearToken(userPrincipal);

        UsuarioEntidad loginUser = userPrincipal.getUsuario();
        loginUser.setToken(jwt);

        return jwt;
    }


}
