package es.laura.saborYNoche.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipos_establecimiento")
@Getter
@Setter
public class TipoEstablecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private boolean activo;

    @OneToOne(mappedBy = "tipoEstablecimiento")
    private Empresa empresas;

    public TipoEstablecimiento() {
    }

}
