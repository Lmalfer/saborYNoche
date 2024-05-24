package es.laura.saborYNoche.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "votos")
public class Votos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int notaComida;
    private int notaServicio;
    private int notaAmbiente;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @OneToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;



}