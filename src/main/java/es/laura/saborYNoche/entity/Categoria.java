package es.laura.saborYNoche.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private boolean activo;

    @OneToMany(mappedBy = "categoria")
    private List<Empresa> empresas;

    // Constructor, getters y setters
}
