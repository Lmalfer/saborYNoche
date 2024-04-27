package es.laura.saborYNoche.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity // Añadida la anotación @Entity
@Table(name = "tipos_establecimiento") // Opcional: Especifica el nombre de la tabla en la base de datos
public class TipoEstablecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private boolean activo;

    @OneToMany(mappedBy = "tipoEstablecimiento")
    private List<Empresa> empresas;

    // Constructor por defecto
    public TipoEstablecimiento() {
    }

    // Constructor con todos los campos
    public TipoEstablecimiento(String nombre, boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }
}
