package es.laura.saborYNoche.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios_no_registrados")
public class UsuarioNoRegistrado extends Usuario {
    private String ip;
    // Otros atributos específicos para usuarios no registrados

    // Getters y setters
}
