package com.ong.microservicioapigateway.Repositorios;

import com.ong.microservicioapigateway.Entidades.Rol;
import com.ong.microservicioapigateway.Entidades.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;


@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioEntidad, Long> {

    Optional<UsuarioEntidad> findByNombre(String nombre);

    Optional<UsuarioEntidad> findByNombreAndContrasenia(String nombre, String contrasenia);

    @Modifying
    @Query(value = "update UsuarioEntidad set rol=:rol where nombre=:nombre")
    void updateRolForUsuario(@Param("nombre") String correo,@Param("rol") Rol rol);
}
