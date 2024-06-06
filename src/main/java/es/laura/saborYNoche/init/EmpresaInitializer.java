package es.laura.saborYNoche.init;

import es.laura.saborYNoche.model.Empresa;
import es.laura.saborYNoche.repository.EmpresaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EmpresaInitializer {

    @Autowired
    private EmpresaRepository empresaRepository;

    @PostConstruct
    public void initialize() {
        List<Empresa> empresas = empresaRepository.findAll();
        for (Empresa empresa : empresas) {
            if (empresa.getFechaPublicacion() == null) {
                empresa.setFechaPublicacion(LocalDateTime.now()); // Puedes elegir otra fecha si es necesario
                empresaRepository.save(empresa);
            }
        }
    }
}

