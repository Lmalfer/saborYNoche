package es.laura.saborYNoche.dto;

import es.laura.saborYNoche.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpresarioDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    // Otros campos específicos para usuarios empresarios
    private String nombreEmpresa;
    private String direccionEmpresa;
    private String codigoPostalEmpresa;
    private String poblacionEmpresa;
    private String provinciaEmpresa;
    private String descripcionEmpresa;
    private String urlImagenEmpresa;
    private boolean activo;
    private RoleEnum role;
}
