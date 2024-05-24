package es.laura.saborYNoche.validator;


import es.laura.saborYNoche.model.Empresa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CodigoPostalValidator implements ConstraintValidator<Empresa.CodigoPostalValido, String> {

    @Override
    public void initialize(Empresa.CodigoPostalValido constraintAnnotation) {
    }

    @Override
    public boolean isValid(String codigoPostal, ConstraintValidatorContext context) {
        return codigoPostal.matches("\\d{5}") || codigoPostal.matches("\\d{5}-\\d{4}");
    }
}
