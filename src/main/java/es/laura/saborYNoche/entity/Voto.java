package es.laura.saborYNoche.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "votos")
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int notaComida;
    private int notaServicio;
    private int notaAmbiente;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter y Setter para el atributo 'notaComida'
    public int getNotaComida() {
        return notaComida;
    }

    public void setNotaComida(int notaComida) {
        this.notaComida = notaComida;
    }

    // Getter y Setter para el atributo 'notaServicio'
    public int getNotaServicio() {
        return notaServicio;
    }

    public void setNotaServicio(int notaServicio) {
        this.notaServicio = notaServicio;
    }

    // Getter y Setter para el atributo 'notaAmbiente'
    public int getNotaAmbiente() {
        return notaAmbiente;
    }

    public void setNotaAmbiente(int notaAmbiente) {
        this.notaAmbiente = notaAmbiente;
    }

    // Getter y Setter para el atributo 'activo'
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Getter y Setter para el atributo 'usuario'
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Getter y Setter para el atributo 'empresa'
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
