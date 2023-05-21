package com.ong.microservicioapigateway.Controladores;

import com.ong.microservicioapigateway.Entidades.UsuarioEntidad;
import com.ong.microservicioapigateway.Seguridad.UserPrincipal;
import com.ong.microservicioapigateway.Servicios.AuthenticationServicio;
import com.ong.microservicioapigateway.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/authentication")
public class AuthenticationControlador {

    @Autowired
    private AuthenticationServicio authenticationServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioEntidad usuarioEntidad){
        System.out.println(usuarioEntidad);
        if (usuarioServicio.obtenerUsuarioforNombreV2(usuarioEntidad.getNombre()).equals(null)){
            System.out.println("Nulo");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(usuarioServicio.guardarUsuario(usuarioEntidad),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody UsuarioEntidad usuarioEntidad){
        return new ResponseEntity<>(authenticationServicio.loginAndReturnJwt(usuarioEntidad),HttpStatus.OK);
    }
}
