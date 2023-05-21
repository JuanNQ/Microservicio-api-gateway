package com.ong.microservicioapigateway.Servicios;

import com.ong.microservicioapigateway.Entidades.Rol;
import com.ong.microservicioapigateway.Entidades.UsuarioEntidad;
import com.ong.microservicioapigateway.Repositorios.UsuarioRepositorio;
import com.ong.microservicioapigateway.Seguridad.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioServicioImpl implements UsuarioServicio{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public UsuarioEntidad guardarUsuario(UsuarioEntidad usuario){
        System.out.println("usuario: " + usuario);
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        usuario.setRole(Rol.USER);
        UsuarioEntidad userCreated = usuarioRepositorio.save(usuario);

        String jwt = jwtProvider.crearToken(userCreated);
        userCreated.setToken(jwt);
        System.out.println(userCreated);
        return userCreated;
    }

    @Override
    public Optional<UsuarioEntidad> obtenerUsuarioforNombreV2(String nombre){
        System.out.println(nombre);
        var usuario= usuarioRepositorio.findByNombre(nombre);
        System.out.println(usuario);
        return usuario;
    }

    @Override
    public UsuarioEntidad obtenerUsuarioforNombre(String nombre){
        return usuarioRepositorio.findByNombre(nombre).orElseThrow(()-> new UsernameNotFoundException("No existe el usuario"));
    }

    @Override
    public UsuarioEntidad obtenerPerfilUsuario(String nombre){
        UsuarioEntidad usuario = usuarioRepositorio.findByNombre(nombre).orElseThrow(() -> new UsernameNotFoundException("El usuario no existe: " + nombre));
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        boolean contraseniaCorrecta = encoder.matches(contrasenia, usuario.getContrasenia());
//        String jwt = jwtProvider.crearToken(usuario);
//        usuario.setToken(jwt);
        return usuario;
    }

    @Override
    @Transactional
    public void updateRolForUsuario(String nombre, Rol role){
        usuarioRepositorio.updateRolForUsuario(nombre,role);
    }
}
