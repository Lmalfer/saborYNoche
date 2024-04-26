package es.laura.saborYNoche.modelo;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "usuarios")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private boolean activo;
  private boolean denunciado;
  private String nombre;
  private String clave;
  private String email;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }

  public boolean isDenunciado() {
    return denunciado;
  }

  public void setDenunciado(boolean denunciado) {
    this.denunciado = denunciado;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Usuario usuario = (Usuario) o;
    return activo == usuario.activo && denunciado == usuario.denunciado && Objects.equals(id, usuario.id) && Objects.equals(nombre, usuario.nombre) && Objects.equals(clave, usuario.clave) && Objects.equals(email, usuario.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, activo, denunciado, nombre, clave, email);
  }

  @Override
  public String toString() {
    return "Usuario{" +
            "id=" + id +
            ", activo=" + activo +
            ", denunciado=" + denunciado +
            ", nombre='" + nombre + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
