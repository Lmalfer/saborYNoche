package es.laura.saborYNoche.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class EmpresaResponse {
    private Integer id;
    private String nombre;
    private BigDecimal mediaVotos;
}

