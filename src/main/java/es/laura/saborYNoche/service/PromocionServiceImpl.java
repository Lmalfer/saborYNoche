package es.laura.saborYNoche.service;

import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.Promocion;
import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PromocionServiceImpl implements PromocionService{

    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    public void aplicarPromocionATodasLasEmpresas(Promocion promocion, User user) {
        List<Empresa> empresas = empresaRepository.findAllByUser(user);

        for (Empresa empresa : empresas) {
            empresa.setPromocion(promocion);
        }

        empresaRepository.saveAll(empresas);
    }

    @Transactional
    public void quitarPromocionDeTodasLasEmpresas(User user) {
        List<Empresa> empresas = empresaRepository.findAllByUser(user);

        for (Empresa empresa : empresas) {
            empresa.setPromocion(null);
        }

        empresaRepository.saveAll(empresas);
    }
}
