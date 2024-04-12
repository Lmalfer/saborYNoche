package es.laura.saborYNoche.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean activo;


    private String nombre;

    // Getter y Setter para el atributo 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter y Setter para el atributo 'nombre'
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
