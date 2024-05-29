package es.laura.saborYNoche.model;

import es.laura.saborYNoche.enums.RoleEnum;
import jakarta.persistence.*;
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
  private String name;

  @Column(nullable=false, unique=true)
  private String email;

  @Column(nullable=false)
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