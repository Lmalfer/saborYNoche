package es.laura.saborYNoche.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios_registrados")
public class UsuarioRegistrado extends Usuario {
    private String nombreCompleto;
    private String correoElectronico;
    // Otros atributos específicos para usuarios registrados

    // Getters y setters
}
