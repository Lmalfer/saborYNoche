package es.laura.saborYNoche.model;

import es.laura.saborYNoche.validator.CodigoPostalValidator;
import es.laura.saborYNoche.validator.ProvinciaValidator;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "{NotBlank.nombre}")
    private String nombre;

    private String descripcion;
    private String direccion;

    @ProvinciaValida
    private String provincia;


    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = ProvinciaValidator.class)
    public @interface ProvinciaValida {
        String message() default "La provincia no es válida";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    private String comunidad;
    private String poblacion;

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = CodigoPostalValidator.class)
    public @interface CodigoPostalValido {
        String message() default "El código postal no es válido";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    @CodigoPostalValido
    private String codigoPostal;

//    @Lob
//    private byte[] imagen;

    @Transient
    private MultipartFile urlImagen;

    @Column(columnDefinition = "LONGTEXT")
    private String imagenBase64;


    @ManyToMany(fetch = FetchType.EAGER)
//especificacion de que Hibernate debe cargar la colección categorias junto con la entidad Empresa
    @JoinTable(
            name = "empresa_categoria",
            joinColumns = @JoinColumn(name = "empresa_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias;

    @ManyToOne
    @JoinColumn(name = "tipo_establecimiento_id")
    private TipoEstablecimiento tipoEstablecimiento;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<User> usuariosQueVotaron = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "votos", joinColumns = @JoinColumn(name = "empresa_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "puntuacion")
    private Map<User, Integer> puntuaciones = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "promocion_id")
    private Promocion promocion;

}
