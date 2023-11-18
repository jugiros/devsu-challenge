package com.challenge.ClientePersona.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clienteId")
    private Long clienteId;

    @Column(name = "contraseña")
    private String contraseña;

    @Column(name = "estado")
    private boolean estado;

    @OneToOne
    @JoinColumn(name = "id")
    private Persona persona;
}
