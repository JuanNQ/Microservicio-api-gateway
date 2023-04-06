package com.ong.microservicioapigateway.Controladores;

import com.ong.microservicioapigateway.Repositorios.UsuarioRepositorio;
import com.ong.microservicioapigateway.Seguridad.UserPrincipal;
import com.ong.microservicioapigateway.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerPerfil(@AuthenticationPrincipal UserPrincipal userPrincipal){
//        try {
            return new ResponseEntity<>(usuarioServicio.obtenerPerfilUsuario(userPrincipal.getNombre()), HttpStatus.OK);
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
    }

}
