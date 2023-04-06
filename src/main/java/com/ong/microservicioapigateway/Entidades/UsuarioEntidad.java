package com.ong.microservicioapigateway.Entidades;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.management.relation.Role;
import javax.persistence.*;

@Data
@Entity
@Table(name = "usuarios")
public class UsuarioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;

    private String nombre;

    private String correo;

    private String contrasenia;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Rol role;

    @Transient
    private String token;

}
