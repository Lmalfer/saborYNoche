package es.laura.saborYNoche.modelo;

import jakarta.persistence.*;


    @Entity
    @Table(name = "Favoritos")
    public class Favoritos {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Integer id;

        @ManyToOne
        @JoinColumn(name = "id_usuario", nullable = false)
        private Usuario usuario;

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

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
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


