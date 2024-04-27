package es.laura.saborYNoche.entity;

import jakarta.persistence.*;


    @Entity
    @Table(name = "Favoritos")
    public class Favoritos {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Integer id;

        @ManyToOne
        @JoinColumn(name = "id_usuario", nullable = false)
        private User usuario;

        @ManyToOne
        @JoinColumn(name = "id_empresa", nullable = false)
        private Empresa empresa;

        private boolean activo;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public User getUser() {
            return usuario;
        }

        public void setUser(User usuario) {
            this.usuario = usuario;
        }

        public Empresa getEmpresa() {
            return empresa;
        }

        public void setEmpresa(Empresa empresa) {
            this.empresa = empresa;
        }

        public boolean isActivo() {
            return activo;
        }

        public void setActivo(boolean activo) {
            this.activo = activo;
        }
    }


