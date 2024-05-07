package es.laura.saborYNoche.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
    @Entity
    @Getter
    @Setter
    @Table(name = "Favoritos")
    public class Favoritos {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Integer id;

        @OneToOne
        @JoinColumn(name = "id_usuario", nullable = false)
        private User usuario;

        @OneToOne
        @JoinColumn(name = "id_empresa", nullable = false)
        private Empresa empresa;

        private boolean activo;

    }


