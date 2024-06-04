package es.laura.saborYNoche.model;

import es.laura.saborYNoche.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuarios")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false)
  @NotBlank(message = "El nombre es requerido")
  private String name;

  @Column(nullable=false, unique=true)
  @Email(message = "El correo electrónico no es válido")
  private String email;

  @Column(nullable=false)
  @NotBlank(message = "La contraseña es requerida")
  @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private RoleEnum role;

  @ManyToMany(fetch = FetchType.EAGER)//especificacion de que Hibernate debe cargar la colección categorias junto con la entidad Empresa
  @JoinTable(
          name = "favoritos",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "empresa_id")
  )
  private List<Empresa> empresas;
}