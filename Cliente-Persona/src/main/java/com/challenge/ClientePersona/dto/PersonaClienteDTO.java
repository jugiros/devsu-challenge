package com.challenge.ClientePersona.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaClienteDTO {

    // Campos para Persona
    private String nombre;
    private String genero;
    private int edad;
    private String identificacion;
    private String direccion;
    private String telefono;

    // Campos para Cliente
    private String contraseña;
    private Boolean estado;

}
