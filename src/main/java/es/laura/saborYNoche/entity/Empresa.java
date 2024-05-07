package es.laura.saborYNoche.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "empresas")
@Getter
@Setter
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String direccion;
    private String codigoPostal;
    private String poblacion;
    private String provincia;
    private String descripcion;
    private String urlImagen;
    private boolean activo;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    // Relación uno a uno con TipoEstablecimiento
    @OneToOne
    @JoinColumn(name = "id_tipo_establecimiento", nullable = false)
    private TipoEstablecimiento tipoEstablecimiento;

    // Relación uno a uno con Categoria
    @OneToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    // Otros campos y métodos si es necesario
}
