package es.laura.saborYNoche.entity;

import jakarta.persistence.*;


    @Entity
    @Table(name = "Favoritos")
    public class Favoritos {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Long idFavorito;

        @ManyToOne
        @JoinColumn(name = "id_usuario", nullable = false)
        private Usuario usuario;

        @ManyToOne
        @JoinColumn(name = "id_empresa", nullable = false)
        private Empresa empresa;

        private boolean activo;


    }


