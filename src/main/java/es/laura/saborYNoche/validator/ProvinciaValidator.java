package es.laura.saborYNoche.validator;

import es.laura.saborYNoche.model.Empresa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ProvinciaValidator implements ConstraintValidator<Empresa.ProvinciaValida, String> {

    @Override
    public void initialize(Empresa.ProvinciaValida constraintAnnotation) {
    }

    @Override
    public boolean isValid(String provincia, ConstraintValidatorContext context) {
        return provincia != null && esProvinciaValida(provincia);
    }

    private boolean esProvinciaValida(String provincia) {

        String[] provinciasValidas = {"Álava", "Albacete", "Alicante", "Almería", "Ávila", "Badajoz", "Baleares", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Castellón", "Ciudad Real", "Córdoba", "Cuenca", "Girona", "Granada", "Guadalajara", "Guipúzcoa", "Huelva", "Huesca", "Jaén", "León", "Lleida", "La Rioja", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Ourense", "Asturias", "Palencia", "Las Palmas", "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", "Cantabria", "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza", "Ceuta", "Melilla"};
        for (String p : provinciasValidas) {
            if (p.equalsIgnoreCase(provincia)) {
                return true;
            }
        }
        return false;
    }
}

