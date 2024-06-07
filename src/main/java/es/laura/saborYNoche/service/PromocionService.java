package es.laura.saborYNoche.service;

import es.laura.saborYNoche.model.Promocion;
import es.laura.saborYNoche.model.User;

public interface PromocionService {
    void quitarPromocionDeTodasLasEmpresas(User user);
    void aplicarPromocionATodasLasEmpresas(Promocion promocion, User user);

}
