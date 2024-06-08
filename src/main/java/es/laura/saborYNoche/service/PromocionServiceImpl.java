package es.laura.saborYNoche.service;

import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.model.Promocion;
import es.laura.saborYNoche.model.User;
import es.laura.saborYNoche.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
public class PromocionServiceImpl implements PromocionService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;
    @Transactional
    public void aplicarPromocionATodasLasEmpresas(Promocion promocion, User user) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            List<Empresa> empresas = empresaRepository.findAllByUser(user);

            for (Empresa empresa : empresas) {
                empresa.setPromocion(promocion);
            }

            empresaRepository.saveAll(empresas);

            transactionManager.commit(status);
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
    @Transactional
    public void quitarPromocionDeTodasLasEmpresas(User user) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            List<Empresa> empresas = empresaRepository.findAllByUser(user);

            for (Empresa empresa : empresas) {
                empresa.setPromocion(null);
            }

            empresaRepository.saveAll(empresas);

            transactionManager.commit(status);
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}