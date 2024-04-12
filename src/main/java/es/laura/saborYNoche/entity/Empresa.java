package es.laura.saborYNoche.entity;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "empresas")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String codigoPostal;
    private String poblacion;
    private String provincia;
    private String descripcion;
    private String urlImagen;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_tipo_establecimiento", nullable = false)
    private TipoEstablecimiento tipoEstablecimiento;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "empresa")
    private List<Voto> votos;

}
